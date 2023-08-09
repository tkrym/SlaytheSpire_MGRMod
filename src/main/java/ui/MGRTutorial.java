package ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.ui.FtueTip;

public class MGRTutorial extends FtueTip
{
    private static final TutorialStrings tutorialStrings = CardCrawlGame.languagePack.getTutorialString("MGR:tutorial");
    public static final String[] TEXT = tutorialStrings.TEXT;
    public static final String[] LABEL = tutorialStrings.LABEL;
    private Texture img1 = ImageMaster.loadImage("img/tutorial/Tutorial1.png");
    private Texture img2 = ImageMaster.loadImage("img/tutorial/Tutorial2.png");
    private Texture img3 = ImageMaster.loadImage("img/tutorial/Tutorial3.png");
    private Color screen = Color.valueOf("1d172900");
    private float x;
    private float x1;
    private static final float X1_Offset = 400.0f;
    private float x2;
    private float targetX;
    private float startX;
    private float scrollTimer = 0.0F;
    private int CurrentPage = 1;
    private int MaxPages = 2;

    public MGRTutorial()
    {
        AbstractDungeon.player.releaseCard();
        if (AbstractDungeon.isScreenUp)
        {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = CurrentScreen.FTUE;
        AbstractDungeon.overlayMenu.showBlackScreen();
        this.x = 0.0F;
        this.x1 = X1_Offset * Settings.scale;
        this.x2 = this.x1 + (float) Settings.WIDTH;
        AbstractDungeon.overlayMenu.proceedButton.show();
        AbstractDungeon.overlayMenu.proceedButton.setLabel(LABEL[0]);
    }

    public void update()
    {
        AbstractDungeon.overlayMenu.proceedButton.setLabel(LABEL[0]);
        if (this.CurrentPage >= this.MaxPages)
            AbstractDungeon.overlayMenu.proceedButton.setLabel(LABEL[1]);
        if (this.screen.a != 0.8F)
            this.screen.a = Math.max(this.screen.a + Gdx.graphics.getDeltaTime(), 0.8f);
        if (AbstractDungeon.overlayMenu.proceedButton.isHovered && InputHelper.justClickedLeft || CInputActionSet.proceed.isJustPressed())
        {
            CInputActionSet.proceed.unpress();
            if (this.CurrentPage >= this.MaxPages)
            {
                CardCrawlGame.sound.play("DECK_CLOSE");
                AbstractDungeon.closeCurrentScreen();
                AbstractDungeon.overlayMenu.proceedButton.hide();
                AbstractDungeon.effectList.clear();
            }
            ++this.CurrentPage;
            this.startX = this.x;
            this.targetX = (float) ((1 - this.CurrentPage) * Settings.WIDTH);
            this.scrollTimer = 0.4F;
            if (this.CurrentPage >= this.MaxPages)
                AbstractDungeon.overlayMenu.proceedButton.setLabel(LABEL[1]);
        }

        if (this.scrollTimer >= 0.0F) this.scrollTimer = Math.max(this.scrollTimer - Gdx.graphics.getDeltaTime(), 0.0f);

        this.x = Interpolation.fade.apply(this.targetX, this.startX, this.scrollTimer / 0.3F);
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(this.screen);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, (float) Settings.WIDTH, (float) Settings.HEIGHT);
        sb.setColor(Color.WHITE);
        sb.draw(this.img2, this.x + this.x1 - (float) (this.img2.getWidth() / 2), (float) Settings.HEIGHT / 2.0F - (float) (this.img2.getHeight() / 2),
                (float) (this.img2.getWidth() / 2), (float) (this.img2.getHeight() / 2),
                (float) this.img2.getWidth(), (float) this.img2.getHeight(),
                Settings.xScale * 1.3f, Settings.yScale * 1.3f,
                0.0F, 0, 0,
                this.img2.getWidth(), this.img2.getHeight(),
                false, false);
        sb.draw(this.img3, this.x + this.x1 - (float) (this.img3.getWidth() / 2) + Settings.xScale * 400.0f, (float) Settings.HEIGHT / 3.0F - (float) (this.img3.getHeight() / 2) - Settings.yScale * 20.0f, (float) (this.img3.getWidth() / 2), (float) (this.img3.getHeight() / 2), (float) this.img3.getWidth(), (float) this.img3.getHeight(), Settings.xScale * 1.2f, Settings.yScale * 1.2f, 0.0F, 0, 0, this.img3.getWidth(), this.img3.getHeight(), false, false);
        sb.draw(this.img1, this.x + this.x2 - (float) (this.img1.getWidth() / 2f), (float) Settings.HEIGHT / 2.0F - (float) (this.img1.getHeight() / 2), (float) (this.img1.getWidth() / 2), (float) (this.img1.getHeight() / 2), (float) this.img1.getWidth(), (float) this.img1.getHeight(), Settings.xScale * 1.2f, Settings.yScale * 1.2f, 0.0F, 0, 0, this.img1.getWidth(), this.img1.getHeight(), false, false);
        FontHelper.renderSmartText(sb, FontHelper.panelNameFont, TEXT[0], this.x + this.x1 + (float) (this.img1.getWidth() / 2 + 50) * Settings.xScale, (float) Settings.HEIGHT / 2.0F - FontHelper.getSmartHeight(FontHelper.panelNameFont, TEXT[0], 1000.0F * Settings.xScale, 40.0F * Settings.yScale) / 2.0F + 75.0f*Settings.yScale, 1000.0F * Settings.xScale, 40.0F * Settings.yScale, Settings.CREAM_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.panelNameFont, TEXT[1], this.x + this.x2 + (float) (this.img2.getWidth() / 2 + 250) * Settings.xScale, (float) Settings.HEIGHT / 2.0F - FontHelper.getSmartHeight(FontHelper.panelNameFont, TEXT[1], 1000.0F * Settings.xScale, 40.0F * Settings.yScale) / 2.0F+ 75.0f*Settings.yScale, 1000.0F * Settings.xScale, 40.0F * Settings.yScale, Settings.CREAM_COLOR);
        FontHelper.renderFontCenteredWidth(sb, FontHelper.panelNameFont, LABEL[2], (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F - 360.0F * Settings.scale, Settings.GOLD_COLOR);
        FontHelper.renderFontCenteredWidth(sb, FontHelper.tipBodyFont, LABEL[3] + this.CurrentPage + "/" + this.MaxPages + LABEL[4], (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F - 400.0F * Settings.scale, Settings.CREAM_COLOR);
        AbstractDungeon.overlayMenu.proceedButton.render(sb);
    }
}
