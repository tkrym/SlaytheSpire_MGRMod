package power;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class HarmonyFormPower extends AbstractPower {
    public static final String POWER_ID = "MGR:HarmonyForm";
    private static final String IMG = "img/power/"+POWER_ID.substring(4)+".png";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public HarmonyFormPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount=1;
        updateDescription();
        this.img = new Texture(IMG);
    }

    @Override
    public void updateDescription()
    {
        this.description = DESCRIPTIONS[0]+this.amount+DESCRIPTIONS[1];
    }
}

