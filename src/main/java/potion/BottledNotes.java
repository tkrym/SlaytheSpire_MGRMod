package potion;

import action.ChannelNoteAction;
import character.MGR_character;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import note.*;

public class BottledNotes extends AbstractPotion{
    public static final String POTION_ID = "MGR:BottledNotes";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public BottledNotes() {
        super(potionStrings.NAME, POTION_ID, PotionRarity.UNCOMMON, AbstractPotion.PotionSize.BOTTLE, PotionColor.SNECKO);
        this.labOutlineColor = MGR_character.MyColor;
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
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            for (int i = 0; i < this.potency; i++)
                AbstractDungeon.actionManager.addToBottom(new ChannelNoteAction(new AttackNote()));
            for (int i = 0; i < this.potency; i++)
                AbstractDungeon.actionManager.addToBottom(new ChannelNoteAction(new DefendNote()));
            for (int i = 0; i < this.potency; i++)
                AbstractDungeon.actionManager.addToBottom(new ChannelNoteAction(new DrawNote()));
            for (int i = 0; i < this.potency; i++)
                AbstractDungeon.actionManager.addToBottom(new ChannelNoteAction(new ArtifactNote()));
            for (int i = 0; i < this.potency; i++)
                AbstractDungeon.actionManager.addToBottom(new ChannelNoteAction(new DebuffNote()));
        }
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new BottledNotes();
    }
}
