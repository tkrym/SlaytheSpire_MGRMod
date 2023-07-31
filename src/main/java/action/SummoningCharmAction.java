package action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class SummoningCharmAction extends AbstractGameAction
{
    public SummoningCharmAction(int amount)
    {
        this.amount = amount;
        this.actionType = ActionType.EXHAUST;
    }

    public void update()
    {
        AbstractPlayer p = AbstractDungeon.player;
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            this.isDone = true;
            return;
        }
        ArrayList<AbstractCard> cards = new ArrayList<>(p.drawPile.group);
        if (!cards.isEmpty())
        {
            Collections.shuffle(cards, new Random(AbstractDungeon.miscRng.randomLong()));
            cards.sort(Comparator.comparingInt(c -> -c.costForTurn));
            for (int i = 1; i <= this.amount; i++)
            {
                if (cards.size() >= i)
                {
                    AbstractCard c = cards.get(i - 1);
                    AddToHand(c);
                }
            }
            p.hand.applyPowers();
            p.hand.refreshHandLayout();
        }
        this.isDone = true;
    }

    private void AddToHand(AbstractCard c)
    {
        if (AbstractDungeon.player.hand.size() <= 9)
        {
            if(c.cost>0) c.cost--;
            c.setCostForTurn(c.costForTurn - 1);
            AbstractDungeon.player.drawPile.removeCard(c);
            AbstractDungeon.player.hand.addToHand(c);
            c.lighten(false);
            c.unhover();
            c.applyPowers();
        }
        else AbstractDungeon.player.createHandIsFullDialog();
    }
}

