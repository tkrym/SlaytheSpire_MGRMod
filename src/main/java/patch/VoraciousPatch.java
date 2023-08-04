package patch;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BlueCandle;
import com.megacrit.cardcrawl.relics.MedicalKit;
import relic.Voracious;

public class VoraciousPatch
{

    @SpirePatch(clz = AbstractPlayer.class, method = "hasRelic")
    public static class hasRelicPatch
    {
        @SpireInsertPatch(rloc = 0)
        public static SpireReturn Insert(AbstractPlayer p, String targetID)
        {
            boolean VoraciousWorks = (targetID.equals(BlueCandle.ID) || targetID.equals(MedicalKit.ID)) && AbstractDungeon.player.hasRelic(Voracious.ID);
            return VoraciousWorks ? SpireReturn.Return(true) : SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = AbstractRelic.class, method = "canSpawn")
    public static class canSpawnPatch
    {
        @SpireInsertPatch(rloc = 0)
        public static SpireReturn Insert(AbstractRelic r)
        {
            boolean VoraciousWorks = (r instanceof BlueCandle || r instanceof MedicalKit) && AbstractDungeon.player.hasRelic(Voracious.ID);
            return VoraciousWorks ? SpireReturn.Return(false) : SpireReturn.Continue();
        }
    }
}
