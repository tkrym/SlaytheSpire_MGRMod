package action;
import character.MGR_character;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import hook.OnChordHook;
import hook.OnGazeTriggeredHook;
import power.GazePower;
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
                    this.target.tint.color = MGR_character.myBuleColor;
                    this.target.tint.changeColor(Color.WHITE.cpy());
                    this.target.damage(new DamageInfo(this.source, this.amount, DamageType.HP_LOSS));
                    if (this.target.isDying) {}
                }
                int dec=2;
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

