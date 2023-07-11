package patch;

import character.MGR_character;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class useCardPatches {

    @SpirePatch(clz = AbstractPlayer.class, method = "useCard",paramtypez={AbstractCard.class,AbstractMonster.class,int.class})
    public static class beforeuseCardPatch {
        @SpirePrefixPatch
        public static void Prefix(AbstractPlayer __instance,AbstractCard c, AbstractMonster monster, int energyOnUse) {
            if (AbstractDungeon.player instanceof MGR_character) {
                MGR_character p=(MGR_character)__instance;
                //do nothing
            }
        }
    }

}
