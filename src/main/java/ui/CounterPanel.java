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

public class CounterPanel extends AbstractPanel {
    private static final String CounterPanelUI="img/UI/CounterPanelUI.png";
    public static float CounterHB_HEIGHT;
    public static float CounterHB_WIDTH;
    public static float Counter_Y;
    public static float Counter_X;
    public static Texture CounterBG;
    private Hitbox CounterHB;
    public static final UIStrings Counter_UIString;
    public static final String[] Counter_MSG;
    private final float OriginFontScale = 0.9F;
    private float fontScale = OriginFontScale;

    public CounterPanel() {
        super(Counter_X, Counter_Y, CounterHB_WIDTH, CounterHB_HEIGHT, Counter_X, Counter_Y, (Texture)null, true);
        this.CounterHB = new Hitbox(Counter_X, Counter_Y, CounterHB_WIDTH, CounterHB_HEIGHT);
    }

    public void EnlargeFontScale() {this.fontScale=OriginFontScale*2.0f;}

    public void updatePositions() {
        super.updatePositions();
        if (!this.isHidden) {
            this.CounterHB.update();
            if (fontScale != OriginFontScale) {
                fontScale = MathHelper.scaleLerpSnap(fontScale, OriginFontScale);
            }
        }
    }

    public void render(SpriteBatch sb) {
        if(!(AbstractDungeon.player instanceof MGR_character))
            return;
        MGR_character p=(MGR_character)AbstractDungeon.player;
        sb.setColor(Color.WHITE);
        if (!this.isHidden) {
            this.CounterHB.render(sb);
            sb.draw(CounterBG, this.CounterHB.x, this.CounterHB.y, this.CounterHB.width, this.CounterHB.height);
            String Counter_String = p.counter + "/" + p.counter_max;
            BitmapFont PanelFont=AbstractDungeon.player.getEnergyNumFont();
            PanelFont.getData().setScale(fontScale);
            FontHelper.renderFontCentered(sb, PanelFont, Counter_String, this.CounterHB.cX, this.CounterHB.cY, new Color(1.0F, 1.0F, 1.0F, 1.0F));
            if (this.CounterHB.hovered && !AbstractDungeon.isScreenUp && AbstractDungeon.getMonsters() != null && !AbstractDungeon.getMonsters().areMonstersDead()) {
                TipHelper.renderGenericTip((float)InputHelper.mX + 60.0F * Settings.scale, (float)InputHelper.mY + 100.0F * Settings.scale, Counter_MSG[0], Counter_MSG[1]+p.counter_max+Counter_MSG[2]+(p.counter_max-1)+Counter_MSG[3]);
                //this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.BLOCK.NAMES[0]), GameDictionary.keywords.get(GameDictionary.BLOCK.NAMES[0])));
            }
        }
    }

    static {
        CounterHB_HEIGHT = 144.0F * Settings.scale;
        CounterHB_WIDTH = 144.0F * Settings.scale;
        Counter_Y = 800.0F * Settings.scale;
        Counter_X = 880.0F* Settings.scale;
        CounterBG = ImageMaster.loadImage(CounterPanelUI);
        Counter_UIString = CardCrawlGame.languagePack.getUIString("CounterPanel");
        Counter_MSG = Counter_UIString.TEXT;
    }
}

