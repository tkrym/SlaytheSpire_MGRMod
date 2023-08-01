package action;

import card.COMMON.QuickShooting;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import note.AbstractNote;
import patch.ManualDiscardPatch;

public class TheBurningSunAction extends AbstractGameAction {
    public static String[] TEXT=CardCrawlGame.languagePack.getUIString("TheBurningSunAction").TEXT;
    public TheBurningSunAction(int amount)
    {
        this.amount = amount;
        this.actionType = ActionType.EXHAUST;
        this.startDuration=Settings.ACTION_DUR_XFAST;
        this.duration= this.startDuration;
    }
    public void update()
    {
        AbstractPlayer p=AbstractDungeon.player;
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()||p.hand.size()<=0||this.amount<=0)
        {
            this.isDone = true;
            return;
        }
        if (this.duration == this.startDuration)
        {
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, true, true);
            p.hand.applyPowers();
            this.tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            addToTop(new DrawCardAction(AbstractDungeon.handCardSelectScreen.selectedCards.size()));
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group)
            {
                p.hand.moveToExhaustPile(c);
                //AbstractNote.GenerateNoteTop(c);
            }
            CardCrawlGame.dungeon.checkForPactAchievement();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        this.tickDuration();
    }
}

