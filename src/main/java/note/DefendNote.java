package note;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.LightningOrbEvokeAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FrostOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.FrostOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningOrbPassiveEffect;
import effect.NoteAboveCreatureEffect;

public class DefendNote extends AbstractNote {
    public static final String ORB_ID = "MGR:Defend";
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    private float vfxIntervalMin = 0.15f;
    private float vfxIntervalMax = 0.8f;
    private boolean hFlip1 = MathUtils.randomBoolean();
    private boolean hFlip2 = MathUtils.randomBoolean();

    public DefendNote() {
        this.ID = ORB_ID;
        this.img = ImageMaster.loadImage("img/note/Defend.png");
        this.name = orbString.NAME;
        this.baseEvokeAmount = 2;
        this.evokeAmount = this.baseEvokeAmount;
        this.channelAnimTimer = 0.5f;
        this.angle = MathUtils.random(360.0f);
        this.forterate=1;
        myColor= CardHelper.getColor(83,249,0);
        updateDescription();
    }

    public void updateDescription() {
        applyForte();
        this.description = orbString.DESCRIPTION[0]+this.evokeAmount+orbString.DESCRIPTION[1]+this.forterate+orbString.DESCRIPTION[2];
    }

    public void myEvoke()
    {
        if(this.evokeAmount<=0) return;
        AbstractPlayer p=AbstractDungeon.player;
        AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, this.evokeAmount,true));
        AbstractDungeon.actionManager.addToTop(new VFXAction(new NoteAboveCreatureEffect(p.hb.cX - p.animX, p.hb.cY + p.hb.height / 2.0F - p.animY, this.img), Settings.ACTION_DUR_XFAST));
    }

    public void playChannelSFX() {
        CardCrawlGame.sound.play("ORB_FROST_CHANNEL", 0.1f);
    }

    public AbstractNote makeCopy() {
        return new DefendNote();
    }
}
