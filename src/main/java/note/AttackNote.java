package note;

import action.NoteDamageEnemyAction;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.LightningOrbPassiveEffect;
import power.StereoPlusPower;
import power.HarmonyFormPower;
import power.StereoPower;

public class AttackNote extends AbstractNote {
    public static final String ORB_ID = "MGR:Attack";
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    private static final float PI_DIV_16 = 0.19634955f;
    private static final float ORB_WAVY_DIST = 0.05f;
    private static final float PI_4 = 12.566371f;
    private static final float ORB_BORDER_SCALE = 1.2f;

    public AttackNote() {
        this.ID = ORB_ID;
        this.img = ImageMaster.loadImage("img/note/Attack.png");
        this.name = orbString.NAME;
        this.baseEvokeAmount = 2;
        this.evokeAmount = this.baseEvokeAmount;
        this.channelAnimTimer = 0.5f;
        this.forterate=2;
        this.angle = MathUtils.random(360.0f);
        myColor= CardHelper.getColor(249,0,0);
        updateDescription();
    }

    public void updateDescription() {
        applyForte();
        this.description = orbString.DESCRIPTION[0]+this.evokeAmount+orbString.DESCRIPTION[1]+this.forterate+orbString.DESCRIPTION[2];
    }

    @Override
    public void myApplyForte()
    {
        AbstractPower power = AbstractDungeon.player.getPower(POWER_ID);
        if (power != null)
        {
            this.evokeAmount = Math.max(0, this.baseEvokeAmount + power.amount*this.forterate);
        }
        else this.evokeAmount = this.baseEvokeAmount;
    }

    public void myEvoke()
    {
        boolean hasStereo=AbstractDungeon.player.hasPower(StereoPower.POWER_ID)||AbstractDungeon.player.hasPower(StereoPlusPower.POWER_ID);
        AbstractDungeon.actionManager.addToTop(new NoteDamageEnemyAction(this.evokeAmount,hasStereo));
    }

    public void playChannelSFX() {
        CardCrawlGame.sound.play("ORB_LIGHTNING_CHANNEL", 0.1f);
    }

    public AbstractNote makeCopy() {
        return new AttackNote();
    }
}
