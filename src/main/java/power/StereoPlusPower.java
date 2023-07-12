package power;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class StereoPlusPower extends AbstractPower {
    public static final String POWER_ID = "MGR:StereoPlus";
    private static final String IMG = "img/power/"+POWER_ID.substring(4)+".png";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public StereoPlusPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        updateDescription();
        this.img = new Texture(IMG);
    }
    
    @Override
    public void updateDescription() {this.description = DESCRIPTIONS[0];}
}

