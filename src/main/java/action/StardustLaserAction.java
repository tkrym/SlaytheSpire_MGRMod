package action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import note.AbstractNote;
import note.StarryNote;
import patch.ManualDiscardPatch;

public class StardustLaserAction extends AbstractGameAction {
    public static String[] TEXT=CardCrawlGame.languagePack.getUIString("StardustLaserAction").TEXT;
    public StardustLaserAction(int amount)
    {
        this.amount = amount;
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
            AbstractDungeon.handCardSelectScreen.open(TEXT[0]+this.amount+TEXT[1], this.amount, true, true);
            p.hand.applyPowers();
            this.tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group)
            {
                addToTop(new ChannelNoteAction(new StarryNote()));
                p.hand.moveToDiscardPile(c);
                ManualDiscardPatch.triggerManualDiscard(c);
                c.triggerOnManualDiscard();
                GameActionManager.incrementDiscard(false);
            }
            int size=AbstractDungeon.handCardSelectScreen.selectedCards.group.size();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        this.tickDuration();
    }
}

