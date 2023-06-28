package action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AnimateNoteAction extends AbstractGameAction {
    private int orbCount;

    public AnimateNoteAction(int amount) {
        this.orbCount = amount;
    }

    public void update() {
        for(int i = 0; i < this.orbCount; ++i) {
            AbstractDungeon.player.triggerEvokeAnimation(i);
        }

        this.isDone = true;
    }
}
