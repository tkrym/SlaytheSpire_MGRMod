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
import java.util.Objects;

import javassist.expr.Instanceof;
import power.FortePower;

public abstract class AbstractNote extends AbstractOrb
{
    public static final String POWER_ID=FortePower.POWER_ID;
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

    @Override
    public void applyFocus()
    {
        this.applyForte();
    }

    public void applyForte()
    {
        AbstractPower power = AbstractDungeon.player.getPower(POWER_ID);
        if (power != null && !this.ID.equals("Plasma")) {
            this.evokeAmount = Math.max(0, this.baseEvokeAmount + power.amount);
        } else {
            this.evokeAmount = this.baseEvokeAmount;
        }
        this.basePassiveAmount=this.passiveAmount=0;
    }

    public static AbstractOrb getRandomNote() {
        ArrayList<AbstractOrb> orbs = new ArrayList();
        orbs.add(new Dark());
        orbs.add(new Frost());
        orbs.add(new Lightning());
        orbs.add(new Plasma());
        return orbs.get(AbstractDungeon.cardRandomRng.random(orbs.size() - 1));
    }

    @Override
    protected void renderText(SpriteBatch sb)
    {
        if(!(this instanceof EmptyNoteSlot))
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.evokeAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, this.c, this.fontScale);
    }

    @Override
    public void setSlot(int slotNum, int maxOrbs)
    {
        this.tX = AbstractDungeon.player.drawX;
        this.tY = 250.0F * Settings.scale + AbstractDungeon.player.drawY + AbstractDungeon.player.hb_h / 2.0F;
        this.tX+=((float)slotNum-((float)maxOrbs-1)/2)*135.0F*Settings.scale;
        this.hb.move(this.tX, this.tY);
    }
}
