package relic;
import basemod.abstracts.CustomRelic;
import card.COMMON.Marionette;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
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
    public static final int MAGIC = 3;
    public static final int DrawThreshold = 2;

    public LittleAngel() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(OUTLINE), RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStartPostDraw() {
        final int OriginCount= (int) AbstractDungeon.player.hand.group.stream().filter(c->c.type==AbstractCard.CardType.SKILL/*&&c.cost!=-2*/).count();
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int amt= (int) (AbstractDungeon.player.hand.group.stream().filter(c->c.type==AbstractCard.CardType.SKILL/*&&c.cost!=-2*/).count()-OriginCount);
                if(amt< LittleAngel.DrawThreshold)
                {
                    LittleAngel.this.flash();
                    addToTop(new DrawCardAction(LittleAngel.MAGIC*(LittleAngel.DrawThreshold-amt)));
                    addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, LittleAngel.this));
                }
                this.isDone=true;
            }
        });
    }

    public String getUpdatedDescription() { return this.DESCRIPTIONS[0]+DrawThreshold+this.DESCRIPTIONS[1]+MAGIC+this.DESCRIPTIONS[2]; }

    @Override
    public AbstractRelic makeCopy() {
        return new LittleAngel();
    }
}
