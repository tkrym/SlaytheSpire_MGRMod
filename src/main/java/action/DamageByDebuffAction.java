package action;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;

public class DamageByDebuffAction extends AbstractGameAction
{
    private DamageInfo.DamageType DamageType;
    public DamageByDebuffAction(int amount, AbstractCreature target, DamageInfo.DamageType DamageType)
    {
        this.target = target;
        this.amount=amount;
        this.DamageType=DamageType;
        this.actionType = ActionType.DAMAGE;
    }

    public void update()
    {
        if(this.target==null)
        {
            this.isDone=true;
            return;
        }
        int sum=0;
        for(AbstractPower power:this.target.powers)
            if(power.type== AbstractPower.PowerType.DEBUFF)
                sum+= Math.abs(power.amount);
        addToTop(new VFXAction(new BiteEffect(this.target.hb.cX, this.target.hb.cY - (40.0f * Settings.scale), Color.PURPLE.cpy()), Settings.FAST_MODE?0.2F:0.5F));
        addToTop(new DamageAction(this.target, new DamageInfo(AbstractDungeon.player, this.amount*sum, this.DamageType),
                AttackEffect.BLUNT_HEAVY));
        this.isDone=true;
    }
}

