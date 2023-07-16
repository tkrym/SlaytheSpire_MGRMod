package action;

import character.MGR_character;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class IncCounterAction extends AbstractGameAction
{
    public IncCounterAction(int amount)
    {
        this.amount=amount;
    }
    public void update()
    {
        MGR_character.IncCounter(this.amount);
        this.isDone=true;
    }
}
