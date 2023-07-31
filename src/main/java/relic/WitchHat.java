package relic;
import action.ChannelNoteAction;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import note.DefendNote;

public class WitchHat extends CustomRelic{
    public static final String ID = "MGR:WitchHat";
    private static final String IMG = "img/relic/"+ID.substring(4)+".png";
    private static final String OUTLINE = "img/relic/outline/"+ID.substring(4)+".png";
    private static final int THRESHOLD1=67;
    private static final int THRESHOLD2=33;

    public WitchHat() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription() { return this.DESCRIPTIONS[0]+THRESHOLD1+this.DESCRIPTIONS[1]+THRESHOLD2+this.DESCRIPTIONS[2]; }

    public void atTurnStart() {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int HPProportion=Math.round(100.0F*((float)AbstractDungeon.player.currentHealth)/((float)AbstractDungeon.player.maxHealth));
                if(HPProportion<=THRESHOLD1)
                {
                    WitchHat.this.flash();
                    addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, WitchHat.this));
                    addToBot(new ChannelNoteAction(new DefendNote()));
                }
                if(HPProportion<=THRESHOLD2)
                    addToBot(new ChannelNoteAction(new DefendNote()));
                addToBot(new ChannelNoteAction(new DefendNote()));
                this.isDone = true;
            }
        });
    }

    @Override
    public AbstractRelic makeCopy() {
        return new WitchHat();
    }

}
