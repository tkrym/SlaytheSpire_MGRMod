package power;

import action.DiscardLeastCostAction;
import card.SPECIAL.Confused;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class YazyuutokasuPlusPower extends AbstractPower {
    public static final String POWER_ID = "MGR:YazyuutokasuPlusPower";
    private static final String IMG = "img/power/"+POWER_ID.substring(4)+".png";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public YazyuutokasuPlusPower(int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        updateDescription();
        this.img = new Texture(IMG);
        this.priority=10;
    }

    @Override
    public void atStartOfTurnPostDraw()
    {
        flashWithoutSound();
        addToBot(new DiscardAction(AbstractDungeon.player,AbstractDungeon.player,this.amount,false));
        //addToBot(new DiscardAction(this.owner, this.owner, this.amount, false));
        addToBot(new MakeTempCardInHandAction(new Confused(),this.amount));
    }

    @Override
    public void updateDescription() {this.description = DESCRIPTIONS[0]+this.amount+DESCRIPTIONS[1]+this.amount+DESCRIPTIONS[2];}
}
