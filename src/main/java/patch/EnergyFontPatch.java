package patch;

import character.MGR_character;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EnergyFontPatch
{
    public static BitmapFont energyNumFontMGR;

    @SpirePatch(clz = FontHelper.class, method = "initialize")
    public static class EnergyFontMGRPatch
    {
        @SpireInsertPatch(loc = 370)
        public static void Insert(FreeTypeFontGenerator.FreeTypeFontParameter ___param)
        {
            ___param.borderColor = CardHelper.getColor(62, 146, 156);
            energyNumFontMGR = FontHelper.prepFont(36.0F, true);
        }
    }
}
