package power;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class UnisonRight extends AbstractPower {
    public static final String POWER_ID = "MGR:UnisonRight";
    private static final String IMG = "img/power/"+POWER_ID.substring(4)+".png";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public UnisonRight(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount=amount;
        updateDescription();
        this.img = new Texture(IMG);
        this.canGoNegative=false;
    }

    @Override
    public void updateDescription()
    {
        if(this.amount==1) this.description=DESCRIPTIONS[0];
        else this.description=DESCRIPTIONS[1]+this.amount+DESCRIPTIONS[2];
    }
}

