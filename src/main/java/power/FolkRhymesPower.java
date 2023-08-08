package power;

import action.ChannelNoteAction;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hook.OnChordHook;
import note.AbstractNote;
import note.DefendNote;

import java.util.ArrayList;

public class FolkRhymesPower extends AbstractPower implements OnChordHook
{
    public static final String POWER_ID = "MGR:FolkRhymesPower";
    private static final String IMG = "img/power/"+POWER_ID.substring(4)+".png";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public FolkRhymesPower(int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount=amount;
        updateDescription();
        this.img = new Texture(IMG);
    }

    @Override
    public void updateDescription() {this.description = DESCRIPTIONS[0]+this.amount+DESCRIPTIONS[1];}

    @Override
    public void OnChord(ArrayList<AbstractNote> notes)
    {
        flashWithoutSound();
        addToTop(new GainBlockAction(AbstractDungeon.player,this.amount));
    }
}

