package power;

import action.ChannelNoteAction;
import card.SPECIAL.Confused;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import note.StarryNote;

public class NextTurnStarryNotePower extends AbstractPower {
    public static final String POWER_ID = "MGR:NextTurnStarryNotePower";
    private static final String IMG = "img/power/"+POWER_ID.substring(4)+".png";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public NextTurnStarryNotePower(int amount) {
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
        flashWithoutSound();
        for(int i=1;i<=this.amount;i++)
            addToBot(new ChannelNoteAction(new StarryNote()));
        addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player,AbstractDungeon.player,this.ID));
    }

    @Override
    public void updateDescription() {this.description = DESCRIPTIONS[0]+this.amount+DESCRIPTIONS[1];}
}
