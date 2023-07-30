package action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import note.AbstractNote;
import patch.ManualDiscardPatch;

import java.util.Iterator;

public class EchoPhantomAction extends AbstractGameAction
{
    public static String[] TEXT = CardCrawlGame.languagePack.getUIString("EchoPhantomAction").TEXT;
    private boolean B = false;

    public EchoPhantomAction(int amount, boolean B)
    {
        this.amount = amount;
        this.B = B;
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
                for (AbstractCard c : p.discardPile.group)
                {
                    addToBot(new MakeTempCardInDrawPileAction(c, 1, false, false));
                    if (this.B) AddToHand(c.makeStatEquivalentCopy());
                }
                this.isDone = true;
                return;
            }
            else
            {
                AbstractDungeon.gridSelectScreen.open(p.discardPile, this.amount, TEXT[0] + this.amount + TEXT[1], false, false, false, false);
                this.tickDuration();
                return;
            }
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
            {
                addToBot(new MakeTempCardInDrawPileAction(c, 1, false, false));
                if (this.B) AddToHand(c.makeStatEquivalentCopy());
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            p.hand.refreshHandLayout();
        }
        this.tickDuration();
    }

    private void AddToHand(AbstractCard c)
    {
        if (AbstractDungeon.player.hand.size() <= 9)
        {
            AbstractDungeon.player.hand.addToHand(c);
            c.lighten(false);
            c.unhover();
            c.applyPowers();
        }
        else
        {
            AbstractDungeon.player.createHandIsFullDialog();
            AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(c, (float) Settings.WIDTH / 2.0F + 25.0F * Settings.scale + AbstractCard.IMG_WIDTH, (float) Settings.HEIGHT / 2.0F));
        }
    }

}

