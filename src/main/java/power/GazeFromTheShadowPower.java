package power;

import action.ApplyGazeAction;
import action.GazeLoseHpAction;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import hook.OnChordHook;
import note.AbstractNote;

import java.util.ArrayList;

public class GazeFromTheShadowPower extends AbstractPower{
    public static final String POWER_ID = "MGR:GazeFromTheShadowPower";
    private static final String IMG = "img/power/"+POWER_ID.substring(4)+".png";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public GazeFromTheShadowPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.type=PowerType.DEBUFF;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        this.img = new Texture(IMG);
        this.canGoNegative = false;
    }

    @Override
    public void atStartOfTurn()
    {
        addToBot(new ApplyGazeAction(this.owner,this.amount));
    }


    @Override
    public void updateDescription() {
        this.description=DESCRIPTIONS[0]+this.amount+DESCRIPTIONS[1];
    }
}
