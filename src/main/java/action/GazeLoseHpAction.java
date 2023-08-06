package action;

import character.MGR_character;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import hook.OnChordHook;
import hook.OnGazeTriggeredHook;
import power.GazePower;
import power.SalivatePower;
import relic.BloodshotEyeball;
import relic.Sunglasses;

public class GazeLoseHpAction extends AbstractGameAction
{
    private static final float DURATION = 0.05F;

    public GazeLoseHpAction(AbstractCreature target)
    {
        this.target = target;
        this.source = AbstractDungeon.player;
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update()
    {
        this.tickDuration();
        if (this.isDone)
        {
            AbstractPower p = this.target.getPower(GazePower.POWER_ID);
            if (p == null) return;
            this.amount = p.amount;
            if (this.target.currentHealth > 0)
            {
                p.flash();
                p.playApplyPowerSfx();
                this.amount=GazePower.applyVulnerable(this.target,this.amount);
                this.target.damage(new DamageInfo(this.source, this.amount, DamageType.HP_LOSS));
                if (AbstractDungeon.player.hasPower(SalivatePower.POWER_ID) && (this.target.isDying || this.target.currentHealth <= 0) && !this.target.halfDead && !this.target.hasPower(MinionPower.POWER_ID))
                {
                    addToTop(new WaitAction(0.1f));
                    addToTop(new VFXAction(AbstractDungeon.player, new BiteEffect(this.target.hb.cX, this.target.hb.cY - (40.0f * Settings.scale), Color.SCARLET.cpy()), 0.1f, true));
                    AbstractDungeon.player.increaseMaxHp(AbstractDungeon.player.getPower(SalivatePower.POWER_ID).amount, false);
                }
            }
            float dec = ((float) GazePower.DecreaseAmount) / 100.0f;
            if (AbstractDungeon.player.hasRelic(Sunglasses.ID))
            {
                dec = ((float) Sunglasses.SunglassesNumber) / 100.0f;
                AbstractDungeon.player.getRelic(Sunglasses.ID).flash();
            }
            p.amount -= MathUtils.ceil(dec * (float) p.amount);
            if (p.amount < 1 && AbstractDungeon.player.hasRelic(BloodshotEyeball.ID)) p.amount = 1;
            if (p.amount <= 0) this.target.powers.remove(p);
            else p.updateDescription();
            for (AbstractPower power : AbstractDungeon.player.powers)
                if (power instanceof OnGazeTriggeredHook)
                    ((OnGazeTriggeredHook) power).OnGazeTriggered(target, this.amount);
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
            {
                AbstractDungeon.actionManager.clearPostCombatActions();
                return;
            }
        }
    }
}

