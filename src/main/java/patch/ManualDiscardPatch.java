package patch;

import character.MGR_character;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hook.OnManualDiscardHook;

import javax.swing.*;
import java.util.Objects;

public class ManualDiscardPatch {
    @SpirePatch(clz= DiscardAction.class, method = "update")
    public static class triggerManualDiscardHook1 {
        @SpireInsertPatch(loc = 58,localvars={"c"})
        public static void Insert(DiscardAction __instance,boolean ___endTurn, AbstractCard c) {
            if(!___endTurn)
            {
                for(AbstractPower power:AbstractDungeon.player.powers)
                    if(power instanceof OnManualDiscardHook)
                        ((OnManualDiscardHook)power).OnManualDiscard(c);
            }
        }
    }
    @SpirePatch(clz= DiscardAction.class, method = "update")
    public static class triggerManualDiscardHook2 {
        @SpireInsertPatch(locs = {71,98},localvars={"c"})
        public static void Insert(DiscardAction __instance,AbstractCard c) {
            for(AbstractPower power:AbstractDungeon.player.powers)
                if(power instanceof OnManualDiscardHook)
                    ((OnManualDiscardHook)power).OnManualDiscard(c);
        }
    }
}

