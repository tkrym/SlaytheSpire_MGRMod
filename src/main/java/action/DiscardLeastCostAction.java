package action;

import card.COMMON.Kimitomitahosizora;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import note.AbstractNote;
import note.StarryNote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class DiscardLeastCostAction extends AbstractGameAction {
    public DiscardLeastCostAction(int amount)
    {
        this.amount = amount;
        this.actionType = ActionType.DISCARD;
    }
    public void update() {
        AbstractPlayer p=AbstractDungeon.player;
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()||p.hand.group.size()==0)
        {
            this.isDone=true;
            return;
        }
        int DiscardAmount=Math.min(this.amount,p.hand.group.size());
        ArrayList<AbstractCard> cards=new ArrayList<>(p.hand.group);
        Collections.shuffle(cards,new Random(AbstractDungeon.miscRng.randomLong()));
        cards.sort(Comparator.comparingInt(c -> c.cost));
        for(int i=0;i<DiscardAmount;i++)
            addToTop(new DiscardSpecificCardAction(cards.get(i)));
        this.isDone=true;
    }
}

