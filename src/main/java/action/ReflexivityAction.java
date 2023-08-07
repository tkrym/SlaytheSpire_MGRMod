package action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;

public class ReflexivityAction extends AbstractGameAction
{
    public static String[] TEXT = CardCrawlGame.languagePack.getUIString("ReflexivityAction").TEXT;
    private boolean B;

    public ReflexivityAction(int amount,boolean B)
    {
        this.amount = amount;
        this.B=B;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startDuration = Settings.ACTION_DUR_XFAST;
        this.duration = this.startDuration;
    }

    public void update()
    {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead() || this.amount <= 0)
        {
            this.isDone = true;
            return;
        }
        AbstractPlayer p = AbstractDungeon.player;
        if (this.duration == this.startDuration)
        {
            if (p.discardPile.size() <= 0)
            {
                this.isDone = true;
                return;
            }
            else if (p.discardPile.size() <= this.amount)
            {
                int size=p.discardPile.size();
                for (int i=1;i<=size;i++)
                {
                    AbstractCard c=p.discardPile.getTopCard();
                    p.discardPile.removeCard(c);
                    if(B) p.discardPile.moveToDeck(c,false);
                    else p.discardPile.moveToBottomOfDeck(c);
                }
                this.isDone = true;
                return;
            }
            else
            {
                AbstractDungeon.gridSelectScreen.open(p.discardPile, this.amount, TEXT[0] + this.amount + (this.B?TEXT[2]:TEXT[1]), false, false, false, false);
                this.tickDuration();
                return;
            }
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
            {
                p.discardPile.removeCard(c);
                if(B) p.discardPile.moveToDeck(c,false);
                else p.discardPile.moveToBottomOfDeck(c);
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            p.hand.refreshHandLayout();
        }
        this.tickDuration();
    }

}

