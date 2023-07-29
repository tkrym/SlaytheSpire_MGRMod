package action;

import card.UNCOMMON.ChristmasGift;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import power.ClimaxPower;

import java.util.ArrayList;
import java.util.Iterator;

public class ChristmasGiftAction extends AbstractGameAction
{
    private boolean upgraded;
    private AbstractCard card;

    public ChristmasGiftAction(int amount, boolean upgraded)
    {
        this.startDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startDuration;
        this.amount = amount;
        this.card=null;
        this.upgraded = upgraded;
    }

    public void update()
    {
        if (this.duration == this.startDuration)
        {
            int seed = AbstractDungeon.miscRng.random(99);
            if (!this.upgraded)
            {
                if (seed < ChristmasGift.PRO_POTION)
                {
                    AbstractDungeon.getCurrRoom().addPotionToRewards(AbstractDungeon.returnRandomPotion());
                }
                else if (seed < ChristmasGift.PRO_POTION + ChristmasGift.PRO_GOLD)
                {
                    AbstractDungeon.effectList.add(new RainingGoldEffect(this.amount * 2, true));
                    addToTop(new GainGoldAction(this.amount));
                }
                else
                {
                    SelectCard();
                }
            }else
            {
                if (seed < ChristmasGift.PRO_POTION_UPGRADED)
                {
                    AbstractDungeon.getCurrRoom().addPotionToRewards(AbstractDungeon.returnRandomPotion());
                }
                else if (seed < ChristmasGift.PRO_POTION_UPGRADED + ChristmasGift.PRO_GOLD_UPGRADED)
                {
                    AbstractDungeon.effectList.add(new RainingGoldEffect(this.amount * 2, true));
                    addToTop(new GainGoldAction(this.amount));
                }
                else
                {
                    SelectCard();
                }
            }
        }
        this.tickDuration();
        if (this.isDone && this.card != null)
        {
            this.card.upgrade();
            AbstractDungeon.player.bottledCardUpgradeCheck(this.card);
            AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(((float) Settings.WIDTH) / 2.0f, ((float) Settings.HEIGHT) / 2.0f));
            AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(this.card.makeStatEquivalentCopy()));
            addToTop(new WaitAction(0.1f));
        }
    }

    private void SelectCard()
    {
        ArrayList<AbstractCard> possibleCards = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
            if (c.canUpgrade()) possibleCards.add(c);
        if (!possibleCards.isEmpty())
        {
            this.card = possibleCards.get(AbstractDungeon.miscRng.random(0, possibleCards.size() - 1));
        }
    }
}
