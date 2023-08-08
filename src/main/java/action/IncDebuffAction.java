package action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import power.FourEyesPower;
import power.GazePower;
import power.IndifferentLookPower;

public class IncDebuffAction extends AbstractGameAction
{
    private boolean upgraded;
    public IncDebuffAction(AbstractCreature target,boolean upgraded)
    {
        this.source = AbstractDungeon.player;
        this.upgraded=upgraded;
        this.target = target;
        this.actionType = ActionType.DEBUFF;
        this.startDuration = Settings.ACTION_DUR_XFAST;
        this.duration = this.startDuration;
    }

    public void update()
    {
        if (this.duration == this.startDuration && this.target != null)
        {
            int amt1=this.upgraded?2:2;
            int amt2=this.upgraded?3:1;
            /*if(this.upgraded)
            {
                if(this.target.hasPower(ConstrictedPower.POWER_ID)) addToTop(new ApplyPowerAction(this.target, this.source, new ConstrictedPower(this.target, this.source, 2), 2, true, AbstractGameAction.AttackEffect.NONE));
                else addToTop(new ApplyPowerAction(this.target, this.source, new ConstrictedPower(this.target, this.source, 1), 1, true, AbstractGameAction.AttackEffect.NONE));
            }*/
            if(this.target.hasPower(GazePower.POWER_ID)) addToTop(new ApplyPowerAction(this.target, this.source, new GazePower(this.target, amt2), amt2, true, AbstractGameAction.AttackEffect.NONE));
            else addToTop(new ApplyPowerAction(this.target, this.source, new GazePower(this.target, amt1), amt1, true, AbstractGameAction.AttackEffect.NONE));
            if(this.target.hasPower(VulnerablePower.POWER_ID)) addToTop(new ApplyPowerAction(this.target, this.source, new VulnerablePower(this.target, amt2, false), amt2, true, AbstractGameAction.AttackEffect.NONE));
            else addToTop(new ApplyPowerAction(this.target, this.source, new VulnerablePower(this.target, amt1, false), amt1, true, AbstractGameAction.AttackEffect.NONE));
            if(this.target.hasPower(WeakPower.POWER_ID)) addToTop(new ApplyPowerAction(this.target, this.source, new WeakPower(this.target, amt2, false), amt2, true, AbstractGameAction.AttackEffect.NONE));
            else addToTop(new ApplyPowerAction(this.target, this.source, new WeakPower(this.target, amt1, false), amt1, true, AbstractGameAction.AttackEffect.NONE));
        }
        this.tickDuration();
    }
}

