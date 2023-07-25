package action;

import card.COMMON.QuickShooting;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import note.AbstractNote;
import patch.ManualDiscardPatch;

import javax.swing.*;

public class QuickShootingAction extends AbstractGameAction {
    public static String[] TEXT=CardCrawlGame.languagePack.getUIString("ExhaustAction").TEXT;
    AbstractCard myCard;
    public QuickShootingAction(int amount, AbstractCard myCard, AbstractMonster target)
    {
        this.amount = amount;
        this.myCard=myCard;
        this.target=target;
        this.actionType = ActionType.EXHAUST;
        this.startDuration=Settings.ACTION_DUR_XFAST;
        this.duration= this.startDuration;
    }
    public void update()
    {
        AbstractPlayer p=AbstractDungeon.player;
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()||p.hand.size()<=0||this.amount<=0)
        {
            this.isDone = true;
            return;
        }
        if (this.duration == this.startDuration)
        {
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, true, true);
            p.hand.applyPowers();
            this.tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group)
            {
                p.hand.moveToDiscardPile(c);
                ManualDiscardPatch.triggerManualDiscard(c);
                c.triggerOnManualDiscard();
                GameActionManager.incrementDiscard(false);
                QuickShooting tmp = (QuickShooting) myCard.makeSameInstanceOf();
                AbstractDungeon.player.limbo.addToBottom(tmp);
                tmp.current_x = myCard.current_x;
                tmp.current_y = myCard.current_y;
                tmp.target_x = (((float) Settings.WIDTH) / 2.0f) - (300.0f * Settings.scale);
                tmp.target_y = ((float) Settings.HEIGHT) / 2.0f;
                tmp.calculateCardDamage((AbstractMonster)this.target);
                tmp.purgeOnUse = true;
                tmp.myPurgeOnUse=true;
                AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, (AbstractMonster)this.target , myCard.energyOnUse, true, true), true);
            }
            CardCrawlGame.dungeon.checkForPactAchievement();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        this.tickDuration();
    }
}

