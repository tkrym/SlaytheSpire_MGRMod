package patch;

import card.COMMON.Marionette;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AngerPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BlueCandle;
import com.megacrit.cardcrawl.relics.MedicalKit;
import relic.Voracious;

public class AngerPowerPatch
{

    @SpirePatch(clz = AngerPower.class, method = "onUseCard")
    public static class AngerPowerReturnPatch
    {
        @SpireInsertPatch(rloc = 0)
        public static SpireReturn Insert(AngerPower power, AbstractCard card, UseCardAction action)
        {
            boolean Works=card instanceof Marionette;
            return Works ? SpireReturn.Return(): SpireReturn.Continue();
        }
    }

}
