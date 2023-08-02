package effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class MGRVictoryEffect extends AbstractGameEffect
{
    private static final String VictoryIMGPath="img/select/myfigure.png";
    public static float IMG_WIDTH = 1920.0F * Settings.xScale;
    public static float IMG_HEIGHT = 1080.0F * Settings.yScale;
    public static float IMG_X = 0.0F * Settings.xScale;
    public static float IMG_Y = -40.0F * Settings.yScale;
    public static Texture VictoryIMG = ImageMaster.loadImage(VictoryIMGPath);

    public MGRVictoryEffect()
    {
    }

    public void update() {}

    public void render(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        sb.draw(VictoryIMG, IMG_X, IMG_Y, IMG_WIDTH,IMG_HEIGHT);
    }

    public void dispose()
    {
    }
}

