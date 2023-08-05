package patch;

import character.MGR_character;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.unique.GamblingChipAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hook.OnExhaustCardHook;
import hook.OnManualDiscardHook;

import javax.swing.*;
import java.util.Objects;

public class ManualDiscardPatch
{

    public static void triggerManualDiscard(AbstractCard card)
    {
        for (AbstractPower power : AbstractDungeon.player.powers)
            if (power instanceof OnManualDiscardHook)
                ((OnManualDiscardHook) power).OnManualDiscard(card);
        for (AbstractCard c : AbstractDungeon.player.hand.group)
            if (c instanceof OnManualDiscardHook) ((OnManualDiscardHook) c).OnManualDiscard(card);
        for (AbstractCard c : AbstractDungeon.player.drawPile.group)
            if (c instanceof OnManualDiscardHook) ((OnManualDiscardHook) c).OnManualDiscard(card);
        for (AbstractCard c : AbstractDungeon.player.discardPile.group)
            if (c instanceof OnManualDiscardHook) ((OnManualDiscardHook) c).OnManualDiscard(card);
        /*for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group)
            if (c instanceof OnManualDiscardHook) ((OnManualDiscardHook) c).OnManualDiscard(card);*/
    }

    @SpirePatch(clz = DiscardAction.class, method = "update")
    public static class triggerManualDiscardHook1
    {
        @SpireInsertPatch(loc = 55, localvars = {"c"})
        public static void Insert(DiscardAction __instance, boolean ___endTurn, AbstractCard c)
        {
            if (!___endTurn) triggerManualDiscard(c);
        }
    }

    @SpirePatch(clz = DiscardAction.class, method = "update")
    public static class triggerManualDiscardHook2
    {
        @SpireInsertPatch(locs = {70, 97}, localvars = {"c"})
        public static void Insert(DiscardAction __instance, AbstractCard c)
        {
            triggerManualDiscard(c);
        }
    }

    @SpirePatch(clz = DiscardSpecificCardAction.class, method = "update")
    public static class triggerManualDiscardHook3
    {
        @SpireInsertPatch(loc = 37)
        public static void Insert(DiscardSpecificCardAction __instance, AbstractCard ___targetCard)
        {
            triggerManualDiscard(___targetCard);
        }
    }

    @SpirePatch(clz = GamblingChipAction.class, method = "update")
    public static class triggerManualDiscardHook4
    {
        @SpireInsertPatch(loc = 57, localvars = {"c"})
        public static void Insert(GamblingChipAction __instance, AbstractCard c)
        {
            triggerManualDiscard(c);
        }
    }
}

