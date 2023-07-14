package action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import hook.OnApplyGazeHook;
import hook.OnGazeTriggeredHook;
import power.FortePower;
import power.GazePower;

public class ApplyGazeAction extends AbstractGameAction {
    public ApplyGazeAction(AbstractCreature target,int amount) {
        this.setValues(target,AbstractDungeon.player,amount);
        this.actionType = ActionType.DEBUFF;
    }

    public void update() {
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, source, new GazePower(target, this.amount), this.amount));
        for(AbstractPower power:AbstractDungeon.player.powers)
            if(power instanceof OnApplyGazeHook)
                ((OnApplyGazeHook)power).OnApplyGaze(target,this.amount);
        for (AbstractRelic relic : AbstractDungeon.player.relics)
            if (relic instanceof OnApplyGazeHook)
                ((OnApplyGazeHook) relic).OnApplyGaze(target,this.amount);
        this.isDone=true;
    }
}
