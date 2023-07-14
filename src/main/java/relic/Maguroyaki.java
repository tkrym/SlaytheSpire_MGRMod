package relic;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnSmithRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class Maguroyaki extends CustomRelic implements BetterOnSmithRelic {
    public static final String ID = "MGR:Maguroyaki";
    private static final String IMG = "img/relic/"+ID.substring(4)+".png";
    private static final String OUTLINE = "img/relic/outline/"+ID.substring(4)+".png";
    private static final int MAGIC = 15;

    public Maguroyaki() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(OUTLINE), RelicTier.SHOP, LandingSound.MAGICAL);
    }

    public void betterOnSmith(AbstractCard card) {
        this.flash();
        AbstractDungeon.player.heal(Math.round((float)AbstractDungeon.player.maxHealth*(float)MAGIC/100.0F));
    }

    public String getUpdatedDescription() { return this.DESCRIPTIONS[0]+MAGIC+this.DESCRIPTIONS[1]; }

    @Override
    public AbstractRelic makeCopy() {
        return new Maguroyaki();
    }

    @Override
    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.floorNum <= 60;
    }

}
