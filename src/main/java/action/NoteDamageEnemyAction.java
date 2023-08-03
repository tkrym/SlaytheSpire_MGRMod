package action;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.RelicAboveCreatureEffect;
import effect.NoteAboveCreatureEffect;
import note.AbstractNote;

public class NoteDamageEnemyAction extends AbstractGameAction
{
    private boolean HitAll;
    private static final Texture AttackNoteIMG = ImageMaster.loadImage("img/note/Attack.png");

    public NoteDamageEnemyAction(int amount, boolean HitAll)
    {
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.HitAll = HitAll;
        this.source = AbstractDungeon.player;
        this.amount = amount;
    }

    @Override
    public void update()
    {
        //float speedTime = Settings.FAST_MODE?0.0F:0.05F;
        if (!this.HitAll)
        {
            AbstractCreature m = AbstractDungeon.getRandomMonster();
            if (m != null)
            {
                DamageInfo info = new DamageInfo(source, AbstractNote.applyVulnerable(m, this.amount), DamageInfo.DamageType.THORNS);
                addToTop(new DamageAction(m, info, GetEffect(info.base), true));
                addToTop(new VFXAction(new NoteAboveCreatureEffect(m.hb.cX - m.animX, m.hb.cY + m.hb.height / 2.0F - m.animY, AttackNoteIMG, 20.0f*Settings.scale,80.0f*Settings.scale, 0.5f), Settings.ACTION_DUR_XFAST / 2.0f));
            }
        }
        else
        {
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
            {
                if (!m.isDeadOrEscaped() && !m.halfDead)
                {
                    DamageInfo info = new DamageInfo(source, AbstractNote.applyVulnerable(m, this.amount), DamageInfo.DamageType.THORNS);
                    addToTop(new DamageAction(m, info, GetEffect(info.base), true));
                    addToTop(new VFXAction(new NoteAboveCreatureEffect(m.hb.cX - m.animX, m.hb.cY + m.hb.height / 2.0F - m.animY, AttackNoteIMG, 20.0f*Settings.scale,80.0f*Settings.scale, 0.5f), Settings.ACTION_DUR_XFAST / 3.0f));
                }
            }
        }
        this.isDone = true;
    }

    private AttackEffect GetEffect(int dmg)
    {
        if(dmg<=4) return AttackEffect.BLUNT_LIGHT;
        else if(dmg<=10) return AttackEffect.BLUNT_HEAVY;
        else return AttackEffect.FIRE;
    }

}