package action;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.RelicAboveCreatureEffect;
import effect.NoteAboveCreatureEffect;
import note.AbstractNote;

public class NoteDamageEnemyAction extends AbstractGameAction {
    private boolean HitAll;
    private static final Texture AttackNoteIMG= ImageMaster.loadImage("img/note/Attack.png");

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
                addToTop(new DamageAction(m, info, AttackEffect.NONE, true));
                addToTop(new VFXAction(new NoteAboveCreatureEffect(m.hb.cX - this.source.animX, m.hb.cY + m.hb.height / 3.0F - m.animY, AttackNoteIMG), Settings.ACTION_DUR_XFAST));
            }
        } else
        {
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (!m.isDeadOrEscaped() && !m.halfDead) {
                    DamageInfo info=new DamageInfo(source,AbstractNote.applyVulnerable(m, this.amount), DamageInfo.DamageType.THORNS);
                    addToTop(new DamageAction(m, info, AttackEffect.NONE, true));
                    addToTop(new VFXAction(new NoteAboveCreatureEffect(m.hb.cX - m.animX, m.hb.cY + m.hb.height / 2.0F - m.animY, AttackNoteIMG,m.hb.height*0.3f,m.hb.height*1.4f,2.5f), Settings.ACTION_DUR_XFAST));
                }
            }
        }
        //this.addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));
        this.isDone = true;
    }
}