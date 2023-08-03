package ui;

import character.MGR_character;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.panels.AbstractPanel;
import patch.EnergyFontPatch;

public class CounterPanel extends AbstractPanel
{
    private static final String CounterPanelUI = "img/UI/CounterPanelUI.png";
    private static final String CounterPanelUIBG = "img/UI/CounterPanelUIBG.png";
    public static float CounterHB_HEIGHT = 144.0F * Settings.scale;
    public static float CounterHB_WIDTH = 144.0F * Settings.scale;
    public static float Counter_X = 880.0F * Settings.scale;
    public static float Counter_Y = 800.0F * Settings.scale;
    public static Texture CounterBG = ImageMaster.loadImage(CounterPanelUI);
    public static Texture CounterBGBG = ImageMaster.loadImage(CounterPanelUIBG);
    private Hitbox CounterHB;
    public static final UIStrings Counter_UIString = CardCrawlGame.languagePack.getUIString("MGR:CounterPanel");
    public static final String[] Counter_MSG = Counter_UIString.TEXT;
    private final float OriginFontScale = 0.9F;
    private float fontScale = OriginFontScale;
    private float angle;

    public CounterPanel()
    {
        super(Counter_X, Counter_Y, CounterHB_WIDTH, CounterHB_HEIGHT, Counter_X, Counter_Y, (Texture) null, true);
        this.CounterHB = new Hitbox(Counter_X, Counter_Y, CounterHB_WIDTH, CounterHB_HEIGHT);
        this.angle=0.0f;
    }

    public void EnlargeFontScale() {this.fontScale = OriginFontScale * 2.0f;}

    public void updatePositions()
    {
        super.updatePositions();
        if (!this.isHidden)
        {
            this.CounterHB.update();
            if (fontScale != OriginFontScale)
            {
                fontScale = MathHelper.scaleLerpSnap(fontScale, OriginFontScale);
            }
        }
        this.angle+=Gdx.graphics.getDeltaTime();
    }

    public void render(SpriteBatch sb)
    {
        if (!(AbstractDungeon.player instanceof MGR_character))
            return;
        MGR_character p = (MGR_character) AbstractDungeon.player;
        if (!this.isHidden)
        {
            this.CounterHB.render(sb);
            float scale = 1 + MathUtils.sin(this.angle*1.5f) * 0.075F + 0.075F;
            Color c=Color.WHITE.cpy();
            c.a=c.a*0.8f/(3*scale-2.0f);
            sb.setColor(c);
            sb.setBlendFunction(770, 1);
            float deltaX=this.CounterHB.width*(scale-1.0f)/2.0f;
            float deltaY=this.CounterHB.height*(scale-1.0f)/2.0f;
            sb.draw(CounterBGBG, this.CounterHB.x-deltaX, this.CounterHB.y-deltaY, this.CounterHB.width*scale, this.CounterHB.height*scale);
            sb.setColor(Color.WHITE);
            sb.setBlendFunction(770, 771);
            sb.draw(CounterBG, this.CounterHB.x, this.CounterHB.y, this.CounterHB.width, this.CounterHB.height);
            String Counter_String = p.counter + "/" + p.counter_max;
            BitmapFont PanelFont = EnergyFontPatch.energyNumFontMGR_blue;
            PanelFont.getData().setScale(fontScale);
            FontHelper.renderFontCentered(sb, PanelFont, Counter_String, this.CounterHB.cX, this.CounterHB.cY, new Color(1.0F, 1.0F, 1.0F, 1.0F));
            if (this.CounterHB.hovered && !AbstractDungeon.isScreenUp && AbstractDungeon.getMonsters() != null && !AbstractDungeon.getMonsters().areMonstersBasicallyDead())
                TipHelper.renderGenericTip((float) InputHelper.mX + 60.0F * Settings.scale, (float) InputHelper.mY + 100.0F * Settings.scale, Counter_MSG[0], Counter_MSG[1] + p.counter_max + Counter_MSG[2] + (p.counter_max - 1) + Counter_MSG[3]);
        }
    }

}

