package ui;

import character.MGR_character;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
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
    private float scale = 1.0F;
    private float glowAlpha;
    private Color glowColor;
    String Counter_DATA;

    public CounterPanel() {
        super(Counter_X, Counter_Y, CounterHB_WIDTH, CounterHB_HEIGHT, Counter_X, Counter_Y, (Texture)null, true);
        this.CounterHB = new Hitbox(Counter_X + CounterHB_HEIGHT * 2.0F + 16.0F * Settings.scale, Counter_Y - 30.0F * Settings.scale, CounterHB_WIDTH, CounterHB_HEIGHT);
        this.glowAlpha = 1.0F;
        this.glowColor = Color.WHITE.cpy();
    }

    public void updatePositions() {
        super.updatePositions();
        if (!this.isHidden) {
            this.CounterHB.update();
            this.scale = MathHelper.scaleLerpSnap(this.scale, Settings.scale);
        }

        this.glowAlpha += Gdx.graphics.getDeltaTime() * 3.0F;
        if (this.glowAlpha < 0.0F) {
            this.glowAlpha *= -1.0F;
        }

        float tmp = MathUtils.cos(this.glowAlpha);
        if (tmp < 0.0F) {
            this.glowColor.a = -tmp / 2.0F;
        } else {
            this.glowColor.a = tmp / 2.0F;
        }

    }

    public void render(SpriteBatch sb) {
        if(!(AbstractDungeon.player instanceof MGR_character))
            return;
        MGR_character p=(MGR_character)AbstractDungeon.player;
        sb.setColor(Color.WHITE);
        if (this.CounterHB.hovered) {
            this.scale = 1.2F * Settings.scale;
        }

        if (!this.isHidden) {
            this.CounterHB.render(sb);
            sb.draw(CounterBG, this.CounterHB.x, this.CounterHB.y, this.CounterHB.width, this.CounterHB.height);
            this.Counter_DATA = "-" + p.counter + "-";
            FontHelper.renderFontCentered(sb, AbstractDungeon.player.getEnergyNumFont(), this.Counter_DATA, this.CounterHB.cX, this.CounterHB.cY, new Color(1.0F, 1.0F, 1.0F, 1.0F));
            if (this.CounterHB.hovered && !AbstractDungeon.isScreenUp && AbstractDungeon.getMonsters() != null && !AbstractDungeon.getMonsters().areMonstersDead()) {
                TipHelper.renderGenericTip((float)InputHelper.mX + 60.0F * Settings.scale, (float)InputHelper.mY + 100.0F * Settings.scale, Counter_MSG[0], Counter_MSG[1]+p.counter_max+Counter_MSG[2]);
            }
        }

    }

    static {
        CounterHB_HEIGHT = 96.0F * Settings.scale;
        CounterHB_WIDTH = 96.0F * Settings.scale;
        Counter_Y = 308.0F * Settings.scale;
        Counter_X = 0.0F;
        CounterBG = ImageMaster.loadImage(CounterPanelUI);
        Counter_UIString = CardCrawlGame.languagePack.getUIString("CounterPanel");
        Counter_MSG = Counter_UIString.TEXT;
    }
}

