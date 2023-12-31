package note;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.vfx.combat.*;
import effect.NoteAboveCreatureEffect;

public class ArtifactNote extends AbstractNote {
    public static final String ORB_ID = "MGR:Artifact";
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    private float vfxIntervalMin = 0.1f;
    private float vfxIntervalMax = 0.4f;
    private static final float ORB_WAVY_DIST = 0.04f;
    private static final float PI_4 = 12.566371f;

    public ArtifactNote() {
        this.ID = ORB_ID;
        this.img = ImageMaster.loadImage("img/note/Artifact.png");
        this.name = orbString.NAME;
        this.baseEvokeAmount = 1;
        this.evokeAmount = this.baseEvokeAmount;
        this.angle = 0.0F;
        this.channelAnimTimer = 0.5f;
        this.forterate=4;
        this.angle = MathUtils.random(360.0f);
        myColor= CardHelper.getColor(249,228,5);
        updateDescription();
    }

    public void updateDescription() {
        applyForte();
        this.description = orbString.DESCRIPTION[0]+this.evokeAmount+orbString.DESCRIPTION[1]+this.forterate+orbString.DESCRIPTION[2];
    }

    public void myEvoke() {
        if(this.evokeAmount<=0) return;
        AbstractCreature p=AbstractDungeon.player;
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new ArtifactPower(p, this.evokeAmount), this.evokeAmount,true));
        AbstractDungeon.actionManager.addToTop(new VFXAction(new NoteAboveCreatureEffect(p.hb.cX - p.animX, p.hb.cY + p.hb.height / 2.0F - p.animY, this.img), Settings.ACTION_DUR_XFAST));
    }

    public AbstractNote makeCopy() {
        return new ArtifactNote();
    }
}
