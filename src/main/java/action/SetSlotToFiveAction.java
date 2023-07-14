package action;

import character.MGR_character;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SetSlotToFiveAction extends AbstractGameAction {
    public SetSlotToFiveAction() {
        this.actionType = ActionType.POWER;
    }

    public void update() {
        if(AbstractDungeon.player instanceof MGR_character)
            ((MGR_character)AbstractDungeon.player).SetSlotToFive();
        this.isDone=true;
    }
}

