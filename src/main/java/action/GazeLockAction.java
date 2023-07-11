package action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hook.OnChordHook;
import power.GazePower;

public class GazeLockAction extends AbstractGameAction {
    public GazeLockAction(AbstractCreature target) {
        this.setValues(target,AbstractDungeon.player,0);
        this.actionType = ActionType.DEBUFF;
    }

    public void update() {
        for (AbstractPower power : target.powers) {
            if (power.type== AbstractPower.PowerType.DEBUFF && !(power instanceof GazePower))
                this.amount+=Math.abs(power.amount);
        }
        if(this.amount>0)
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, source, new GazePower(target, this.amount), this.amount));
        this.isDone=true;
    }
}
