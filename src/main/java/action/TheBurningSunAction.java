package action;

import card.COMMON.QuickShooting;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import note.AbstractNote;
import patch.ManualDiscardPatch;

public class TheBurningSunAction extends AbstractGameAction
{
    public static String[] TEXT = CardCrawlGame.languagePack.getUIString("TheBurningSunAction").TEXT;
    private boolean B;

    public TheBurningSunAction(int amount, boolean B)
    {
        this.amount = amount;
        this.B = B;
        this.actionType = ActionType.EXHAUST;
        this.startDuration = Settings.ACTION_DUR_XFAST;
        this.duration = this.startDuration;
    }

    public void update()
    {
        AbstractPlayer p = AbstractDungeon.player;
        if (this.duration == this.startDuration)
        {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead() || p.hand.size() <= 0 || this.amount <= 0)
            {
                this.isDone = true;
                return;
            }
            AbstractDungeon.handCardSelectScreen.open(this.B ? TEXT[1] : TEXT[0], this.amount, true, true);
            p.hand.applyPowers();
            this.tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group)
            {
                p.hand.moveToExhaustPile(c);
                if (this.B) addToTop(new TemporaryDuplicationAction(c));
            }
            addToTop(new ApplyPowerAction(p,p,new DrawCardNextTurnPower(p,AbstractDungeon.handCardSelectScreen.selectedCards.size())));
            //addToTop(new DrawCardAction(AbstractDungeon.handCardSelectScreen.selectedCards.size()));
            CardCrawlGame.dungeon.checkForPactAchievement();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.applyPowers();
        }
        this.tickDuration();
    }
}

