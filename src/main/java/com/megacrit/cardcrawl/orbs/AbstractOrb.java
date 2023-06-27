//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.BobEffect;
import java.util.ArrayList;
import java.util.Objects;

public abstract class AbstractOrb {
    public String name;
    public String description;
    public String ID;
    protected ArrayList<PowerTip> tips = new ArrayList();
    public int evokeAmount = 0;
    public int passiveAmount = 0;
    protected int baseEvokeAmount = 0;
    protected int basePassiveAmount = 0;
    public float cX = 0.0F;
    public float cY = 0.0F;
    public float tX;
    public float tY;
    protected Color c;
    protected Color shineColor;
    protected static final int W = 96;
    public Hitbox hb;
    protected Texture img;
    protected BobEffect bobEffect;
    protected static final float NUM_X_OFFSET;
    protected static final float NUM_Y_OFFSET;
    protected float angle;
    protected float scale;
    protected float fontScale;
    protected boolean showEvokeValue;
    protected static final float CHANNEL_IN_TIME = 0.5F;
    protected float channelAnimTimer;

    public AbstractOrb() {
        this.c = Settings.CREAM_COLOR.cpy();
        this.shineColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        this.hb = new Hitbox(96.0F * Settings.scale, 96.0F * Settings.scale);
        this.img = null;
        this.bobEffect = new BobEffect(3.0F * Settings.scale, 3.0F);
        this.fontScale = 0.7F;
        this.showEvokeValue = false;
        this.channelAnimTimer = 0.5F;
    }

    public abstract void updateDescription();

    public abstract void onEvoke();

    public static AbstractOrb getRandomOrb(boolean useCardRng) {
        ArrayList<AbstractOrb> orbs = new ArrayList();
        orbs.add(new Dark());
        orbs.add(new Frost());
        orbs.add(new Lightning());
        orbs.add(new Plasma());
        return useCardRng ? (AbstractOrb)orbs.get(AbstractDungeon.cardRandomRng.random(orbs.size() - 1)) : (AbstractOrb)orbs.get(MathUtils.random(orbs.size() - 1));
    }

    public void onStartOfTurn() {
    }

    public void onEndOfTurn() {
    }

    public void applyFocus() {
        AbstractPower power = AbstractDungeon.player.getPower("Focus");
        if (power != null && !this.ID.equals("Plasma")) {
            this.passiveAmount = Math.max(0, this.basePassiveAmount + power.amount);
            this.evokeAmount = Math.max(0, this.baseEvokeAmount + power.amount);
        } else {
            this.passiveAmount = this.basePassiveAmount;
            this.evokeAmount = this.baseEvokeAmount;
        }

    }

    public static int applyLockOn(AbstractCreature target, int dmg) {
        int retVal = dmg;
        if (target.hasPower("Lockon")) {
            retVal = (int)((float)dmg * 1.5F);
        }

        return retVal;
    }

    public abstract AbstractOrb makeCopy();

    public void update() {
        this.hb.update();
        if (this.hb.hovered) {
            TipHelper.renderGenericTip(this.tX + 96.0F * Settings.scale, this.tY + 64.0F * Settings.scale, this.name, this.description);
        }

        this.fontScale = MathHelper.scaleLerpSnap(this.fontScale, 0.7F);
    }

    public void updateAnimation() {
        this.bobEffect.update();
        this.cX = MathHelper.orbLerpSnap(this.cX, AbstractDungeon.player.animX + this.tX);
        this.cY = MathHelper.orbLerpSnap(this.cY, AbstractDungeon.player.animY + this.tY);
        if (this.channelAnimTimer != 0.0F) {
            this.channelAnimTimer -= Gdx.graphics.getDeltaTime();
            if (this.channelAnimTimer < 0.0F) {
                this.channelAnimTimer = 0.0F;
            }
        }

        this.c.a = Interpolation.pow2In.apply(1.0F, 0.01F, this.channelAnimTimer / 0.5F);
        this.scale = Interpolation.swingIn.apply(Settings.scale, 0.01F, this.channelAnimTimer / 0.5F);
    }

    public void setSlot(int slotNum, int maxOrbs) {
        if (Objects.equals(AbstractDungeon.player.name, "MGR"))
        {
            this.tX = AbstractDungeon.player.drawX;
            this.tY = 250.0F * Settings.scale + AbstractDungeon.player.drawY + AbstractDungeon.player.hb_h / 2.0F;
            this.tX+=((float)slotNum-((float)maxOrbs-1)/2)*135.0F*Settings.scale;
            this.hb.move(this.tX, this.tY);
        }
        else
        {
            float dist = 160.0F * Settings.scale + (float)maxOrbs * 10.0F * Settings.scale;
            float angle = 100.0F + (float)maxOrbs * 12.0F;
            float offsetAngle = angle / 2.0F;
            angle *= (float)slotNum / ((float)maxOrbs - 1.0F);
            angle += 90.0F - offsetAngle;
            this.tX = dist * MathUtils.cosDeg(angle) + AbstractDungeon.player.drawX;
            this.tY = dist * MathUtils.sinDeg(angle) + AbstractDungeon.player.drawY + AbstractDungeon.player.hb_h / 2.0F;
            if (maxOrbs == 1) {
                this.tX = AbstractDungeon.player.drawX;
                this.tY = 160.0F * Settings.scale + AbstractDungeon.player.drawY + AbstractDungeon.player.hb_h / 2.0F;
            }
            this.hb.move(this.tX, this.tY);
        }
    }

    public abstract void render(SpriteBatch var1);

    protected void renderText(SpriteBatch sb) {
        if (!(this instanceof EmptyOrbSlot)) {
            if (this.showEvokeValue) {
                FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.evokeAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, new Color(0.2F, 1.0F, 1.0F, this.c.a), this.fontScale);
            } else {
                FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.passiveAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, this.c, this.fontScale);
            }
        }

    }

    public void triggerEvokeAnimation() {
    }

    public void showEvokeValue() {
        this.showEvokeValue = true;
        this.fontScale = 1.5F;
    }

    public void hideEvokeValues() {
        this.showEvokeValue = false;
    }

    public abstract void playChannelSFX();

    static {
        NUM_X_OFFSET = 20.0F * Settings.scale;
        NUM_Y_OFFSET = -12.0F * Settings.scale;
    }
}
