package potion;

import action.ApplyForteAction;
import character.MGR_character;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import relic.LittleAngel;

public class ShiningEssence extends AbstractPotion{
    public static final String POTION_ID = "MGR:ShiningEssence";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public ShiningEssence() {
        super(potionStrings.NAME, POTION_ID, PotionRarity.RARE, PotionSize.EYE, PotionColor.ANCIENT);
        this.labOutlineColor = MGR_character.MyColor;
        this.isThrown = false;
    }

    @Override
    public void initializeData() {
        this.potency = getPotency();
        this.description = potionStrings.DESCRIPTIONS[0] + this.potency + potionStrings.DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    if(AbstractDungeon.player instanceof MGR_character)
                    {
                        MGR_character p=(MGR_character)AbstractDungeon.player;
                        p.counter_max-=ShiningEssence.this.potency;
                        if(p.counter_max<=p.counter_min+1) p.counter_max=p.counter_min+1;
                        MGR_character.IncCounter(p.counter_max);
                    }
                    this.isDone=true;
                }
            });
        }
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new ShiningEssence();
    }
}
