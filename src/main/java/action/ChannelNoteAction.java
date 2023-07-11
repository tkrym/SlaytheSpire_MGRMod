package action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import java.util.Iterator;

public class ChannelNoteAction extends AbstractGameAction {
    private AbstractOrb orbType;

    public ChannelNoteAction(AbstractOrb newOrbType) {
        this.startDuration = 0.1F;
        this.duration = this.startDuration;
        this.orbType = newOrbType;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            AbstractDungeon.player.channelOrb(this.orbType);
            if (Settings.FAST_MODE) {
                this.isDone = true;
                return;
            }
        }
        this.tickDuration();
    }
}
