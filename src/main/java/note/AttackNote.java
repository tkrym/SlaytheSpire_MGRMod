package note;

import action.NoteDamageEnemyAction;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.vfx.combat.LightningOrbPassiveEffect;
import power.StereoPlusPower;
import power.HarmonyFormPower;
import power.StereoPower;

public class AttackNote extends AbstractNote {
    public static final String ORB_ID = "MGR:Attack";
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    private float vfxTimer = 1.0f;
    private static final float PI_DIV_16 = 0.19634955f;
    private static final float ORB_WAVY_DIST = 0.05f;
    private static final float PI_4 = 12.566371f;
    private static final float ORB_BORDER_SCALE = 1.2f;

    public AttackNote() {
        this.ID = ORB_ID;
        this.img = ImageMaster.ORB_LIGHTNING;
        this.name = orbString.NAME;
        this.baseEvokeAmount = 2;
        this.evokeAmount = this.baseEvokeAmount;
        this.angle = MathUtils.random(360.0f);
        this.channelAnimTimer = 0.5f;
        this.forterate=1;
        updateDescription();
    }

    public void updateDescription() {
        applyForte();
        this.description = orbString.DESCRIPTION[0]+this.evokeAmount+orbString.DESCRIPTION[1]+this.forterate+orbString.DESCRIPTION[2];
    }

    public void myEvoke()
    {
        boolean hasStereo=AbstractDungeon.player.hasPower(StereoPower.POWER_ID)||AbstractDungeon.player.hasPower(StereoPlusPower.POWER_ID);
        AbstractDungeon.actionManager.addToTop(new NoteDamageEnemyAction(this.evokeAmount,hasStereo));
    }

    @Override
    public void triggerEvokeAnimation() {
        //CardCrawlGame.sound.play("ORB_LIGHTNING_EVOKE", 0.1f);
        //AbstractDungeon.effectsQueue.add(new LightningOrbActivateEffect(this.cX, this.cY));
    }

    @Override
    public void updateAnimation() {
        super.updateAnimation();
        updateDescription();
        this.angle += Gdx.graphics.getDeltaTime() * 180.0f;
        this.vfxTimer -= Gdx.graphics.getDeltaTime();
        if (this.vfxTimer < 0.0f) {
            AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(this.cX, this.cY));
            if (MathUtils.randomBoolean()) {
                AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(this.cX, this.cY));
            }
            this.vfxTimer = MathUtils.random(0.15f, 0.8f);
        }
    }

    public void render(SpriteBatch sb) {
        this.shineColor.a = this.c.a / 2.0f;
        sb.setColor(this.shineColor);
        sb.setBlendFunction(770, 1);
        sb.draw(this.img, this.cX - 48.0f, (this.cY - 48.0f) + this.bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, this.scale + (MathUtils.sin(this.angle / PI_4) * ORB_WAVY_DIST) + PI_DIV_16, this.scale * ORB_BORDER_SCALE, this.angle, 0, 0, 96, 96, false, false);
        sb.draw(this.img, this.cX - 48.0f, (this.cY - 48.0f) + this.bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, this.scale * ORB_BORDER_SCALE, this.scale + (MathUtils.sin(this.angle / PI_4) * ORB_WAVY_DIST) + PI_DIV_16, -this.angle, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(770, 771);
        sb.setColor(this.c);
        sb.draw(this.img, this.cX - 48.0f, (this.cY - 48.0f) + this.bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, this.scale, this.scale, this.angle / 12.0f, 0, 0, 96, 96, false, false);
        renderText(sb);
        this.hb.render(sb);
    }

    public void playChannelSFX() {
        CardCrawlGame.sound.play("ORB_LIGHTNING_CHANNEL", 0.1f);
    }

    public AbstractNote makeCopy() {
        return new AttackNote();
    }
}
