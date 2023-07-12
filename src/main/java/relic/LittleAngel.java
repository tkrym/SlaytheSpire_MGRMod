package relic;
import action.LittleAngelAction;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class LittleAngel extends CustomRelic{
    public static final String ID = "MGR:LittleAngel";
    private static final String IMG = "img/relic/"+ID.substring(4)+".png";
    private static final String OUTLINE = "img/relic/outline/"+ID.substring(4)+".png";
    public static final int MAGIC = 2;
    public static final int DrawThreshold = 1;

    public LittleAngel() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(OUTLINE), RelicTier.RARE, LandingSound.CLINK);
    }

    @Override
    public void atTurnStartPostDraw() {
        addToBot(new LittleAngelAction(this));
    }

    public String getUpdatedDescription() { return this.DESCRIPTIONS[0]+DrawThreshold+this.DESCRIPTIONS[1]+MAGIC+this.DESCRIPTIONS[2]; }

    @Override
    public AbstractRelic makeCopy() {
        return new LittleAngel();
    }
}
