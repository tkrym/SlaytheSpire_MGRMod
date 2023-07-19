package power;

import action.ResonanceFormAddCardAction;
import card.RARE.ResonanceForm;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hook.OnManualDiscardHook;

public class ResonanceFormPower extends AbstractPower implements OnManualDiscardHook {
    public static final String POWER_ID = "MGR:ResonanceFormPower";
    private static final String IMG = "img/power/"+POWER_ID.substring(4)+".png";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ResonanceFormPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount=ResonanceForm.MAGIC;
        updateDescription();
        this.img = new Texture(IMG);
    }

    @Override
    public void stackPower(int stackAmount) {}

    @Override
    public void updateDescription() {this.description = DESCRIPTIONS[0]+ ResonanceForm.MAGIC+DESCRIPTIONS[1]+this.amount+DESCRIPTIONS[2];}

    @Override
    public void OnManualDiscard(AbstractCard c) {UpdateAmount(c);}

    @Override
    public void onCardDraw(AbstractCard c) {UpdateAmount(c);}

    @Override
    public void onUseCard(AbstractCard c, UseCardAction action) {UpdateAmount(c);}

    private void UpdateAmount(AbstractCard c)
    {
        this.amount--;
        if(this.amount<=0)
        {
            this.amount=ResonanceForm.MAGIC;
            this.flashWithoutSound();
            addToTop(new ResonanceFormAddCardAction(c));
        }
        updateDescription();
    }
}

