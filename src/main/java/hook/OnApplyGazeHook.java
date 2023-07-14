package hook;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import note.AbstractNote;

import java.util.ArrayList;

public interface OnApplyGazeHook {
    void OnApplyGaze(AbstractCreature m, int amount);
}
