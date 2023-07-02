package action;

import character.MGR_character;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
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
        if(AbstractDungeon.player instanceof MGR_character)
        {
            MGR_character p=(MGR_character)AbstractDungeon.player;
            p.Inccounter(1);
        }
        this.isDone=true;
    }
}
