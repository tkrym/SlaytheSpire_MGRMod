package character;

import basemod.BaseMod;
import basemod.interfaces.*;
import card.*;
import character.MGR_character;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import path.AbstractCardEnum;
import path.ModClassEnum;
import relic.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

@SpireInitializer
public class MGR_subscriber implements EditCharactersSubscriber,EditRelicsSubscriber,EditCardsSubscriber,EditStringsSubscriber,EditKeywordsSubscriber{
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
        BaseMod.subscribe((ISubscriber)this);
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
        Iterator<AbstractCard> var1 = this.cardsToAdd.iterator();
        while (var1.hasNext()) {
            AbstractCard card = var1.next();
            BaseMod.addCard(card);
        }
    }

    private static String loadJson(String jsonPath) {
        return Gdx.files.internal(jsonPath).readString(String.valueOf(StandardCharsets.UTF_8));
    }


    @Override
    public void receiveEditKeywords() {

    }

    @Override
    public void receiveEditStrings() {
        String StringPath;
        switch (Settings.language) {
            case ZHS: StringPath = "localization/zhs/"; break;
            default: StringPath = "localization/eng/";
        }
        String  relic=StringPath+"MGR_relic.json",
                card=StringPath+"MGR_card.json",
                power=StringPath+"MGR_power.json",
                potion=StringPath+"MGR_potion.json",
                event=StringPath+"MGR_event.json",
                orb=StringPath+"MGR_orb.json",
                ui=StringPath+"MGR_ui.json";

        String relicStrings = Gdx.files.internal(relic).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
        String cardStrings = Gdx.files.internal(card).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
        String powerStrings = Gdx.files.internal(power).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
        String potionStrings = Gdx.files.internal(potion).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PotionStrings.class, potionStrings);
        String eventStrings = Gdx.files.internal(event).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(EventStrings.class, eventStrings);
        String orbStrings = Gdx.files.internal(orb).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(OrbStrings.class, orbStrings);
        String uiStrings = Gdx.files.internal(ui).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(UIStrings.class, uiStrings);
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
    }
    @Override
    public void receiveEditRelics()
    {
        BaseMod.addRelicToCustomPool(new TheFirst(),AbstractCardEnum.MGR_COLOR);
    }

    class Keywords {
        Keyword[] keywords;
    }
}
