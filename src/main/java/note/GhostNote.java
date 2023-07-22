package note;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.vfx.combat.PlasmaOrbPassiveEffect;
import power.HarmonyFormPower;
import power.ResonanceFormPower;

public class GhostNote extends AbstractNote {
    public static final String ORB_ID = "MGR:Ghost";
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    private float vfxIntervalMin = 0.1f;
    private float vfxIntervalMax = 0.4f;
    private static final float ORB_WAVY_DIST = 0.04f;
    private static final float PI_4 = 12.566371f;

    public GhostNote() {
        this.ID = ORB_ID;
        this.img = ImageMaster.loadImage("img/note/Ghost.png");
        this.name = orbString.NAME;
        this.baseEvokeAmount = 1;
        this.evokeAmount = this.baseEvokeAmount;
        this.angle = MathUtils.random(360.0f);
        this.channelAnimTimer = 0.5f;
        this.forterate=6;
        myColor= CardHelper.getColor(187,247,143);
        updateDescription();
    }

    public void updateDescription() {
        applyForte();
        this.description = orbString.DESCRIPTION[0]+this.evokeAmount+orbString.DESCRIPTION[1]+this.forterate+orbString.DESCRIPTION[2];
    }

    public void myEvoke() {
        AbstractCreature p=AbstractDungeon.player;
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, this.evokeAmount), this.evokeAmount,true));
    }

    public void playChannelSFX() {
        CardCrawlGame.sound.play("ORB_PLASMA_CHANNEL", 0.1f);
    }

    public AbstractNote makeCopy() {
        return new GhostNote();
    }

    @Override
    public void render(SpriteBatch sb) {
        float scale=1+MathUtils.sin(this.angle)*0.1F+0.1F;
        float alphascale=2.0F+MathUtils.sin(this.angle*2)*1.0F;
        this.shineColor=this.c.cpy();
        this.shineColor.a = this.c.a / alphascale;
        sb.setBlendFunction(770, 771);
        sb.setColor(this.shineColor);
        sb.draw(this.img, this.cX - 48.0f, (this.cY - 48.0f) + this.bobEffect.y*0.5F, 48.0f, 48.0f, 96.0f, 96.0f, this.scale*scale, this.scale*scale, 0.0f, 0, 0, 96, 96, false, false);
        if(AbstractDungeon.player!=null&&AbstractDungeon.player.hasPower(HarmonyFormPower.POWER_ID))
        {
            Color tmpColor=this.shineColor.cpy();
            tmpColor.a/=2;
            sb.setColor(tmpColor);
            sb.setBlendFunction(770, 1);
            sb.draw(this.img, this.cX - 48.0f, (this.cY - 48.0f) + this.bobEffect.y*0.5F, 48.0f, 48.0f, 96.0f, 96.0f, this.scale*scale*1.3F, this.scale*scale*1.3F, 0.0f, 0, 0, 96, 96, false, false);
        }
        renderText(sb);
        this.hb.render(sb);
    }
}
