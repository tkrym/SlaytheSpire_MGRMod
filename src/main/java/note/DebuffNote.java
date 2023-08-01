package note;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbPassiveEffect;
import effect.NoteAboveCreatureEffect;

import java.util.Iterator;

public class DebuffNote extends AbstractNote {
    public static final String ORB_ID = "MGR:Debuff";
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    private static final float ORB_BORDER_SCALE = 1.2f;
    private float vfxTimer = 0.5f;
    private static final float VFX_INTERVAL_TIME = 0.25f;

    public DebuffNote() {
        this.ID = ORB_ID;
        this.img = ImageMaster.loadImage("img/note/Debuff.png");
        this.name = orbString.NAME;
        this.baseEvokeAmount = 1;
        this.evokeAmount = this.baseEvokeAmount;
        this.channelAnimTimer = 0.5f;
        this.angle = MathUtils.random(360.0f);
        this.forterate=2;
        myColor= CardHelper.getColor(117,117,117);
        updateDescription();
    }

    public void updateDescription() {
        applyForte();
        this.description = orbString.DESCRIPTION[0]+this.evokeAmount+orbString.DESCRIPTION[1]+this.forterate+orbString.DESCRIPTION[2];
    }

    public void myEvoke()
    {
        if(this.evokeAmount<=0) return;
        AbstractCreature p=AbstractDungeon.player;
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if(mo.isDeadOrEscaped()) continue;
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mo, p, new VulnerablePower(mo, this.evokeAmount, false), this.evokeAmount, true, AbstractGameAction.AttackEffect.NONE));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mo, p, new WeakPower(mo, this.evokeAmount, false), this.evokeAmount, true, AbstractGameAction.AttackEffect.NONE));
            AbstractDungeon.actionManager.addToTop(new VFXAction(new NoteAboveCreatureEffect(mo.hb.cX - mo.animX, mo.hb.cY + mo.hb.height / 2.0F - mo.animY, this.img, 20.0f*Settings.scale,80.0f*Settings.scale, 0.5f), Settings.ACTION_DUR_XFAST / 2.0f));
        }
    }

    public void playChannelSFX() {
        CardCrawlGame.sound.play("ORB_DARK_CHANNEL", 0.1f);
    }

    public AbstractNote makeCopy() {
        return new DebuffNote();
    }
}
