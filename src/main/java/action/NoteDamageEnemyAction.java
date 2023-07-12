package action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import note.AbstractNote;

public class NoteDamageEnemyAction extends AbstractGameAction {
    private boolean HitAll;

    public NoteDamageEnemyAction(int amount,boolean HitAll) {
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.attackEffect = AttackEffect.NONE;
        this.HitAll=HitAll;
        this.source=AbstractDungeon.player;
        this.amount=amount;
    }

    @Override
    public void update() {
        //float speedTime = Settings.FAST_MODE?0.0F:0.05F;
        if (!this.HitAll)
        {
            AbstractCreature m = AbstractDungeon.getRandomMonster();
            if (m != null)
            {
                DamageInfo info=new DamageInfo(source,AbstractNote.applyVulnerable(m, this.amount), DamageInfo.DamageType.THORNS);
                this.addToTop(new DamageAction(m, info, AttackEffect.NONE, true));
                //this.addToTop(new VFXAction(new LightningEffect(m.drawX, m.drawY), speedTime));
            }
        } else
        {
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (!m.isDeadOrEscaped() && !m.halfDead) {
                    DamageInfo info=new DamageInfo(source,AbstractNote.applyVulnerable(m, this.amount), DamageInfo.DamageType.THORNS);
                    this.addToTop(new DamageAction(m, info, AttackEffect.NONE, true));
                }
            }
        }
        //this.addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));
        this.isDone = true;
    }
}