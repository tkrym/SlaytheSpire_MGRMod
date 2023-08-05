package power;

import action.ChannelNoteAction;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import note.AttackNote;

public class BurnsRedPower extends AbstractPower
{
    public static final String POWER_ID = "MGR:BurnsRedPower";
    private static final String IMG = "img/power/" + POWER_ID.substring(4) + ".png";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public BurnsRedPower()
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        updateDescription();
        this.img = new Texture(IMG);
    }

    @Override
    public void stackPower(int amt){}

    @Override
    public void updateDescription() {this.description = DESCRIPTIONS[0];}
}
