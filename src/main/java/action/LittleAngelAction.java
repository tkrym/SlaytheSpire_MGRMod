package action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import power.FortePower;
import relic.LittleAngel;

public class LittleAngelAction extends AbstractGameAction {
    LittleAngel myrelic;
    public LittleAngelAction(LittleAngel myrelic) {this.myrelic=myrelic;}

    public void update() {
        if(myrelic==null)
        {
            this.isDone=true;
            return;
        }
        int SkillCount=0;
        for (AbstractCard c : AbstractDungeon.player.hand.group)
            if(c.type== AbstractCard.CardType.SKILL) ++SkillCount;
        //addToTop(new TalkAction(true,"SkillCount="+SkillCount+"Total="+AbstractDungeon.player.hand.group.size(),2.0F,2.0F));
        if(SkillCount<= LittleAngel.DrawThreshold)
        {
            myrelic.flash();
            addToTop(new DrawCardAction(LittleAngel.MAGIC));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, myrelic));
        }
        this.isDone=true;
    }
}

