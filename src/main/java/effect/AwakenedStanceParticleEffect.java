package effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import power.HarmonyFormPower;

public class AwakenedStanceParticleEffect extends AbstractGameEffect
{
    private float x;
    private float y;
    private float vY;
    private float dur_div2;
    private AbstractCreature target;
    private TextureAtlas.AtlasRegion img;

    public AwakenedStanceParticleEffect(int index)
    {
        this.img = ImageMaster.EYE_ANIM_0;
        this.scale = 1.0F;
        this.startingDuration = MathUtils.random(1.0F, 1.5F);
        this.duration = this.startingDuration;
        this.dur_div2 = this.duration / 2.0F;
        this.target = AbstractDungeon.player;
        this.color = new Color(MathUtils.random(0.0F, 0.2F), MathUtils.random(0.75F, 0.95F), MathUtils.random(0.9F, 1.0F), 0.0F);
        if(this.target==null) this.isDone=true;
        else
        {
            this.x = this.target.hb.cX + (index - 2.5f) * this.target.hb.width * 0.4f;
            this.x += MathUtils.random(-5.0F * Settings.scale, 5.0f * Settings.scale);
            this.y = this.target.hb.cY + this.target.hb.height * 0.2f;
            this.y += MathUtils.random(3.0F * Settings.scale, -3.0F * Settings.scale);
            this.renderBehind = false;
            if (index == 1) this.rotation = -20.0f;
            else if (index == 2) this.rotation = -7.0f;
            else if (index == 3) this.rotation = 7.0f;
            else if (index == 4) this.rotation = 20.0f;
            else this.rotation = MathUtils.random(12.0F, 6.0F) * (this.x > this.target.hb.cX ? -1 : 1);
            this.rotation += MathUtils.random(-2.0f, 2.0f);
            this.x -= (float) this.img.packedWidth / 2.0F;
            this.y -= (float) this.img.packedHeight / 2.0F;
        }
    }

    public AwakenedStanceParticleEffect(AbstractCreature target)
    {
        this.img = ImageMaster.EYE_ANIM_0;
        this.scale = MathUtils.random(1.0F, 1.5F);
        this.startingDuration = this.scale + 0.8F;
        this.duration = this.startingDuration;
        this.dur_div2 = this.duration / 2.0F;
        this.target = target;
        this.color = new Color(MathUtils.random(0.0F, 0.2F), MathUtils.random(0.75F, 0.95F), MathUtils.random(0.9F, 1.0F), 0.0F);
        if(this.target==null) this.isDone=true;
        else
        {
            this.x = this.target.hb.cX + MathUtils.random(-this.target.hb.width / 2.0F - 50.0F * Settings.scale, this.target.hb.width / 2.0F + 50.0F * Settings.scale);
            this.y = this.target.hb.cY + MathUtils.random(-this.target.hb.height / 2.0F + 10.0F * Settings.scale, this.target.hb.height / 2.0F - 20.0F * Settings.scale);
            this.renderBehind = MathUtils.randomBoolean();
            this.rotation = MathUtils.random(12.0F, 6.0F);
            if (this.x > this.target.hb.cX) this.rotation = -this.rotation;
            this.x -= (float) this.img.packedWidth / 2.0F;
            this.y -= (float) this.img.packedHeight / 2.0F;
        }
    }

    public void update()
    {
        if (this.duration > this.dur_div2)
        {
            this.color.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - this.dur_div2) / this.dur_div2);
        }
        else
        {
            this.color.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration / this.dur_div2);
        }

        if (this.duration > this.startingDuration * 0.85F)
        {
            this.vY = 12.0F * Settings.scale;
            this.img = ImageMaster.EYE_ANIM_0;
        }
        else if (this.duration > this.startingDuration * 0.8F)
        {
            this.vY = 8.0F * Settings.scale;
            this.img = ImageMaster.EYE_ANIM_1;
        }
        else if (this.duration > this.startingDuration * 0.75F)
        {
            this.vY = 4.0F * Settings.scale;
            this.img = ImageMaster.EYE_ANIM_2;
        }
        else if (this.duration > this.startingDuration * 0.7F)
        {
            this.vY = 3.0F * Settings.scale;
            this.img = ImageMaster.EYE_ANIM_3;
        }
        else if (this.duration > this.startingDuration * 0.65F)
        {
            this.img = ImageMaster.EYE_ANIM_4;
        }
        else if (this.duration > this.startingDuration * 0.6F)
        {
            this.img = ImageMaster.EYE_ANIM_5;
        }
        else if (this.duration > this.startingDuration * 0.55F)
        {
            this.img = ImageMaster.EYE_ANIM_6;
        }
        else if (this.duration > this.startingDuration * 0.38F)
        {
            this.img = ImageMaster.EYE_ANIM_5;
        }
        else if (this.duration > this.startingDuration * 0.3F)
        {
            this.img = ImageMaster.EYE_ANIM_4;
        }
        else if (this.duration > this.startingDuration * 0.25F)
        {
            this.vY = 3.0F * Settings.scale;
            this.img = ImageMaster.EYE_ANIM_3;
        }
        else if (this.duration > this.startingDuration * 0.2F)
        {
            this.vY = 4.0F * Settings.scale;
            this.img = ImageMaster.EYE_ANIM_2;
        }
        else if (this.duration > this.startingDuration * 0.15F)
        {
            this.vY = 8.0F * Settings.scale;
            this.img = ImageMaster.EYE_ANIM_1;
        }
        else
        {
            this.vY = 12.0F * Settings.scale;
            this.img = ImageMaster.EYE_ANIM_0;
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F)
        {
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(this.color);
        sb.setBlendFunction(770, 1);
        sb.draw(this.img, this.x, this.y + this.vY*this.scale, (float) this.img.packedWidth / 2.0F, (float) this.img.packedHeight / 2.0F, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale*Settings.scale, this.scale*Settings.scale, this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }
}

