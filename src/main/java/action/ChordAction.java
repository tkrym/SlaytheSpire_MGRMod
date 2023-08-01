package action;

import character.MGR_character;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import hook.OnChordHook;
import note.AbstractNote;
import power.GazePower;

import java.util.ArrayList;
import java.util.Iterator;

public class ChordAction extends AbstractGameAction
{
    public ChordAction()
    {
        this.actionType = ActionType.SPECIAL;
        this.startDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startDuration;
    }

    public void update()
    {
        if (!(AbstractDungeon.player instanceof MGR_character))
        {
            this.isDone = true;
            return;
        }
        if (this.duration == this.startDuration)
        {
            MGR_character p = (MGR_character) AbstractDungeon.player;
            ArrayList<AbstractNote> notes = new ArrayList<>();
            for (AbstractOrb orb : p.orbs)
                if (orb instanceof AbstractNote)
                    notes.add((AbstractNote) orb);
            for (AbstractPower power : p.powers)
            {
                if (power instanceof OnChordHook)
                {
                    ((OnChordHook) power).OnChord(notes);
                }
            }
            /*for (AbstractRelic relic : p.relics)
            {
                if (relic instanceof OnChordHook)
                {
                    ((OnChordHook) relic).OnChord(notes);
                }
            }*/
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters)
            {
                if (!m.isDeadOrEscaped() && m.hasPower(GazePower.POWER_ID))
                {
                    ((OnChordHook) m.getPower(GazePower.POWER_ID)).OnChord(notes);
                }
            }
            p.inccounter(1);
            MGR_character.IncChordCount(1);
            p.EvokeAll();
        }
        this.tickDuration();
    }
}
