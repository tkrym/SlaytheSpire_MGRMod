package patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.unique.GamblingChipAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hook.OnExhaustCardHook;
import hook.OnManualDiscardHook;

public class OnExhaustCardPatch
{

    public static void OnExhaustCard(AbstractCard card)
    {
        for (AbstractCard c : AbstractDungeon.player.hand.group)
            if (c instanceof OnExhaustCardHook) ((OnExhaustCardHook) c).OnExhaustCard(card);
        for (AbstractCard c : AbstractDungeon.player.drawPile.group)
            if (c instanceof OnExhaustCardHook) ((OnExhaustCardHook) c).OnExhaustCard(card);
        for (AbstractCard c : AbstractDungeon.player.discardPile.group)
            if (c instanceof OnExhaustCardHook) ((OnExhaustCardHook) c).OnExhaustCard(card);
    }

    @SpirePatch(clz = CardGroup.class, method = "moveToExhaustPile")
    public static class OnExhaustCardHook1
    {
        @SpireInsertPatch(loc = 946, localvars = {"c"})
        public static void Insert(CardGroup __instance, AbstractCard c)
        {
            OnExhaustCard(c);
        }
    }
}

