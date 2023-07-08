package action;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import power.GazePower;

public class GazeLoseHpAction extends AbstractGameAction {
    private static final float DURATION = 0.05F;

    public GazeLoseHpAction(AbstractCreature target, int amount) {
        this.setValues(target, AbstractDungeon.player, amount);
        this.actionType = ActionType.DAMAGE;
        this.duration = DURATION;
    }

    public void update() {
        if (AbstractDungeon.getCurrRoom().phase != RoomPhase.COMBAT) {
            this.isDone = true;
        } else {
            /*if (this.duration == DURATION && this.target.currentHealth > 0) {
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect,true));
            }*/
            this.tickDuration();
            if (this.isDone) {
                AbstractPower p = this.target.getPower(GazePower.POWER_ID);
                if (p != null) {
                    p.amount-=GazePower.DecreaseAmount;
                    p.flashWithoutSound();
                    if (p.amount <= 0) this.target.powers.remove(p);
                    else p.updateDescription();
                }

                if (this.target.currentHealth > 0) {
                    this.target.tint.color = new Color(2147483647);
                    this.target.tint.changeColor(Color.WHITE.cpy());
                    AbstractDungeon.actionManager.addToTop(new DamageAction(target,new DamageInfo(source,amount,DamageType.THORNS),true));
                    //this.target.damage(new DamageInfo(this.source, this.amount, DamageType.THORNS));
                    if (this.target.isDying) {}
                }

                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                }
            }

        }
    }
}

