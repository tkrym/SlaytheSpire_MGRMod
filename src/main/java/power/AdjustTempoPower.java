package power;

import action.ChannelNoteAction;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hook.OnChordHook;
import note.AbstractNote;

import java.util.ArrayList;

public class AdjustTempoPower extends AbstractPower implements OnChordHook {
    public static final String POWER_ID = "MGR:AdjustTempo";
    private static final String IMG = "img/power/"+POWER_ID.substring(4)+".png";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public AdjustTempoPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type= PowerType.BUFF;
        updateDescription();
        this.img = new Texture(IMG);
    }



    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public void OnChord(ArrayList<AbstractNote> notes)
    {
        ArrayList<AbstractNote> newNote=new ArrayList<>();
        for(AbstractNote note:notes)
            newNote.add(0,note.makeCopy());
        for(AbstractNote note:newNote)
            addToTop(new ChannelNoteAction(note));
        flashWithoutSound();
        this.amount--;
        if(this.amount<=0)
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }
}
