package action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DoubleDebuffAction extends AbstractGameAction
{
    public DoubleDebuffAction(AbstractCreature target)
    {
        this.source=AbstractDungeon.player;
        this.target = target;
        this.actionType = ActionType.DEBUFF;
        this.startDuration = Settings.ACTION_DUR_XFAST;
        this.duration=this.startDuration;
    }

    public void update()
    {
        if (this.duration == this.startDuration && this.target != null)
        {
            for(AbstractPower power:this.target.powers)
                if(power.type== AbstractPower.PowerType.DEBUFF)
                {
                    power.stackPower(power.amount);
                    power.flashWithoutSound();
                    power.updateDescription();
                   // addToTop(new ApplyPowerAction(this.target, this.source, power, power.amount));
                }
        }
        this.tickDuration();
    }
}

