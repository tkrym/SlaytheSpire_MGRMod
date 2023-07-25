package action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import power.ClimaxPower;

import java.util.Iterator;

public class ChannelNoteAction extends AbstractGameAction {
    private AbstractOrb orbType;
    private boolean IsClimax;

    public ChannelNoteAction(AbstractOrb newOrbType,boolean Climax) {
        this.startDuration = 0.1F;
        this.duration = this.startDuration;
        this.orbType = newOrbType;
        this.IsClimax=Climax;
    }

    public ChannelNoteAction(AbstractOrb newOrbType) {
        this(newOrbType,false);
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if(!this.IsClimax&&AbstractDungeon.player.hasPower(ClimaxPower.POWER_ID))
                for(int i=1;i<=AbstractDungeon.player.getPower(ClimaxPower.POWER_ID).amount;i++)
                    addToTop(new ChannelNoteAction(orbType.makeCopy(),true));
            AbstractDungeon.player.channelOrb(this.orbType);
            if (Settings.FAST_MODE) {
                this.isDone = true;
                return;
            }
        }
        this.tickDuration();
    }
}
