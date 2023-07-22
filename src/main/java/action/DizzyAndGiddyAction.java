package action;

import card.COMMON.Kimitomitahosizora;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import note.AbstractNote;
import note.StarryNote;
import patch.ManualDiscardPatch;

import java.util.Iterator;

public class DizzyAndGiddyAction extends AbstractGameAction {
    public static String[] TEXT=CardCrawlGame.languagePack.getUIString("DizzyAndGiddyAction").TEXT;
    private int DrawThreshold;
    private int DrawAmount;
    public DizzyAndGiddyAction(int amount,int threshold,int drawamount)
    {
        this.amount = amount;
        this.DrawThreshold=threshold;
        this.DrawAmount=drawamount;
        this.actionType = ActionType.DISCARD;
        this.startDuration=Settings.ACTION_DUR_XFAST;
        this.duration= this.startDuration;
    }
    public void update()
    {
        AbstractPlayer p=AbstractDungeon.player;
        if (this.duration == this.startDuration)
        {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()||p.hand.size()<=0||this.amount<=0)
            {
                this.isDone = true;
                return;
            }
            AbstractDungeon.handCardSelectScreen.open(TEXT[0]+this.amount+TEXT[1]+this.DrawThreshold+TEXT[2]+this.DrawAmount+TEXT[3], this.amount, true, true);
            p.hand.applyPowers();
            this.tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            if(AbstractDungeon.handCardSelectScreen.selectedCards.group.size()>=this.DrawThreshold)
                addToTop(new DrawCardAction(this.DrawAmount));
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group)
            {
                p.hand.moveToDiscardPile(c);
                ManualDiscardPatch.triggerManualDiscard(c);
                AbstractNote.GenerateNoteBottom(c);
                c.triggerOnManualDiscard();
                GameActionManager.incrementDiscard(false);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        this.tickDuration();
    }
}

