package effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import power.ClimaxPower;

public class ClimaxEffect extends AbstractGameEffect
{
    private final Texture img = ImageMaster.SPOTLIGHT_VFX;
    private final float S1 = 1.5f;
    private final float R1 = 45.0f;
    private final float S2 = 9.0f;
    private final float R2 = 315.0f;

    public ClimaxEffect()
    {
        this.duration = 0;
        this.color = Color.WHITE.cpy();
    }

    public void update()
    {
        if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT || !AbstractDungeon.player.hasPower(ClimaxPower.POWER_ID))
        {
            this.isDone = true;
            return;
        }
        if (this.duration < 0.36F)
        {
            this.duration += Gdx.graphics.getDeltaTime();
            this.color.a = this.duration;
        }
        else this.color.a = 0.36f;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(this.color);
        sb.setBlendFunction(770, 1);
        sb.draw(this.img, 0.0f, Settings.HEIGHT*0.3f,
                (float) this.img.getWidth() / 2.0F, (float) this.img.getHeight() / 2.0F,
                (float) this.img.getWidth(), (float) this.img.getHeight(),
                this.S1*Settings.xScale, this.S2*Settings.yScale,
                this.R1, 0, 0,
                this.img.getWidth(), this.img.getHeight(),
                false, false);
        sb.draw(this.img, Settings.WIDTH*0.6f, Settings.HEIGHT*0.3f,
                (float) this.img.getWidth() / 2.0F, (float) this.img.getHeight() / 2.0F,
                (float) this.img.getWidth(), (float) this.img.getHeight(),
                this.S1*Settings.xScale,
                this.S2*Settings.yScale,
                this.R2, 0, 0,
                this.img.getWidth(), this.img.getHeight(),
                true, false);
        sb.draw(this.img, (float) Settings.WIDTH*0.12f, 0.0f, (float) Settings.WIDTH*0.7f, (float) Settings.HEIGHT*1.2f);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }
}


