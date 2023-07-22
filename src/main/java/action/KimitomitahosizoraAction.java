package action;

import card.COMMON.Kimitomitahosizora;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import note.AbstractNote;
import note.StarryNote;

public class KimitomitahosizoraAction extends AbstractGameAction {
    public static String[] TEXT=CardCrawlGame.languagePack.getUIString("KimitomitahosizoraAction").TEXT;
    public KimitomitahosizoraAction(int amount)
    {
        this.amount = amount;
        if (AbstractDungeon.player.hasRelic("GoldenEye")) {
            AbstractDungeon.player.getRelic("GoldenEye").flash();
            this.amount += 2;
        }
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration= Settings.ACTION_DUR_FAST;
    }
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {this.isDone = true;return;}
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            for (AbstractPower p : AbstractDungeon.player.powers) p.onScry();
            if (AbstractDungeon.player.drawPile.isEmpty()) {this.isDone = true;return;}
            CardGroup tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for(int i = 0; i < Math.min(this.amount, AbstractDungeon.player.drawPile.size()); ++i)
                tmpGroup.addToTop(AbstractDungeon.player.drawPile.group.get(AbstractDungeon.player.drawPile.size() - i - 1));
            AbstractDungeon.gridSelectScreen.open(tmpGroup, this.amount, true, TEXT[0]+ Kimitomitahosizora.THRESHOLD+TEXT[1]);
        }
        else if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            if(AbstractDungeon.gridSelectScreen.selectedCards.size()>=Kimitomitahosizora.THRESHOLD)
                addToTop(new ChannelNoteAction(new StarryNote()));
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
            {
                AbstractNote.GenerateNoteBottom(c);
                AbstractDungeon.player.drawPile.moveToDiscardPile(c);
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) c.triggerOnScry();
        this.tickDuration();
    }
}

