package action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import java.util.Iterator;

public class ChannelNoteAction extends AbstractGameAction {
    private AbstractOrb orbType;
    private boolean autoEvoke;

    public ChannelNoteAction(AbstractOrb newOrbType) {
        this(newOrbType, true);
    }

    public ChannelNoteAction(AbstractOrb newOrbType, boolean autoEvoke) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.orbType = newOrbType;
        this.autoEvoke = autoEvoke;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.player.channelOrb(this.orbType);
            if (Settings.FAST_MODE) {
                this.isDone = true;
                return;
            }
        }
        this.tickDuration();
    }
}
