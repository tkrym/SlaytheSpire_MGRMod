package note;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.vfx.combat.PlasmaOrbPassiveEffect;
import effect.NotePassiveEffect;

public class StarryNote extends AbstractNote {
    public static final String ORB_ID = "MGR:Starry";
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    private float vfxIntervalMin = 0.6f;
    private float vfxIntervalMax = 1.0f;
    private static final float ORB_WAVY_DIST = 0.04f;
    private static final float PI_4 = 12.566371f;
    private float myvfxtimer=0.8f;

    public StarryNote() {
        this.ID = ORB_ID;
        this.img = ImageMaster.loadImage("img/note/Starry.png");
        this.name = orbString.NAME;
        this.baseEvokeAmount = 1;
        this.evokeAmount = this.baseEvokeAmount;
        this.angle = MathUtils.random(360.0f);
        this.channelAnimTimer = 0.5f;
        this.forterate=999;
        myColor= CardHelper.getColor(89,248,255);
        updateDescription();
    }

    @Override
    public void myApplyForte() { this.evokeAmount=this.baseEvokeAmount; }

    public void updateDescription() {
        applyForte();
        this.description = orbString.DESCRIPTION[0]+this.evokeAmount+orbString.DESCRIPTION[1];
    }

    public void myEvoke() {
        AbstractDungeon.actionManager.addToTop(new GainEnergyAction(this.evokeAmount));
    }

    @Override
    public void updateAnimation()
    {
        super.updateAnimation();
        this.myvfxtimer -= Gdx.graphics.getDeltaTime();
        if (this.myvfxtimer< 0.0f) {
            AbstractDungeon.effectList.add(new PlasmaOrbPassiveEffect(this.cX, this.cY));
            this.myvfxtimer = MathUtils.random(vfxIntervalMin, vfxIntervalMax);
        }
    }

    public void playChannelSFX() {
        CardCrawlGame.sound.play("ORB_PLASMA_CHANNEL", 0.1f);
    }

    public AbstractNote makeCopy() {
        return new StarryNote();
    }
}
