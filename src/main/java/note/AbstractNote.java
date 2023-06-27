package note;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.BobEffect;
import java.util.ArrayList;
import power.FortePower;

public abstract class AbstractNote extends AbstractOrb
{
    public AbstractNote() {
        this.c = Settings.CREAM_COLOR.cpy();
        this.shineColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        this.hb = new Hitbox(96.0F * Settings.scale, 96.0F * Settings.scale);
        this.img = null;
        this.bobEffect = new BobEffect(3.0F * Settings.scale, 3.0F);
        this.fontScale = 0.7F;
        this.channelAnimTimer = 0.5F;
        this.passiveAmount=0;
        this.basePassiveAmount=0;
    }

    public static AbstractOrb getRandomNote()
    {
        ArrayList<AbstractOrb> notes = new ArrayList();
        int p=AbstractDungeon.cardRandomRng.random(99);
        if(p<35) return new Lightning();
        else if(p<70) return new Frost();
        else if(p<85) return new Plasma();
        else return new Dark();
    }

    public void onStartOfTurn() {
    }

    public void onEndOfTurn() {
    }

    public void applyForte() {
        AbstractPower power = AbstractDungeon.player.getPower(FortePower.POWER_ID);
        if (power != null && !this.ID.equals("Plasma")) {
            this.evokeAmount = Math.max(0, this.baseEvokeAmount + power.amount);
        } else {
            this.evokeAmount = this.baseEvokeAmount;
        }

    }

    public abstract AbstractNote makeCopy();

    public void update()
    {
        this.hb.update();
        if (this.hb.hovered)
            TipHelper.renderGenericTip(this.tX + 96.0F * Settings.scale, this.tY + 64.0F * Settings.scale, this.name, this.description);
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

    public void setSlot(int slotNum, int maxNotes) {
        this.tX = AbstractDungeon.player.drawX;
        this.tY = 160.0F * Settings.scale + AbstractDungeon.player.drawY + AbstractDungeon.player.hb_h / 2.0F;
        this.tX+=(float)(slotNum-(maxNotes+1)/2)*100.0F*Settings.scale;
        this.hb.move(this.tX, this.tY);
    }

    public abstract void render(SpriteBatch var1);

    protected void renderText(SpriteBatch sb)
    {
        if (!(this instanceof EmptyNoteSlot))
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.evokeAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, this.c, this.fontScale);
    }

    public void triggerEvokeAnimation() {
    }

    public abstract void playChannelSFX();

}
