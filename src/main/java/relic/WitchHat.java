package relic;

import action.ChannelNoteAction;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.RedSkull;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import note.DefendNote;

public class WitchHat extends CustomRelic
{
    public static final String ID = "MGR:WitchHat";
    private static final String IMG = "img/relic/" + ID.substring(4) + ".png";
    private static final String OUTLINE = "img/relic/outline/" + ID.substring(4) + ".png";

    public WitchHat()
    {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(OUTLINE), RelicTier.COMMON, LandingSound.FLAT);
    }

    public String getUpdatedDescription() {return this.DESCRIPTIONS[0];}

    public void atTurnStart()
    {
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, WitchHat.this));
        addToBot(new ChannelNoteAction(new DefendNote()));
        if (AbstractDungeon.player.isBloodied) addToBot(new ChannelNoteAction(new DefendNote()));
    }

    @Override
    public void atBattleStart() {if (AbstractDungeon.player.isBloodied) {flash(); this.pulse = true;}}

    @Override
    public void onBloodied() {flash(); this.pulse = true;}

    @Override
    public void onNotBloodied() {this.pulse = false;}

    @Override
    public void onVictory() {this.pulse = false;}

    @Override
    public AbstractRelic makeCopy()
    {
        return new WitchHat();
    }

}
