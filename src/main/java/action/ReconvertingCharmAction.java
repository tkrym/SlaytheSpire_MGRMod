package action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;

import java.util.ListIterator;

public class ReconvertingCharmAction extends AbstractGameAction
{
    private boolean IsAwakened;

    public ReconvertingCharmAction(int amount, boolean IsAwakened)
    {
        this.amount = amount;
        this.IsAwakened = IsAwakened;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update()
    {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead() || AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty())
        {
            this.isDone = true; return;
        }
        ListIterator<AbstractCard> iterator =
                AbstractDungeon.actionManager.cardsPlayedThisCombat.listIterator(AbstractDungeon.actionManager.cardsPlayedThisCombat.size());
        AbstractPlayer p = AbstractDungeon.player;
        while (iterator.hasPrevious())
        {
            AbstractCard c = iterator.previous();
            if (p.discardPile.contains(c))
            {
                if (this.IsAwakened) addToBot(new TemporaryDuplicationAction(c));
                else
                {
                    p.discardPile.removeCard(c);
                    AddToHand(c);
                }
                if (DecAmount()) break;
            }
            else if (p.drawPile.contains(c))
            {
                if (this.IsAwakened) addToBot(new TemporaryDuplicationAction(c));
                else
                {
                    p.drawPile.removeCard(c);
                    AddToHand(c);
                }
                if (DecAmount()) break;
            }
            else if (p.hand.contains(c))
            {
                if (this.IsAwakened) addToBot(new TemporaryDuplicationAction(c));
                else
                {
                    p.hand.removeCard(c);
                    AddToHand(c);
                }
                if (DecAmount()) break;
            }
        }
        for (AbstractCard c : p.hand.group) c.applyPowers();
        p.hand.refreshHandLayout();
        this.isDone = true;
    }

    private boolean DecAmount() {this.amount--; return this.amount <= 0;}

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

