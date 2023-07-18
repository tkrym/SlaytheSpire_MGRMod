package relic;
import action.ChannelNoteAction;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import hook.OnChordHook;
import note.AbstractNote;
import note.AttackNote;
import note.DefendNote;

import java.util.ArrayList;

public class FriendsSpirit extends CustomRelic implements OnChordHook {
    public static final String ID = "MGR:FriendsSpirit";
    private static final String IMG = "img/relic/"+ID.substring(4)+".png";
    private static final String OUTLINE = "img/relic/outline/"+ID.substring(4)+".png";
    public static final int MAGIC = 1;
    public static final int Threshold = 2;

    public FriendsSpirit() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(OUTLINE), RelicTier.RARE, LandingSound.MAGICAL);
        this.counter=0;
    }

    public String getUpdatedDescription() { return this.DESCRIPTIONS[0]+Threshold+this.DESCRIPTIONS[1]; }

    @Override
    public AbstractRelic makeCopy() {
        return new FriendsSpirit();
    }

    @Override
    public void OnChord(ArrayList<AbstractNote> notes) {
        this.counter++;
        if(this.counter>=Threshold)
        {
            this.counter=0;
            this.flash();
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToTop(new ChannelNoteAction(new AttackNote()));
            addToTop(new ChannelNoteAction(new DefendNote()));
        }
    }
}
