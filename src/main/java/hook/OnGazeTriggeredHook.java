package hook;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public interface OnGazeTriggeredHook {
    void OnGazeTriggered(AbstractCreature m, int amount);
}
