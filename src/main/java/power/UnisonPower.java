package power;

import action.ChannelNoteAction;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import note.AbstractNote;
import note.AttackNote;

public class UnisonPower extends AbstractPower
{
    public static final String POWER_ID = "MGR:UnisonPower";
    private static final String IMG = "img/power/" + POWER_ID.substring(4) + ".png";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public UnisonPower(int amount)
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        updateDescription();
        this.img = new Texture(IMG);
    }

    @Override
    public void onUseCard(AbstractCard c, UseCardAction action)
    {
        for(int i=1;i<=this.amount;i++) AbstractNote.GenerateRandomBasicNoteBottom();
    }

    @Override
    public void updateDescription() {this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];}
}
