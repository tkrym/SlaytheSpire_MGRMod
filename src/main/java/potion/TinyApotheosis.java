package potion;

import character.MGR_character;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.shrines.WeMeetAgain;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TinyApotheosis extends AbstractPotion{
    public static final String POTION_ID = "MGR:TinyApotheosis";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public TinyApotheosis() {
        super(potionStrings.NAME, POTION_ID, PotionRarity.RARE, PotionSize.ANVIL, AbstractPotion.PotionEffect.RAINBOW, Color.WHITE, null, null);
        this.isThrown = false;
    }

    @Override
    public void initializeData() {
        this.potency = getPotency();
        this.description = potionStrings.DESCRIPTIONS[0] + this.potency + potionStrings.DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        //this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.BLOCK.NAMES[0]), GameDictionary.keywords.get(GameDictionary.BLOCK.NAMES[0])));
    }

    @Override
    public void use(AbstractCreature target) {
        for(int i=0;i<this.potency;++i)
        {
            ArrayList<AbstractCard> upgradableCards_RARE = new ArrayList<>();
            ArrayList<AbstractCard> upgradableCards_UNCOMMON = new ArrayList<>();
            ArrayList<AbstractCard> upgradableCards_SPECIAL = new ArrayList<>();
            ArrayList<AbstractCard> upgradableCards_COMMON = new ArrayList<>();
            ArrayList<AbstractCard> upgradableCards_BASIC = new ArrayList<>();
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                if (c.canUpgrade()) {
                    switch (c.rarity)
                    {
                        case RARE:upgradableCards_RARE.add(c);break;
                        case UNCOMMON:upgradableCards_UNCOMMON.add(c);break;
                        case SPECIAL:upgradableCards_SPECIAL.add(c);break;
                        case COMMON:upgradableCards_COMMON.add(c);break;
                        default:upgradableCards_BASIC.add(c);
                    }
                }
            }
            ArrayList<AbstractCard> upgradableCards;
            if(!upgradableCards_RARE.isEmpty()) upgradableCards=upgradableCards_RARE;
            else if(!upgradableCards_UNCOMMON.isEmpty()) upgradableCards=upgradableCards_UNCOMMON;
            else if(!upgradableCards_SPECIAL.isEmpty()) upgradableCards=upgradableCards_SPECIAL;
            else if(!upgradableCards_COMMON.isEmpty()) upgradableCards=upgradableCards_COMMON;
            else if(!upgradableCards_BASIC.isEmpty()) upgradableCards=upgradableCards_BASIC;
            else continue;
            Collections.shuffle(upgradableCards, new Random(AbstractDungeon.miscRng.randomLong()));
            if (!upgradableCards.isEmpty()) {
                upgradableCards.get(0).upgrade();
                if(upgradableCards.size()>1)  AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(1));
                AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(0));
                AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(upgradableCards.get(0).makeStatEquivalentCopy(), ((((float) Settings.WIDTH) / 2.0f) - (AbstractCard.IMG_WIDTH / 2.0f)) - (20.0f * Settings.scale), ((float) Settings.HEIGHT) / 2.0f));
                AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(((float) Settings.WIDTH) / 2.0f, ((float) Settings.HEIGHT) / 2.0f));
            }
        }
    }

    @Override
    public boolean canUse() {
        if (AbstractDungeon.actionManager.turnHasEnded && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            return false;
        }
        if (AbstractDungeon.getCurrRoom().event == null || !(AbstractDungeon.getCurrRoom().event instanceof WeMeetAgain)) {
            return true;
        }
        return false;
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new TinyApotheosis();
    }
}
