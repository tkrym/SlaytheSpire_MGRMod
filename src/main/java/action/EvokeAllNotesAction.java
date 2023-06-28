package action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.AnimateOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EvokeAllNotesAction extends AbstractGameAction
{
    public EvokeAllNotesAction() {
        this.actionType = ActionType.DAMAGE;
    }
    public void update() {
        for(int i = 0; i < AbstractDungeon.player.orbs.size(); ++i) {
            this.addToTop(new EvokeNoteAction(1));
            this.addToTop(new AnimateOrbAction(1));
        }
        this.isDone = true;
    }
}
