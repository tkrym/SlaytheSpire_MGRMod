package relic;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import stance.BigBrotherStance;

public class Sunglasses extends CustomRelic{
    public static final String ID = "MGR:Sunglasses";
    private static final String IMG = "img/relic/"+ID.substring(4)+".png";
    private static final String OUTLINE = "img/relic/outline/"+ID.substring(4)+".png";

    public Sunglasses() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(OUTLINE), RelicTier.COMMON, LandingSound.CLINK);
    }

    public String getUpdatedDescription() { return this.DESCRIPTIONS[0]; }

}
