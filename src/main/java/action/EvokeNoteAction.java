package action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EvokeNoteAction extends AbstractGameAction {
    public EvokeNoteAction(int amount) {
        this.duration = this.startDuration;
        this.amount = amount;
        this.actionType = ActionType.DAMAGE;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            for(int i = 0; i < this.amount; ++i) {
                AbstractDungeon.player.evokeOrb();
            }
        }
        this.tickDuration();
    }
}

