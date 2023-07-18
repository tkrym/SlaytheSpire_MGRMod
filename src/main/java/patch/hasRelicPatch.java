package patch;

import character.MGR_character;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import java.util.Objects;

public class hasRelicPatch {
    @SpirePatch(clz=AbstractPlayer.class, method = "hasRelic")
    public static class hasRelicResurrect {
        @SpireInsertPatch(rloc = 0)
        public static SpireReturn Insert(AbstractPlayer p, String targetID) {
            return !(p instanceof MGR_character) || (!Objects.equals(targetID, "Blue Candle") && !Objects.equals(targetID, "Medical Kit")) ? SpireReturn.Continue() : SpireReturn.Return(true);
        }
    }
}

