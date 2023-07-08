package action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AnimateNoteAction extends AbstractGameAction {

    public AnimateNoteAction(int amount) {
        this.amount = amount;
    }

    public void update() {
        for(int i = 0; i < this.amount; ++i) {
            AbstractDungeon.player.triggerEvokeAnimation(i);
        }

        this.isDone = true;
    }
}
