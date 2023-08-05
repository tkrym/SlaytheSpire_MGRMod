package action;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class MyReflectionAction extends AbstractGameAction
{
    public MyReflectionAction(int amount)
    {
        this.amount = amount;
        this.actionType = ActionType.EXHAUST;
    }

    public void update()
    {
        AbstractPlayer p = AbstractDungeon.player;
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead() || p.hand.size() == 0)
        {
            this.isDone = true;
            return;
        }
        ArrayList<AbstractCard> cards = new ArrayList<>(p.hand.group);
        Collections.shuffle(cards, new Random(AbstractDungeon.miscRng.randomLong()));
        cards.sort(Comparator.comparingInt(c -> c.costForTurn));
        if(!cards.isEmpty()) addToTop(new ExhaustSpecificCardAction(cards.get(0),p.hand));
        if (cards.size()>1)
        {
            AbstractCard c = cards.get(cards.size() - 1);
            c.setCostForTurn(c.costForTurn - this.amount);
            c.superFlash(Color.GOLD.cpy());
        }
        this.isDone = true;
    }
}

