package power;

import card.UNCOMMON.TheForsaken;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hook.OnManualDiscardHook;

public class TheForsakenPower extends AbstractPower implements OnManualDiscardHook {
    public static final String POWER_ID = "MGR:TheForsakenPower";
    private static final String IMG = "img/power/"+POWER_ID.substring(4)+".png";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public int myAmount;

    public TheForsakenPower(int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.myAmount=amount;
        this.amount = TheForsaken.MAGIC;
        updateDescription();
        this.img = new Texture(IMG);
    }

    @Override
    public void stackPower(int stackAmount) { this.myAmount+=stackAmount;}

    @Override
    public void OnManualDiscard(AbstractCard c) {UpdateAmount();}

    @Override
    public void onExhaust(AbstractCard c) { UpdateAmount();}

    private void UpdateAmount()
    {
        this.amount--;
        if(this.amount<=0)
        {
            this.amount=TheForsaken.MAGIC;
            this.flashWithoutSound();
            addToTop(new GainEnergyAction(this.myAmount));
        }
    }

    @Override
    public void updateDescription()
    {
        this.description=DESCRIPTIONS[0]+TheForsaken.MAGIC+DESCRIPTIONS[1];
        for(int i=1;i<=this.myAmount;i++)
            this.description=this.description+" [E] ";
        this.description=this.description+DESCRIPTIONS[2]+this.amount+DESCRIPTIONS[3];
    }

}
