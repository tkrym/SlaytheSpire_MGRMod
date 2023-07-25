package action;

import character.MGR_character;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import hook.OnChordHook;
import note.AbstractNote;
import power.GazePower;

import java.util.ArrayList;

public class EndOfSanityAction extends AbstractGameAction
{
    public EndOfSanityAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startDuration= Settings.ACTION_DUR_FAST;
        this.duration=this.startDuration;
    }
    public void update() {
        if(this.duration==this.startDuration)
        {
            int cnt=0;
            for(AbstractCard c:AbstractDungeon.player.hand.group)
                if(c.cost>0||c.costForTurn>0)
                {
                    c.isCostModified=true;
                    c.cost=0;
                    c.costForTurn=0;
                    c.superFlash(Color.GOLD.cpy());
                    ++cnt;
                }
            while (cnt>0)
            {
                int tmp= Math.min(cnt, 5);
                addToTop(new MakeTempCardInDiscardAction(new VoidCard(),tmp));
                cnt-=tmp;
            }
        }
        this.tickDuration();
    }
}
