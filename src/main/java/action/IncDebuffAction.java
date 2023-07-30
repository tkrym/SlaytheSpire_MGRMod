package action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import power.FourEyesPower;
import power.GazePower;
import power.IndifferentLookPower;

public class IncDebuffAction extends AbstractGameAction
{
    private int GazeAmount;
    public IncDebuffAction(AbstractCreature target,int amount,int GazeAmount)
    {
        this.source = AbstractDungeon.player;
        this.amount=amount;
        this.GazeAmount=GazeAmount;
        this.target = target;
        this.actionType = ActionType.DEBUFF;
        this.startDuration = Settings.ACTION_DUR_XFAST;
        this.duration = this.startDuration;
    }

    public void update()
    {
        if (this.duration == this.startDuration && this.target != null)
        {
            for (AbstractPower power : this.target.powers)
                if (power.type == AbstractPower.PowerType.DEBUFF)
                {
                    int zf=power.amount>=0?1:-1;
                    if (!(power instanceof GazePower))
                    {
                        FourEyesPower.Trigger(this.target, Math.abs(power.amount));
                        power.stackPower(this.amount * zf);
                    }else
                    {
                        IndifferentLookPower.Trigger(power.amount);
                        power.stackPower(GazeAmount);
                    }
                    power.flashWithoutSound();
                    power.updateDescription();
                }
        }
        this.tickDuration();
    }
}

