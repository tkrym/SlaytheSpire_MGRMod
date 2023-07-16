package action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import note.AbstractNote;

import java.util.Iterator;

public class GenerateNotefromDrawnCardAction extends AbstractGameAction{
    public GenerateNotefromDrawnCardAction(int amount) {
        this.actionType = ActionType.WAIT;
        this.amount = amount;
    }
    public void update() {
        for (AbstractCard c : DrawCardAction.drawnCards) {
            for(int i=0;i<this.amount;++i)
                AbstractNote.GenerateNoteTop(c);
        }
        this.isDone = true;
    }
}
