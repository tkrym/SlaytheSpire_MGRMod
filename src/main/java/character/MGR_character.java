package character;


import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.RandomXS128;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.beyond.SpireHeart;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import path.ModClassEnum;
import path.AbstractCardEnum;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MGR_character extends CustomPlayer {
    private static final int ENERGY_PER_TURN = 3;
    private static final String MGR_SHOULDER_2 = "img/character/shoulder2.png";
    private static final String MGR_SHOULDER_1 = "img/character/shoulder1.png";
    private static final String MGR_CORPSE = "img/character/fallen.png";
    private static final String MGR_STAND = "img/character/stand.png";
    private static final String[] ORB_TEXTURES = new String[] { "img/UI/EPanel/layer5.png", "img/UI/EPanel/layer4.png", "img/UI/EPanel/layer3.png", "img/UI/EPanel/layer2.png", "img/UI/EPanel/layer1.png", "img/UI/EPanel/layer0.png", "img/UI/EPanel/layer5d.png", "img/UI/EPanel/layer4d.png", "img/UI/EPanel/layer3d.png", "img/UI/EPanel/layer2d.png", "img/UI/EPanel/layer1d.png" };
    private static final String ORB_VFX = "img/UI/energyBlueVFX.png";
    private static final float[] LAYER_SPEED = new float[] { -40.0F, -32.0F, 20.0F, -20.0F, 0.0F, -10.0F, -8.0F, 5.0F, -5.0F, 0.0F };
    private static final int STARTING_HP = 67;
    private static final int MAX_HP = 67;
    private static final int STARTING_GOLD = 999;
    private static final int HAND_SIZE = 0;
    private static final int ASCENSION_MAX_HP_LOSS = 7;
    public static final Color SILVER = CardHelper.getColor(255, 255, 255);

    public MGR_character(String name) {
        super(name, ModClassEnum.MGR_CLASS, ORB_TEXTURES, ORB_VFX, LAYER_SPEED, (String)null, (String)null);
        this.dialogX = this.drawX + 0.0F * Settings.scale;
        this.dialogY = this.drawY + 220.0F * Settings.scale;
        initializeClass(MGR_STAND, MGR_SHOULDER_2, MGR_SHOULDER_1, MGR_CORPSE,
                getLoadout(),
                0.0F, 5.0F, 240.0F, 300.0F,
                new EnergyManager(ENERGY_PER_TURN));
    }

    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add("Strike_MGR");
        retVal.add("Strike_MGR");
        retVal.add("Defend_MGR");
        retVal.add("Defend_MGR");
        retVal.add("SpBullet");
        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add("TheFirst");
        UnlockTracker.markRelicAsSeen("TheFirst");
        return retVal;
    }

    public CharSelectInfo getLoadout() {
        String title="";
        String flavor="";
        if (Settings.language == Settings.GameLanguage.ZHS) {
            title = "MGR_Test";
            flavor = "测试1 NL 测试2";
        } else if (Settings.language == Settings.GameLanguage.ZHT) {
        } else {
        }

        return new CharSelectInfo(title, flavor, STARTING_HP, MAX_HP,HAND_SIZE , STARTING_GOLD, ASCENSION_MAX_HP_LOSS, this, getStartingRelics(), getStartingDeck(), false);
    }

    public String getTitle(PlayerClass playerClass) {
        String title="";
        if (Settings.language == Settings.GameLanguage.ZHS) {
            title = "MGR";
        } else if (Settings.language == Settings.GameLanguage.ZHT) {
            title = "MGR";
        } else {
            title = "MGR";
        }

        return title;
    }

    public String getLocalizedCharacterName() {
        String char_name;
        if (Settings.language == Settings.GameLanguage.ZHS) {
            char_name = "MGR";
        } else if (Settings.language == Settings.GameLanguage.ZHT) {
            char_name = "MGR";
        } else {
            char_name = "MGR";
        }
        return char_name;
    }

    public List<CutscenePanel> getCutscenePanels() {
        List<CutscenePanel> panels = new ArrayList();
        panels.add(new CutscenePanel("img/victory/part_1.png", "ATTACK_FIRE"));
        panels.add(new CutscenePanel("img/victory/part_2.png"));
        panels.add(new CutscenePanel("img/victory/part_3.png"));
        return panels;
    }

    public AbstractCard.CardColor getCardColor() {return AbstractCardEnum.MGR_COLOR;}

    public Color getCardRenderColor() {return SILVER;}

    public AbstractCard getStartCardForEvent() {return null;}

    public Color getCardTrailColor() {return SILVER;}

    public int getAscensionMaxHPLoss() {return ASCENSION_MAX_HP_LOSS;}

    public BitmapFont getEnergyNumFont() {return FontHelper.energyNumFontBlue;}

    public void doCharSelectScreenSelectEffect() {}

    public void updateOrb(int orbCount) {this.energyOrb.updateOrb(orbCount);}

    public String getCustomModeCharacterButtonSoundKey() {return null;}

    public AbstractPlayer newInstance() {return new MGR_character(this.name);}

    public String getSpireHeartText() {return SpireHeart.DESCRIPTIONS[10];}

    public Color getSlashAttackColor() {return SILVER;}

    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {return new AbstractGameAction.AttackEffect[0];}

    public String getVampireText() {return "Hello, vampires?";}

    public void applyEndOfTurnTriggers() {super.applyEndOfTurnTriggers();}

    public void useCard(AbstractCard targetCard, AbstractMonster monster, int energyOnUse) {
        super.useCard(targetCard, monster, energyOnUse);
        AbstractDungeon.player.gainGold(10);
    }
}