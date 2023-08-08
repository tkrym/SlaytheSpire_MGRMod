package effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class IveSeenEverythingEffect extends AbstractGameEffect
{
    private TextureAtlas.AtlasRegion img;
    private float x;
    private float y;
    private float vy;
    private float angle;
    private final float TargetScale = 10.0f;
    private final float AngelSpeed = 360.0f;

    public IveSeenEverythingEffect()
    {
        this.img = ImageMaster.EYE_ANIM_6;
        this.scale = TargetScale;
        this.startingDuration = 1.0F;
        this.duration = 0.0f;
        this.angle = 180.0f-this.startingDuration*AngelSpeed;
        this.color = this.getColorFromAngle(this.angle);
        this.color.a = 0;
        this.x = 880.0F * Settings.scale;
        this.y = 540.0F * Settings.scale;
        this.vy=0.0f;
    }

    public void update()
    {
        this.duration += Gdx.graphics.getDeltaTime();
        this.angle += Gdx.graphics.getDeltaTime() * AngelSpeed;
        if (this.duration > this.startingDuration)
        {
            this.isDone = true;
            return;
        }
        this.color = this.getColorFromAngle(this.angle);
        this.color.a=this.duration / this.startingDuration*0.7f;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(this.color);
        sb.setBlendFunction(770, 1);
        sb.draw(this.img, this.x, this.y+this.vy*this.scale, (float) this.img.packedWidth / 2.0F, (float) this.img.packedHeight / 2.0F, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale*Settings.scale, this.scale*Settings.scale, 0.0f);
        sb.draw(ImageMaster.BORDER_GLOW_2, 0.0F, 0.0F, (float)Settings.WIDTH, (float)Settings.HEIGHT);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private Color getColorFromAngle(float angle)
    {
        angle = (angle % 360.0f+360.0f)%360.0f;
        if (angle < 60.0f)
            return CardHelper.getColor(255.0f, 255.0f * angle / 60.0f, 0.0f);
        else if (angle < 120.0f)
            return CardHelper.getColor(255.0f * (120.0f - angle) / 60.0f, 255.0f, 0.0f);
        else if (angle < 180.0f)
            return CardHelper.getColor(0.0f, 255.0f, 255.0f * (angle - 120.0f) / 60.0f);
        else if (angle < 240.0f)
            return CardHelper.getColor(0.0f, 255.0f * (240.0f - angle) / 60.0f, 255.0f);
        else if (angle < 300.0f)
            return CardHelper.getColor(255.0f * (angle - 240.0f) / 60.0f, 0.0f, 255.0f);
        else return CardHelper.getColor(255.0f, 0.0f, 255.0f * (360.0f - angle) / 60.0f);
    }
}
