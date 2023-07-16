package action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import relic.LittleAngel;

public class SongOfLevitationAction extends AbstractGameAction {
    private int NoteCount;
    private int EmptyCount;
    public SongOfLevitationAction(int block,int NoteCount,int EmptyCount)
    {
        this.amount=block;
        this.NoteCount=NoteCount;
        this.EmptyCount=EmptyCount;
    }
    public void update() {
        addToTop(new DrawCardAction(NoteCount));
        for(int i=0;i<EmptyCount;i++)
            addToTop(new GainBlockAction(AbstractDungeon.player,AbstractDungeon.player,this.amount));
        this.isDone=true;
    }
}

