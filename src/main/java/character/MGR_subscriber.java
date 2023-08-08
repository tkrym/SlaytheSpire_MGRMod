package character;

import action.MGRTutorialAction;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.interfaces.*;
import card.BASIC.*;
import card.COMMON.*;
import card.COMMON.GazeOfOthers;
import card.RARE.*;
import card.RARE.Hallucination;
import card.BASIC.Peek;
import card.COMMON.Frighten;
import card.COMMON.RapidClaw;
import card.UNCOMMON.HakkeroCharge;
import card.COMMON.Reflexivity;
import card.COMMON.StarryDrift;
import card.COMMON.TheEyeBehind;
import card.UNCOMMON.EchoPhantom;
import card.COMMON.IntertwinedTimbres;
import card.UNCOMMON.Brilliant;
import card.RARE.LightUpTheStage;
import card.UNCOMMON.SummoningCharm;
import card.COMMON.Dexterous;
import card.RARE.Salivate;
import card.UNCOMMON.SunDescending;
import card.COMMON.MyReflection;
import card.RARE.VocalPreparation;
import card.UNCOMMON.Romp;
import card.RARE.ResonanceForm;
import card.COMMON.Nocturnal;
import card.COMMON.Marionette;
import card.COMMON.StardustLaser;
import card.COMMON.BroomStrike;
import card.RARE.Shatter;
import card.COMMON.AccurateShooting;
import card.UNCOMMON.Drown;
import card.RARE.DragonClaw;
import card.UNCOMMON.ReconvertingCharm;
import card.UNCOMMON.NowhereToHide;
import card.UNCOMMON.ChristmasGift;
import card.UNCOMMON.Strafe;
import card.UNCOMMON.TheCursed;
import card.UNCOMMON.Swallow;
import card.UNCOMMON.SongOfSubmersion;
import card.COMMON.Chorus;
import card.UNCOMMON.Futariboshi;
import card.UNCOMMON.Kimitomitahosizora;
import card.UNCOMMON.Yazyuutokasu;
import card.UNCOMMON.GazeFromTheShadow;
import card.UNCOMMON.DarkDiffuse;
import card.COMMON.Rehearsal;
import card.UNCOMMON.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import path.AbstractCardEnum;
import path.ModClassEnum;
import relic.*;
import potion.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Properties;

@SpireInitializer
public class MGR_subscriber implements EditCharactersSubscriber, EditRelicsSubscriber, EditCardsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber, PostInitializeSubscriber, OnCardUseSubscriber, OnStartBattleSubscriber, PostBattleSubscriber, AddAudioSubscriber, PostDungeonInitializeSubscriber
{
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
    public static final Color MyColor = CardHelper.getColor(255, 110, 0);
    private ArrayList<AbstractCard> cardsToAdd = new ArrayList<>();
    private ArrayList<AbstractRelic> relicsToAdd = new ArrayList<>();
    private ArrayList<AbstractRelic> relicsToAdd_SHARED = new ArrayList<>();
    private AbstractCard LastCardPlayed;
    public static Properties CustomSettings = new Properties();
    public static boolean AddCustomObjects = false;
    public static final String AddCustomObjectsString = "AddCustomObjects";
    public static boolean EnableTutorial = true;
    public static final String EnableTutorialString = "EnableTutorial";
    public static boolean UnlockA20 = false;
    public static final String UnlockA20String = "UnlockA20";
    public static boolean BanRelics = false;
    public static final String BanRelicsString = "BanRelics";

    public MGR_subscriber()
    {
        BaseMod.subscribe(this);
        BaseMod.addColor(AbstractCardEnum.MGR_COLOR, MyColor, MyColor, MyColor, MyColor, MyColor, MyColor, MyColor, ATTACK_CC, SKILL_CC, POWER_CC, ENERGY_ORB_CC, ATTACK_CC_PORTRAIT, SKILL_CC_PORTRAIT, POWER_CC_PORTRAIT, ENERGY_ORB_CC_PORTRAIT, CARD_ENERGY_ORB);
        LoadConfig();
    }

    private void LoadConfig()
    {
        CustomSettings.setProperty(AddCustomObjectsString, "FALSE");
        CustomSettings.setProperty(EnableTutorialString, "TRUE");
        CustomSettings.setProperty(UnlockA20String, "FALSE");
        CustomSettings.setProperty(BanRelicsString, "FALSE");
        try
        {
            SpireConfig config = new SpireConfig("MGRMod", "CustomSettings", CustomSettings);
            config.load();
            UnlockA20 = config.getBool(UnlockA20String);
            AddCustomObjects = config.getBool(AddCustomObjectsString);
            BanRelics = config.getBool(BanRelicsString);
            EnableTutorial = config.getBool(EnableTutorialString);
        } catch (Exception ignored) {}
    }

    @Override
    public void receiveEditCharacters()
    {
        BaseMod.addCharacter(new MGR_character("MGR"), MY_CHARACTER_BUTTON, MY_CHARACTER_PORTRAIT, ModClassEnum.MGR);
    }

    public static void initialize()
    {
        new MGR_subscriber();
    }

    public void receiveAddAudio()
    {
        BaseMod.addAudio("MGR:CharSelect", "audio/MGR_charselect.ogg");
        BaseMod.addAudio("MGR:MasterSpark", "audio/MGR_masterspark.wav");
        BaseMod.addAudio("MGR:NoteChannel", "audio/NoteChannel.ogg");
        BaseMod.addAudio("MGR:Chord", "audio/Chord.ogg");
        BaseMod.addAudio("MGR:Gaze", "audio/Gaze.ogg");
        BaseMod.addAudio("MGR:souraze", "audio/souraze.wav");
    }

    @Override
    public void receiveEditCards()
    {
        loadCardsToAdd();
        for (AbstractCard card : this.cardsToAdd)
        {
            BaseMod.addCard(card);
        }
    }

    private ModPanel PrepareModPanel()
    {
        TutorialStrings CustomSettingsStrings = CardCrawlGame.languagePack.getTutorialString("MGR:CustomSettings");
        ModPanel settingsPanel = new ModPanel();
        ModLabeledToggleButton UnlockA20Button = new ModLabeledToggleButton(CustomSettingsStrings.TEXT[0], 400.0f, 750.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, UnlockA20, settingsPanel, modLabel -> {}, button ->
        {
            UnlockA20 = button.enabled;
            try
            {
                if (UnlockA20) UnlockAscensionLevel();
                SpireConfig config = new SpireConfig("MGRMod", "CustomSettings", CustomSettings);
                config.setBool(UnlockA20String, UnlockA20);
                config.save();
            } catch (Exception ignored) {}
        });
        ModLabeledToggleButton AddCustomObjectsButton = new ModLabeledToggleButton(CustomSettingsStrings.TEXT[1], 400.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, AddCustomObjects, settingsPanel, modLabel -> {}, button ->
        {
            AddCustomObjects = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("MGRMod", "CustomSettings", CustomSettings);
                config.setBool(AddCustomObjectsString, AddCustomObjects);
                config.save();
            } catch (Exception ignored) {}
        });
        ModLabeledToggleButton BanRelicsButton = new ModLabeledToggleButton(CustomSettingsStrings.TEXT[2], 400.0f, 650.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, BanRelics, settingsPanel, modLabel -> {}, button ->
        {
            BanRelics = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("MGRMod", "CustomSettings", CustomSettings);
                config.setBool(BanRelicsString, BanRelics);
                config.save();
            } catch (Exception ignored) {}
        });
        ModLabeledToggleButton EnableTutorialButton = new ModLabeledToggleButton(CustomSettingsStrings.TEXT[4], 400.0F, 550.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, EnableTutorial, settingsPanel, (label) -> {}, button ->
        {
            EnableTutorial = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("MGRMod", "CustomSettings", CustomSettings);
                config.setBool(EnableTutorialString, EnableTutorial);
                config.save();
            } catch (Exception ignored) {}
        });
        settingsPanel.addUIElement(UnlockA20Button);
        settingsPanel.addUIElement(AddCustomObjectsButton);
        settingsPanel.addUIElement(BanRelicsButton);
        settingsPanel.addUIElement(EnableTutorialButton);
        return settingsPanel;
    }

    public void receivePostInitialize()
    {
        Texture badge = ImageMaster.loadImage(MOD_BADGE);
        BaseMod.registerModBadge(badge, "MGRMod", "MGRSK", "MGRMod", PrepareModPanel());
        Color mybluecolor = MGR_character.myBuleColor;
        BaseMod.addPotion(FortePotion.class, mybluecolor.cpy(), mybluecolor.cpy(), mybluecolor.cpy(), FortePotion.POTION_ID, ModClassEnum.MGR);
        BaseMod.addPotion(BottledNotes.class, mybluecolor.cpy(), mybluecolor.cpy(), new Color(1.0F, 0.72F, 0.19F, 1.0F), BottledNotes.POTION_ID, ModClassEnum.MGR);
        BaseMod.addPotion(ShiningEssence.class, CardHelper.getColor(28, 206, 227), CardHelper.getColor(53, 150, 159), null, ShiningEssence.POTION_ID, ModClassEnum.MGR);
        if (!AddCustomObjects)
        {
            BaseMod.addPotion(Doping.class, Color.WHITE.cpy(), Color.WHITE.cpy(), Color.WHITE.cpy(), Doping.POTION_ID, ModClassEnum.MGR);
            BaseMod.addPotion(PortableAnvil.class, Color.WHITE.cpy(), Color.WHITE.cpy(), Color.WHITE.cpy(), PortableAnvil.POTION_ID, ModClassEnum.MGR);
        }
        else
        {
            BaseMod.addPotion(Doping.class, Color.WHITE.cpy(), Color.WHITE.cpy(), Color.WHITE.cpy(), Doping.POTION_ID);
            BaseMod.addPotion(PortableAnvil.class, Color.WHITE.cpy(), Color.WHITE.cpy(), Color.WHITE.cpy(), PortableAnvil.POTION_ID);
        }
        for (AbstractCard card : cardsToAdd) UnlockTracker.markCardAsSeen(card.cardID);
        if (UnlockA20) UnlockAscensionLevel();
    }

    private void UnlockAscensionLevel()
    {
        Prefs p = SaveHelper.getPrefs("MGR");
        if (p.getInteger("WIN_COUNT", 0) == 0) p.putInteger("WIN_COUNT", 1);
        if (p.getInteger("BOSS_KILL", 0) == 0) p.putInteger("BOSS_KILL", 1);
        if (p.getInteger("ASCENSION_LEVEL", 0) != 20)
        {
            p.putInteger("ASCENSION_LEVEL", 20);
            p.putInteger("LAST_ASCENSION_LEVEL", 20);
        }
        p.flush();
    }

    @Override
    public void receiveEditKeywords()
    {
        String keywordsPath = getStringPath() + "MGR_keyword.json";
        Gson gson = new Gson();
        Keyword[] keywords = gson.fromJson(loadJson(keywordsPath), Keyword[].class);
        if (keywords != null)
            for (Keyword keyword : keywords)
                BaseMod.addKeyword("mgr", keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
    }

    private String getStringPath()
    {
        String StringPath;
        switch (Settings.language)
        {
            case ZHS:
                StringPath = "localization/zhs/"; break;
            default:
                StringPath = "localization/eng/";
        }
        return StringPath;
    }

    private static String loadJson(String jsonPath) {return Gdx.files.internal(jsonPath).readString(String.valueOf(StandardCharsets.UTF_8));}

    @Override
    public void receiveEditStrings()
    {
        String StringPath = getStringPath();
        String relic = StringPath + "MGR_relic.json",
                card = StringPath + "MGR_card.json",
                power = StringPath + "MGR_power.json",
                potion = StringPath + "MGR_potion.json",
                event = StringPath + "MGR_event.json",
                orb = StringPath + "MGR_orb.json",
                ui = StringPath + "MGR_ui.json",
                stance = StringPath + "MGR_stance.json",
                tutorial = StringPath + "MGR_tutorial.json",
                character = StringPath + "MGR_character.json";

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

    private void loadCardsToAdd()
    {
        this.cardsToAdd.clear();
        this.cardsToAdd.add(new Strike_MGR());
        this.cardsToAdd.add(new Defend_MGR());
        this.cardsToAdd.add(new AttackTied());
        this.cardsToAdd.add(new Lullaby());
        this.cardsToAdd.add(new EastOfTimeline());
        this.cardsToAdd.add(new IveSeenEverything());
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
        this.cardsToAdd.add(new Rehearsal());
        this.cardsToAdd.add(new GazeOfOthers());
        this.cardsToAdd.add(new Finale());
        this.cardsToAdd.add(new DarkDiffuse());
        this.cardsToAdd.add(new Peek());
        this.cardsToAdd.add(new Hallucination());
        this.cardsToAdd.add(new GazeFromTheShadow());
        this.cardsToAdd.add(new Romp());
        this.cardsToAdd.add(new Yazyuutokasu());
        this.cardsToAdd.add(new Obakenoukenerai());
        this.cardsToAdd.add(new ResonanceForm());
        this.cardsToAdd.add(new MaguroCleave());
        this.cardsToAdd.add(new Futariboshi());
        this.cardsToAdd.add(new Kimitomitahosizora());
        this.cardsToAdd.add(new Chorus());
        this.cardsToAdd.add(new SongOfSubmersion());
        this.cardsToAdd.add(new DizzyAndGiddy());
        this.cardsToAdd.add(new Swallow());
        this.cardsToAdd.add(new TheCursed());
        this.cardsToAdd.add(new Nocturnal());
        this.cardsToAdd.add(new MetalGear());
        this.cardsToAdd.add(new Marionette());
        this.cardsToAdd.add(new TinyOrchestra());
        this.cardsToAdd.add(new FieryPerformance());
        this.cardsToAdd.add(new StardustLaser());
        this.cardsToAdd.add(new BroomStrike());
        this.cardsToAdd.add(new Shatter());
        this.cardsToAdd.add(new ChristmasGift());
        this.cardsToAdd.add(new Strafe());
        this.cardsToAdd.add(new NowhereToHide());
        this.cardsToAdd.add(new Drown());
        this.cardsToAdd.add(new ReconvertingCharm());
        this.cardsToAdd.add(new DragonClaw());
        this.cardsToAdd.add(new AccurateShooting());
        this.cardsToAdd.add(new Siren());
        this.cardsToAdd.add(new Masterful());
        this.cardsToAdd.add(new VocalPreparation());
        this.cardsToAdd.add(new FolkRhymes());
        this.cardsToAdd.add(new MyReflection());
        this.cardsToAdd.add(new Dexterous());
        this.cardsToAdd.add(new Salivate());
        this.cardsToAdd.add(new SummoningCharm());
        this.cardsToAdd.add(new SunDescending());
        this.cardsToAdd.add(new FourEyes());
        this.cardsToAdd.add(new Frighten());
        this.cardsToAdd.add(new Brilliant());
        this.cardsToAdd.add(new LightUpTheStage());
        this.cardsToAdd.add(new EchoPhantom());
        this.cardsToAdd.add(new IntertwinedTimbres());
        this.cardsToAdd.add(new Reflexivity());
        this.cardsToAdd.add(new TheEyeBehind());
        this.cardsToAdd.add(new StarryDrift());
        this.cardsToAdd.add(new HakkeroCharge());
        this.cardsToAdd.add(new RapidClaw());
        this.cardsToAdd.add(new BurnsRed());
        this.cardsToAdd.add(new TheBurningSun());
        this.cardsToAdd.add(new Dandanhayakunaru());
    }

    @Override
    public void receiveEditRelics()
    {
        loadRelicsToAdd();
        for (AbstractRelic relic : this.relicsToAdd)
        {
            BaseMod.addRelicToCustomPool(relic, AbstractCardEnum.MGR_COLOR);
            UnlockTracker.markRelicAsSeen(relic.relicId);
        }
        for (AbstractRelic relic : this.relicsToAdd_SHARED)
        {
            if (!AddCustomObjects) BaseMod.addRelicToCustomPool(relic, AbstractCardEnum.MGR_COLOR);
            else RelicLibrary.add(relic);
            UnlockTracker.markRelicAsSeen(relic.relicId);
        }
    }

    private void loadRelicsToAdd()
    {
        this.relicsToAdd.clear();
        this.relicsToAdd_SHARED.clear();
        this.relicsToAdd_SHARED.add(new UnknownCreature());
        this.relicsToAdd.add(new Sunglasses());
        this.relicsToAdd.add(new OldBow());
        this.relicsToAdd.add(new Telescreen());
        this.relicsToAdd.add(new BloodshotEyeball());
        this.relicsToAdd_SHARED.add(new LittleAngel());
        this.relicsToAdd_SHARED.add(new Maguroyaki());
        this.relicsToAdd.add(new MiniMicrophone());
        this.relicsToAdd.add(new YourExclusiveStage());
        this.relicsToAdd_SHARED.add(new WitchHat());
        this.relicsToAdd_SHARED.add(new FriendsSpirit());
        this.relicsToAdd.add(new Fumo());
        this.relicsToAdd.add(new Voracious());
    }

    @Override
    public void receiveCardUsed(AbstractCard c)
    {
        if (!c.purgeOnUse && !c.dontTriggerOnUseCard)
        {
            if (LastCardPlayed == null && c instanceof EastOfTimeline)
                EastOfTimeline.IncMist(c.uuid, 1);
            LastCardPlayed = c;
        }
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom)
    {
        LastCardPlayed = null;
        if (AbstractDungeon.player instanceof MGR_character && EnableTutorial)
            AbstractDungeon.actionManager.addToBottom(new MGRTutorialAction());
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom)
    {
        if (LastCardPlayed != null && LastCardPlayed instanceof EastOfTimeline)
            EastOfTimeline.IncMist(LastCardPlayed.uuid, 1);
    }

    @Override
    public void receivePostDungeonInitialize()
    {
        if (AbstractDungeon.player instanceof MGR_character)
        {
            AbstractDungeon.bossRelicPool.remove(SneckoEye.ID);
            //AbstractDungeon.shopRelicPool.remove(PrismaticShard.ID);
            if (BanRelics)
            {
                AbstractDungeon.shopRelicPool.remove(MedicalKit.ID);
                AbstractDungeon.uncommonRelicPool.remove(BlueCandle.ID);
            }
        }
    }
}
