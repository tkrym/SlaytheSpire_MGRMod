package effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import power.HarmonyFormPower;

public class NoteAboveCreatureEffect extends AbstractGameEffect {
    private float effectDuration;
    private float x;
    private float y;
    private float ratio;
    private float offsetY;
    private float ScaleSpeed;
    private Texture img;
    private float TARGET_OFFSET_Y;
    private float START_OFFSET_Y;

    public NoteAboveCreatureEffect(float x, float y, Texture img){this(x,y,img,20.0f*Settings.scale,80.0f*Settings.scale,0.4f);}

    public NoteAboveCreatureEffect(float x, float y, Texture img,float START_OFFSET_Y,float TARGET_OFFSET_Y,float ScaleSpeed)
    {
        this.img=img;
        this.effectDuration=0.3F;
        if(AbstractDungeon.player.hasPower(HarmonyFormPower.POWER_ID)) this.effectDuration=0.15f;
        this.duration=this.effectDuration;
        this.x=x;
        this.y=y;
        this.ScaleSpeed=ScaleSpeed;
        this.color = Color.WHITE.cpy();
        this.START_OFFSET_Y=START_OFFSET_Y;
        this.TARGET_OFFSET_Y=TARGET_OFFSET_Y;
        this.scale=Settings.scale;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.ratio=(this.effectDuration-this.duration)/this.effectDuration;
        this.offsetY=START_OFFSET_Y+(TARGET_OFFSET_Y-START_OFFSET_Y)*this.ratio;
        if (this.duration < 0.0F) this.isDone = true;
    }

    public void render(SpriteBatch sb) {
        float scale=0.8f+this.ratio*this.ScaleSpeed;
        Color tmpColor=this.color.cpy();
        tmpColor.a=this.color.a*(0.5f+this.ratio*0.5f);
        sb.setBlendFunction(770, 771);
        sb.setColor(tmpColor);
        sb.draw(this.img, this.x-48.0F, this.y-48.0F+this.offsetY, 48.0f, 48.0f, 96.0f, 96.0f, this.scale*scale, this.scale*scale, 0.0f, 0, 0, 96, 96, false, false);
    }

    public void dispose() {
    }
}

