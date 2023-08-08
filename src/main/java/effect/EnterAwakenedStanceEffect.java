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
import com.megacrit.cardcrawl.vfx.stance.DivinityStanceChangeParticle;

public class EnterAwakenedStanceEffect extends AbstractGameEffect
{
    private float x;
    private float y;
    private float vY;
    private AbstractCreature target;
    private TextureAtlas.AtlasRegion img;
    private boolean LineEffectAdded;

    public EnterAwakenedStanceEffect()
    {
        this.img = ImageMaster.EYE_ANIM_0;
        this.scale = 5.0F;
        this.startingDuration = 1.14f;
        this.duration = 0;
        this.scale *= Settings.scale;
        this.target = AbstractDungeon.player;
        this.color = CardHelper.getColor(16, 225, 241);
        this.color.a = 0;
        this.x = this.target.hb.cX;
        this.y = this.target.hb.cY;
        this.renderBehind = false;
        this.rotation = 0.0f;
        this.x -= (float) this.img.packedWidth / 2.0F;
        this.y -= (float) this.img.packedHeight / 2.0F;
        this.LineEffectAdded = false;
    }

    public void update()
    {
        this.color.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration / this.startingDuration);
        if (this.duration > this.startingDuration * 0.7F)
        {
            this.img = ImageMaster.EYE_ANIM_6;
        }
        else if (this.duration > this.startingDuration * 0.6F)
        {
            /*if (!this.LineEffectAdded)
            {
                this.LineEffectAdded = true;
                for (int i = 1; i <= 20; ++i)
                    AbstractDungeon.effectsQueue.add(new EnterAwakenedStanceLineEffect());
            }*/
            this.img = ImageMaster.EYE_ANIM_5;
        }
        else if (this.duration > this.startingDuration * 0.5F)
        {
            this.img = ImageMaster.EYE_ANIM_4;
        }
        else if (this.duration > this.startingDuration * 0.4F)
        {
            this.vY = 3.0F * Settings.scale;
            this.img = ImageMaster.EYE_ANIM_3;
        }
        else if (this.duration > this.startingDuration * 0.3F)
        {
            this.vY = 4.0F * Settings.scale;
            this.img = ImageMaster.EYE_ANIM_2;
        }
        else if (this.duration > this.startingDuration * 0.2F)
        {
            this.vY = 8.0F * Settings.scale;
            this.img = ImageMaster.EYE_ANIM_1;
        }
        else
        {
            this.vY = 12.0F * Settings.scale;
            this.img = ImageMaster.EYE_ANIM_0;
        }

        this.duration += Gdx.graphics.getDeltaTime();
        if (this.duration > this.startingDuration)
        {
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(this.color);
        sb.setBlendFunction(770, 1);
        sb.draw(this.img, this.x, this.y + this.vY*5.0f, (float) this.img.packedWidth / 2.0F, (float) this.img.packedHeight / 2.0F, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale, this.scale, this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }
}

