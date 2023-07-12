package relic;
import action.ApplyForteAction;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class TestRelic1 extends CustomRelic{
    public static final String ID = "MGR:TestRelic1";
    private static final String IMG = "img/relic/"+ID.substring(4)+".png";
    private static final String OUTLINE = "img/relic/outline/"+ID.substring(4)+".png";
    private static final int MAGIC = 2;
    private static final int DrawThreshold = 2;

    public TestRelic1() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(OUTLINE), RelicTier.RARE, LandingSound.CLINK);
    }

    @Override
    public void atTurnStartPostDraw() {
        int SkillCount=0;
        for (AbstractCard c : AbstractDungeon.player.hand.group)
            if(c.type== AbstractCard.CardType.SKILL) ++SkillCount;
        if(SkillCount<=DrawThreshold)
        {
            flash();
            addToTop(new DrawCardAction(MAGIC));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    public String getUpdatedDescription() { return this.DESCRIPTIONS[0]+DrawThreshold+this.DESCRIPTIONS[1]+MAGIC+this.DESCRIPTIONS[2]; }

    @Override
    public AbstractRelic makeCopy() {
        return new TestRelic1();
    }
}
