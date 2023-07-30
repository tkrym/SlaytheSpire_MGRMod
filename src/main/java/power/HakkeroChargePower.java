package power;

import action.ApplyForteAction;
import action.ChannelNoteAction;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import note.AttackNote;

public class HakkeroChargePower extends AbstractPower {
    public static final String POWER_ID = "MGR:HakkeroChargePower";
    private static final String IMG = "img/power/"+POWER_ID.substring(4)+".png";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public HakkeroChargePower(int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        updateDescription();
        this.img = new Texture(IMG);
    }

    @Override
    public void atStartOfTurn()
    {
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new HakkeroChargedPower(this.amount),this.amount));
        addToBot(new ApplyForteAction(this.amount));
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new LoseFortePower(this.amount),this.amount));
    }

    @Override
    public void updateDescription() {this.description = DESCRIPTIONS[0]+this.amount+DESCRIPTIONS[1];}
}
