package action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import power.FortePower;

public class ApplyForteAction extends AbstractGameAction {
    public ApplyForteAction(int amount) {
        this.duration = this.startDuration;
        this.actionType = ActionType.POWER;
        this.amount=amount;
    }

    public void update() {
        AbstractCreature p=AbstractDungeon.player;
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new FortePower(p, this.amount), this.amount));
        this.isDone=true;
    }
}
