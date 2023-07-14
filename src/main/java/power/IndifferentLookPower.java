package power;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hook.OnApplyGazeHook;
import hook.OnGazeTriggeredHook;

public class IndifferentLookPower extends AbstractPower implements OnApplyGazeHook, OnGazeTriggeredHook {
    public static final String POWER_ID = "MGR:IndifferentLook";
    private static final String IMG = "img/power/"+POWER_ID.substring(4)+".png";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public IndifferentLookPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type= PowerType.BUFF;
        updateDescription();
        this.img = new Texture(IMG);
    }



    @Override
    public void updateDescription() {this.description = DESCRIPTIONS[0]+this.amount+DESCRIPTIONS[1];}

    @Override
    public void OnApplyGaze(AbstractCreature m, int amount) {
        flashWithoutSound();
        addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.amount,true));
    }

    @Override
    public void OnGazeTriggered(AbstractCreature m, int amount) {
        flashWithoutSound();
        addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.amount,true));
    }
}
