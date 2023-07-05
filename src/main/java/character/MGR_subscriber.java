package character;

import basemod.BaseMod;
import basemod.ModPanel;
import basemod.interfaces.*;
import card.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.*;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import path.AbstractCardEnum;
import path.ModClassEnum;
import relic.*;
import potion.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

@SpireInitializer
public class MGR_subscriber implements EditCharactersSubscriber,EditRelicsSubscriber,EditCardsSubscriber,EditStringsSubscriber,EditKeywordsSubscriber,PostInitializeSubscriber{
    private static final String MOD_BADGE = "img/UI/badge.png";
    private static final String ATTACK_CC = "img/512/MGR_attack_s.png";
    private static final String SKILL_CC = "img/512/MGR_skill_s.png";
    private static final String POWER_CC = "img/512/MGR_power_s.png";
    private static final String ENERGY_ORB_CC = "img/512/MGR_orb_s.png";
    private static final String ATTACK_CC_PORTRAIT = "img/1024/MGR_attack.png";
    private static final String SKILL_CC_PORTRAIT = "img/1024/MGR_skill.png";
    private static final String POWER_CC_PORTRAIT = "img/1024/MGR_power.png";
    private static final String ENERGY_ORB_CC_PORTRAIT = "img/1024/MGR_orb.png";
    public static final String CARD_ENERGY_ORB = "img/UI/energyOrb.png";
    private static final String MY_CHARACTER_BUTTON = "img/select/button.png";
    private static final String MY_CHARACTER_PORTRAIT = "img/select/figure.png";
    public static final Color MyColor = CardHelper.getColor(255, 120, 0);
    private ArrayList<AbstractCard> cardsToAdd = new ArrayList<>();

    public MGR_subscriber() {
        BaseMod.subscribe(this);
        BaseMod.addColor(AbstractCardEnum.MGR_COLOR, MyColor, MyColor, MyColor, MyColor, MyColor, MyColor, MyColor, ATTACK_CC, SKILL_CC, POWER_CC, ENERGY_ORB_CC, ATTACK_CC_PORTRAIT, SKILL_CC_PORTRAIT,POWER_CC_PORTRAIT, ENERGY_ORB_CC_PORTRAIT, CARD_ENERGY_ORB);
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new MGR_character("MGR"), MY_CHARACTER_BUTTON, MY_CHARACTER_PORTRAIT, ModClassEnum.MGR_CLASS);
    }
    public static void initialize() {
        new MGR_subscriber();
    }

    @Override
    public void receiveEditCards() {
        loadCardsToAdd();
        for (AbstractCard card : this.cardsToAdd) {
            BaseMod.addCard(card);
        }
    }

    public void receivePostInitialize()
    {
        Texture badge = ImageMaster.loadImage("img/UI/badge.png");
        BaseMod.registerModBadge(badge, "MGRMod", "MGRSK", "COOKIE mod MGR.ver", new ModPanel());
        Color mybluecolor=new Color(1693511880);
        BaseMod.addPotion(FortePotion.class, mybluecolor.cpy(), Color.WHITE.cpy(), mybluecolor.cpy(), FortePotion.POTION_ID, ModClassEnum.MGR_CLASS);
        BaseMod.addPotion(BottledNotes.class,mybluecolor.cpy(), mybluecolor.cpy(), Color.WHITE.cpy(),BottledNotes.POTION_ID,ModClassEnum.MGR_CLASS);
        BaseMod.addPotion(TinyApotheosis.class, Color.WHITE.cpy(), Color.WHITE.cpy(), Color.WHITE.cpy(), TinyApotheosis.POTION_ID);
    }

    @Override
    public void receiveEditKeywords() {
        String keywordsPath=getStringPath()+"MGR_keyword.json";
        Gson gson = new Gson();
        Keyword[] keywords = gson.fromJson(loadJson(keywordsPath), Keyword[].class);
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword("mgr", keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    private String getStringPath()
    {
        String StringPath;
        switch (Settings.language) {
            case ZHS: StringPath = "localization/zhs/"; break;
            default: StringPath = "localization/eng/";
        }
        return StringPath;
    }

    private static String loadJson(String jsonPath) {
        return Gdx.files.internal(jsonPath).readString(String.valueOf(StandardCharsets.UTF_8));
    }

    @Override
    public void receiveEditStrings() {
        String StringPath=getStringPath();
        String  relic=StringPath+"MGR_relic.json",
                card=StringPath+"MGR_card.json",
                power=StringPath+"MGR_power.json",
                potion=StringPath+"MGR_potion.json",
                event=StringPath+"MGR_event.json",
                orb=StringPath+"MGR_orb.json",
                ui=StringPath+"MGR_ui.json",
                stance=StringPath+"MGR_stance.json";

        String relicStrings = loadJson(relic);
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
        String cardStrings = loadJson(card);
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
        String powerStrings = loadJson(power);
        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
        String potionStrings = loadJson(potion);
        BaseMod.loadCustomStrings(PotionStrings.class, potionStrings);
        String eventStrings = loadJson(event);
        BaseMod.loadCustomStrings(EventStrings.class, eventStrings);
        String orbStrings = loadJson(orb);
        BaseMod.loadCustomStrings(OrbStrings.class, orbStrings);
        String uiStrings = loadJson(ui);
        BaseMod.loadCustomStrings(UIStrings.class, uiStrings);
        String stanceStrings = loadJson(stance);
        BaseMod.loadCustomStrings(StanceStrings.class, stanceStrings);
    }

    private void loadCardsToAdd() {
        this.cardsToAdd.clear();
        this.cardsToAdd.add(new Strike_MGR());
        this.cardsToAdd.add(new Defend_MGR());
        this.cardsToAdd.add(new SpBullet());
        this.cardsToAdd.add(new TestAttack());
        this.cardsToAdd.add(new TestDefend());
        this.cardsToAdd.add(new TestPower());
        this.cardsToAdd.add(new CoinflipStrike());
        this.cardsToAdd.add(new AttackTied());
        this.cardsToAdd.add(new CrispEnding());
        this.cardsToAdd.add(new GentleEnding());
        this.cardsToAdd.add(new SpBullet1());
        this.cardsToAdd.add(new SpBullet2());
        this.cardsToAdd.add(new FinalMovement());
    }
    @Override
    public void receiveEditRelics()
    {
        BaseMod.addRelicToCustomPool(new TheFirst(),AbstractCardEnum.MGR_COLOR);
    }
}
