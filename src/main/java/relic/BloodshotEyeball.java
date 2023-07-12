package relic;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class BloodshotEyeball extends CustomRelic{
    public static final String ID = "MGR:BloodshotEyeball";
    private static final String IMG = "img/relic/"+ID.substring(4)+".png";
    private static final String OUTLINE = "img/relic/outline/"+ID.substring(4)+".png";

    public BloodshotEyeball() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription() { return this.DESCRIPTIONS[0]; }

    @Override
    public AbstractRelic makeCopy() {
        return new BloodshotEyeball();
    }

}
