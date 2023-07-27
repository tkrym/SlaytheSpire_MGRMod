package action;
import character.MGR_character;
import com.badlogic.gdx.graphics.Color;
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
import power.TestCard3Power;
import relic.Sunglasses;

public class GazeLoseHpAction extends AbstractGameAction {
    private static final float DURATION = 0.05F;

    public GazeLoseHpAction(AbstractCreature target) {
        this.target=target;
        this.source=AbstractDungeon.player;
        this.actionType = ActionType.DAMAGE;
        this.duration = DURATION;
    }

    public void update() {
        if (AbstractDungeon.getCurrRoom().phase != RoomPhase.COMBAT) {
            this.isDone = true;
        } else {
            this.tickDuration();
            if (this.isDone) {
                AbstractPower p = this.target.getPower(GazePower.POWER_ID);
                if(p==null) return;
                this.amount=p.amount;
                if (this.target.currentHealth > 0) {
                    this.target.damage(new DamageInfo(this.source, this.amount, DamageType.HP_LOSS));
                    if (AbstractDungeon.player.hasPower(TestCard3Power.POWER_ID)&&(this.target.isDying||this.target.currentHealth <= 0)&&!this.target.halfDead&&!this.target.hasPower(MinionPower.POWER_ID))
                    {
                        addToTop(new WaitAction(0.1f));
                        addToTop(new VFXAction(AbstractDungeon.player,new BiteEffect(this.target.hb.cX, this.target.hb.cY - (40.0f * Settings.scale), Color.SCARLET.cpy()), 0.1f,true));
                        AbstractDungeon.player.increaseMaxHp(AbstractDungeon.player.getPower(TestCard3Power.POWER_ID).amount, false);
                    }
                }
                int dec=3;
                if(AbstractDungeon.player.hasRelic(Sunglasses.ID)) {dec=0;AbstractDungeon.player.getRelic(Sunglasses.ID).flash();}
                p.amount-=dec;
                p.flashWithoutSound();
                if (p.amount <= 0) this.target.powers.remove(p);
                else p.updateDescription();
                for(AbstractPower power:AbstractDungeon.player.powers)
                    if(power instanceof OnGazeTriggeredHook)
                        ((OnGazeTriggeredHook)power).OnGazeTriggered(target,this.amount);
                for (AbstractRelic relic : AbstractDungeon.player.relics)
                    if (relic instanceof OnGazeTriggeredHook)
                        ((OnGazeTriggeredHook) relic).OnGazeTriggered(target,this.amount);
                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                    return;
                }
            }

        }
    }
}

