package action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class IncCounterAction extends AbstractGameAction
{
    public IncCounterAction()
    {
        this.startDuration = 0.1F;
        this.duration = this.startDuration;
    }
    public void update()
    {
        if (this.duration == this.startDuration) {
            this.addToTop(new TalkAction(true,"Inc",3.0F,4.0F));
        }
        this.tickDuration();
    }
}
