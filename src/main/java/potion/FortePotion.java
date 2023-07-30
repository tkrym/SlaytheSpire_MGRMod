package potion;

import action.ApplyForteAction;
import action.ChannelNoteAction;
import basemod.BaseMod;
import character.MGR_character;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import note.*;

public class FortePotion extends AbstractPotion{
    public static final String POTION_ID = "MGR:FortePotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public FortePotion() {
        super(potionStrings.NAME, POTION_ID, PotionRarity.COMMON, PotionSize.S, PotionColor.SWIFT);
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
            //AbstractCreature p = AbstractDungeon.player;
            //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new StrengthPower(p,-this.potency),-this.potency));
            //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new DexterityPower(p,-this.potency),-this.potency));
            AbstractDungeon.actionManager.addToBottom(new ApplyForteAction(this.potency));
        }
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new FortePotion();
    }
}
