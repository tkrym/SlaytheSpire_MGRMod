package relic;
import action.ChannelNoteAction;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import note.DrawNote;
import stance.AwakenedStance;

public class OldBow extends CustomRelic{
    public static final String ID = "MGR:OldBow";
    private static final String IMG = "img/relic/"+ID.substring(4)+".png";
    private static final String OUTLINE = "img/relic/outline/"+ID.substring(4)+".png";
    private static final int MAGIC=6;

    public OldBow() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(OUTLINE), RelicTier.COMMON, LandingSound.FLAT);
        this.counter=MAGIC;
    }

    public String getUpdatedDescription() { return this.DESCRIPTIONS[0]+MAGIC+this.DESCRIPTIONS[1]; }

    @Override
    public void onManualDiscard() { UpdateCounter();}

    @Override
    public void onExhaust(AbstractCard card) { UpdateCounter();}

    private void UpdateCounter()
    {
        this.counter--;
        if(this.counter<=0)
        {
            this.counter=MAGIC;
            this.flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new DrawCardAction(1));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new OldBow();
    }

}
