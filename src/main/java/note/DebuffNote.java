package note;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbPassiveEffect;

import java.util.Iterator;

public class DebuffNote extends AbstractNote {
    public static final String ORB_ID = "MGR:Debuff";
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    private static final float ORB_BORDER_SCALE = 1.2f;
    private float vfxTimer = 0.5f;
    private static final float VFX_INTERVAL_TIME = 0.25f;

    public DebuffNote() {
        this.ID = ORB_ID;
        this.img = ImageMaster.ORB_DARK;
        this.name = orbString.NAME;
        this.baseEvokeAmount = 1;
        this.evokeAmount = this.baseEvokeAmount;
        this.channelAnimTimer = 0.5f;
        this.forterate=4;
        updateDescription();
    }

    public void updateDescription() {
        applyForte();
        this.description = orbString.DESCRIPTION[0]+this.evokeAmount+orbString.DESCRIPTION[1]+this.forterate+orbString.DESCRIPTION[2];
    }

    public void myEvoke()
    {
        AbstractCreature p=AbstractDungeon.player;
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mo, p, new ConstrictedPower(mo, p, this.evokeAmount), this.evokeAmount,true,AbstractGameAction.AttackEffect.NONE));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mo, p, new VulnerablePower(mo, this.evokeAmount, false), this.evokeAmount, true, AbstractGameAction.AttackEffect.NONE));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mo, p, new WeakPower(mo, this.evokeAmount, false), this.evokeAmount, true, AbstractGameAction.AttackEffect.NONE));
        }
    }

    @Override
    public void triggerEvokeAnimation() {
        //CardCrawlGame.sound.play("ORB_DARK_EVOKE", 0.1f);
        //AbstractDungeon.effectsQueue.add(new DarkOrbActivateEffect(this.cX, this.cY));
    }

    @Override
    public void updateAnimation() {
        super.updateAnimation();
        updateDescription();
        this.angle += Gdx.graphics.getDeltaTime() * 120.0f;
        this.vfxTimer -= Gdx.graphics.getDeltaTime();
        if (this.vfxTimer < 0.0f) {
            AbstractDungeon.effectList.add(new DarkOrbPassiveEffect(this.cX, this.cY));
            this.vfxTimer = 0.25f;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.c);
        sb.draw(this.img, this.cX - 48.0f, (this.cY - 48.0f) + this.bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, this.scale, this.scale, this.angle, 0, 0, 96, 96, false, false);
        this.shineColor.a = this.c.a / 3.0f;
        sb.setColor(this.shineColor);
        sb.setBlendFunction(770, 1);
        sb.draw(this.img, this.cX - 48.0f, (this.cY - 48.0f) + this.bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, this.scale * ORB_BORDER_SCALE, this.scale * ORB_BORDER_SCALE, this.angle / ORB_BORDER_SCALE, 0, 0, 96, 96, false, false);
        sb.draw(this.img, this.cX - 48.0f, (this.cY - 48.0f) + this.bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, this.scale * 1.5f, this.scale * 1.5f, this.angle / 1.4f, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(770, 771);
        renderText(sb);
        this.hb.render(sb);
    }

    public void playChannelSFX() {
        CardCrawlGame.sound.play("ORB_DARK_CHANNEL", 0.1f);
    }

    public AbstractNote makeCopy() {
        return new DebuffNote();
    }
}
