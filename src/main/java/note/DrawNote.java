package note;

import action.NoteDamageEnemyAction;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DarkOrbEvokeAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.vfx.combat.*;
import effect.NoteAboveCreatureEffect;
import power.StereoPlusPower;
import power.StereoPower;

public class DrawNote extends AbstractNote {
    public static final String ORB_ID = "MGR:Draw";
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    private static final float ORB_BORDER_SCALE = 1.2f;
    private float vfxTimer = 0.5f;
    private static final float VFX_INTERVAL_TIME = 0.25f;

    public DrawNote() {
        this.ID = ORB_ID;
        this.img = ImageMaster.loadImage("img/note/Draw.png");
        this.name = orbString.NAME;
        this.baseEvokeAmount = 1;
        this.evokeAmount = this.baseEvokeAmount;
        this.channelAnimTimer = 0.5f;
        this.forterate=4;
        this.angle = MathUtils.random(360.0f);
        myColor= CardHelper.getColor(0,178,249);
        updateDescription();
    }

    public void updateDescription() {
        applyForte();
        this.description = orbString.DESCRIPTION[0]+this.evokeAmount+orbString.DESCRIPTION[1]+this.forterate+orbString.DESCRIPTION[2];
    }

    public void myEvoke() {
        if(this.evokeAmount<=0) return;
        AbstractPlayer p=AbstractDungeon.player;
        AbstractDungeon.actionManager.addToTop(new DrawCardAction(p,this.evokeAmount));
        AbstractDungeon.actionManager.addToTop(new VFXAction(new NoteAboveCreatureEffect(p.hb.cX - p.animX, p.hb.cY + p.hb.height / 2.0F - p.animY, this.img), Settings.ACTION_DUR_XFAST/1.5f));
        boolean hasStereoPlus=AbstractDungeon.player.hasPower(StereoPlusPower.POWER_ID);
        if(hasStereoPlus)
        {
            AbstractDungeon.actionManager.addToTop(new NoteDamageEnemyAction(this.evokeAmount<<1,true,this.img));
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player,this.evokeAmount<<1));
        }
    }

    public AbstractNote makeCopy() {
        return new DrawNote();
    }
}
