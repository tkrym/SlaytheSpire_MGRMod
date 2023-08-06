package action;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.RelicAboveCreatureEffect;
import effect.NoteAboveCreatureEffect;
import note.AbstractNote;
import power.BurnsRedPower;
import power.StereoPlusPower;
import power.StereoPower;

public class NoteDamageEnemyAction extends AbstractGameAction
{
    private boolean HitAll;
    private Texture img;
    //private boolean hasStereo;
    //private boolean hasStereoPlus;
    //private static final Texture AttackNoteIMG = ImageMaster.loadImage("img/note/Attack.png");

    public NoteDamageEnemyAction(int amount, boolean HitAll,Texture img)
    {
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.HitAll = HitAll;
        this.source = AbstractDungeon.player;
        this.amount = amount;
        this.img=img;
        //this.hasStereo=AbstractDungeon.player.hasPower(StereoPower.POWER_ID)||AbstractDungeon.player.hasPower(StereoPlusPower.POWER_ID);
        //this.hasStereoPlus=AbstractDungeon.player.hasPower(StereoPlusPower.POWER_ID);
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
                DamageInfo info = new DamageInfo(source, this.amount, DamageInfo.DamageType.THORNS);
                addToTop(new DamageAction(m, info, GetEffect(this.amount), true));
                addToTop(new VFXAction(new NoteAboveCreatureEffect(m.hb.cX - m.animX, m.hb.cY + m.hb.height / 2.0F - m.animY, this.img, 20.0f*Settings.scale,80.0f*Settings.scale, 0.5f), Settings.ACTION_DUR_XFAST / 10.0f));
            }
        }
        else
        {
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
            {
                if (!m.isDeadOrEscaped() && !m.halfDead)
                {
                    DamageInfo info = new DamageInfo(source, this.amount, DamageInfo.DamageType.THORNS);
                    addToTop(new DamageAction(m, info, GetEffect(this.amount), true));
                    //if(this.hasStereo) addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, info.base>>1,true));
                    addToTop(new VFXAction(new NoteAboveCreatureEffect(m.hb.cX - m.animX, m.hb.cY + m.hb.height / 2.0F - m.animY, this.img, 20.0f*Settings.scale,80.0f*Settings.scale, 0.5f), Settings.ACTION_DUR_XFAST / 10.0f));
                }
            }
        }
        this.isDone = true;
    }

    private AttackEffect GetEffect(int dmg)
    {
        if(AbstractDungeon.player.hasPower(BurnsRedPower.POWER_ID)&&this.HitAll) return AttackEffect.FIRE;
        if(dmg<=2) return AttackEffect.NONE;
        else if(dmg<=6) return AttackEffect.BLUNT_LIGHT;
        else return AttackEffect.BLUNT_HEAVY;
    }

}