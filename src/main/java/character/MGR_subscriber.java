package character;

import basemod.BaseMod;
import basemod.ModPanel;
import basemod.interfaces.*;
import card.BASIC.*;
import card.COMMON.*;
import card.RARE.*;
import card.COMMON.OneUp;
import card.TEST.Peek;
import card.UNCOMMON.DiffusionOfDarkness;
import card.UNCOMMON.WaitingForFood;
import card.UNCOMMON.Rehearsal;
import card.UNCOMMON.SongOfLevitation;
import card.UNCOMMON.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.Prefs;
import com.megacrit.cardcrawl.helpers.SaveHelper;
import com.megacrit.cardcrawl.localization.*;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import path.AbstractCardEnum;
import path.ModClassEnum;
import relic.*;
import potion.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@SpireInitializer
public class MGR_subscriber implements EditCharactersSubscriber,EditRelicsSubscriber,EditCardsSubscriber,EditStringsSubscriber,EditKeywordsSubscriber,PostInitializeSubscriber,OnCardUseSubscriber,OnStartBattleSubscriber,PostBattleSubscriber,AddAudioSubscriber{
    private static final String MOD_BADGE = "img/UI/MGR_badge.png";
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
    public static final Color MyColor = CardHelper.getColor(255, 160, 0);
    private ArrayList<AbstractCard> cardsToAdd = new ArrayList<>();
    private ArrayList<AbstractRelic> relicsToAdd = new ArrayList<>();
    private AbstractCard LastCardPlayed;

    public MGR_subscriber() {
        BaseMod.subscribe(this);
        BaseMod.addColor(AbstractCardEnum.MGR_COLOR, MyColor, MyColor, MyColor, MyColor, MyColor, MyColor, MyColor, ATTACK_CC, SKILL_CC, POWER_CC, ENERGY_ORB_CC, ATTACK_CC_PORTRAIT, SKILL_CC_PORTRAIT,POWER_CC_PORTRAIT, ENERGY_ORB_CC_PORTRAIT, CARD_ENERGY_ORB);
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new MGR_character("MGR"), MY_CHARACTER_BUTTON, MY_CHARACTER_PORTRAIT, ModClassEnum.MGR);
    }
    public static void initialize() {
        new MGR_subscriber();
    }

    public void receiveAddAudio() {
        BaseMod.addAudio("MGR:CharSelect", "audio/MGR_charselect.ogg");
        BaseMod.addAudio("MGR:MasterSpark", "audio/MGR_masterspark.wav");
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
        Texture badge = ImageMaster.loadImage(MOD_BADGE);
        BaseMod.registerModBadge(badge, "MGRMod", "MGRSK", "COOKIE mod MGR.ver", new ModPanel());
        Color mybluecolor=MGR_character.myBuleColor;
        BaseMod.addPotion(FortePotion.class, mybluecolor.cpy(), mybluecolor.cpy(), mybluecolor.cpy(), FortePotion.POTION_ID, ModClassEnum.MGR);
        BaseMod.addPotion(BottledNotes.class, mybluecolor.cpy(), mybluecolor.cpy(),new Color(1.0F,0.72F,0.19F,1.0F),BottledNotes.POTION_ID,ModClassEnum.MGR);
        BaseMod.addPotion(PortableAnvil.class, Color.WHITE.cpy(), Color.WHITE.cpy(), Color.WHITE.cpy(), PortableAnvil.POTION_ID);
        BaseMod.addPotion(ShiningEssence.class, Color.GOLD.cpy(),CardHelper.getColor(214,186,38),null,ShiningEssence.POTION_ID,ModClassEnum.MGR);
        UnlockAscensionLevel();
        for(AbstractCard card:cardsToAdd)
            UnlockTracker.markCardAsSeen(card.cardID);
    }

    private void UnlockAscensionLevel()
    {
        Prefs p=SaveHelper.getPrefs("MGR");
        if (p.getInteger("WIN_COUNT", 0) == 0) p.putInteger("WIN_COUNT", 1);
        if (p.getInteger("BOSS_KILL", 0) == 0) p.putInteger("BOSS_KILL", 1);
        if (p.getInteger("ASCENSION_LEVEL",0) != 20 )
        {
            p.putInteger("ASCENSION_LEVEL", 20);
            p.putInteger("LAST_ASCENSION_LEVEL", 0);
        }
        p.flush();
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
                stance=StringPath+"MGR_stance.json",
                tutorial=StringPath+"MGR_tutorial.json",
                character=StringPath+"MGR_character.json";

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
        String tutorialStrings = loadJson(tutorial);
        BaseMod.loadCustomStrings(TutorialStrings.class, tutorialStrings);
        String characterStrings = loadJson(character);
        BaseMod.loadCustomStrings(CharacterStrings.class, characterStrings);
    }

    private void loadCardsToAdd() {
        this.cardsToAdd.clear();
        this.cardsToAdd.add(new Strike_MGR());
        this.cardsToAdd.add(new Defend_MGR());
        this.cardsToAdd.add(new CoinflipStrike());
        this.cardsToAdd.add(new AttackTied());
        this.cardsToAdd.add(new CrispEnding());
        this.cardsToAdd.add(new Lullaby());
        this.cardsToAdd.add(new EastOfTimeline());
        this.cardsToAdd.add(new GazeofOthers());
        this.cardsToAdd.add(new GazeLock());
        this.cardsToAdd.add(new LAB01());
        this.cardsToAdd.add(new Stereophonic());
        this.cardsToAdd.add(new IndifferentLook());
        this.cardsToAdd.add(new StageWarmUp());
        this.cardsToAdd.add(new ScaryGlare());
        this.cardsToAdd.add(new HarmonyForm());
        this.cardsToAdd.add(new AdjustTempo());
        this.cardsToAdd.add(new MasterSpark());
        this.cardsToAdd.add(new MaguroAssault());
        this.cardsToAdd.add(new MaguroBash());
        this.cardsToAdd.add(new Unison());
        this.cardsToAdd.add(new OneUp());
        this.cardsToAdd.add(new Rehearsal());
        this.cardsToAdd.add(new SongOfLevitation());
        this.cardsToAdd.add(new WaitingForFood());
        this.cardsToAdd.add(new DiffusionOfDarkness());
        this.cardsToAdd.add(new Peek());
    }
    @Override
    public void receiveEditRelics()
    {
        loadRelicsToAdd();
        for (AbstractRelic relic : this.relicsToAdd) {
            BaseMod.addRelicToCustomPool(relic,AbstractCardEnum.MGR_COLOR);
            UnlockTracker.markRelicAsSeen(relic.relicId);
        }
    }

    private void loadRelicsToAdd()
    {
        this.relicsToAdd.clear();
        this.relicsToAdd.add(new UnknownCreature());
        this.relicsToAdd.add(new Sunglasses());
        this.relicsToAdd.add(new OldBow());
        this.relicsToAdd.add(new Telescreen());
        this.relicsToAdd.add(new BloodshotEyeball());
        this.relicsToAdd.add(new LittleAngel());
        this.relicsToAdd.add(new Maguroyaki());
        this.relicsToAdd.add(new MiniMicrophone());
        this.relicsToAdd.add(new YourExclusiveStage());
        this.relicsToAdd.add(new WitchHat());
        this.relicsToAdd.add(new FriendsSpirit());
        this.relicsToAdd.add(new Fumo());
    }

    @Override
    public void receiveCardUsed(AbstractCard c) {
        if(!c.purgeOnUse&&!c.dontTriggerOnUseCard) LastCardPlayed=c;
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        LastCardPlayed=null;
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        if(LastCardPlayed!=null&&LastCardPlayed instanceof EastOfTimeline)
            EastOfTimeline.IncMist(LastCardPlayed.uuid,1);
    }
}
