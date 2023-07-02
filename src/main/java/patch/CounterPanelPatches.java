package patch;

import character.MGR_character;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.core.OverlayMenu;

public class CounterPanelPatches {

    @SpirePatch(clz = OverlayMenu.class, method = "update")
    public static class updatePatch {
        @SpirePostfixPatch
        public static void Postfix() {
            if (AbstractDungeon.player instanceof MGR_character) {
                MGR_character p=(MGR_character)AbstractDungeon.player;
                p.updateCounterPanel();
            }
        }
    }

    @SpirePatch(clz = OverlayMenu.class, method = "showCombatPanels")
    public static class showCombatPatch {
        @SpirePrefixPatch
        public static void Prefix() {
            if (AbstractDungeon.player instanceof MGR_character) {
                MGR_character p=(MGR_character)AbstractDungeon.player;
                p.showCounterPanel();
            }
        }
    }

    @SpirePatch(clz = OverlayMenu.class, method = "hideCombatPanels")
    public static class hideCombatPatch {
        @SpirePrefixPatch
        public static void Prefix() {
            if (AbstractDungeon.player instanceof MGR_character) {
                MGR_character p=(MGR_character)AbstractDungeon.player;
                p.hideCounterPanel();
            }
        }
    }

    @SpirePatch(clz = OverlayMenu.class, method = "render", paramtypez = {SpriteBatch.class})
    public static class RenderPatch {
        @SpirePostfixPatch
        public static void Prefix(OverlayMenu OM,SpriteBatch sb) {
            if (AbstractDungeon.player instanceof MGR_character) {
                MGR_character p=(MGR_character)AbstractDungeon.player;
                p.renderCounterPanel(sb);
            }
        }
    }

}
