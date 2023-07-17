package relic;
import action.ChannelNoteAction;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import hook.OnChordHook;
import note.*;
import stance.BigBrotherStance;

import java.util.ArrayList;

public class MiniMicrophone extends CustomRelic{
    public static final String ID = "MGR:MiniMicrophone";
    private static final String IMG = "img/relic/"+ID.substring(4)+".png";
    private static final String OUTLINE = "img/relic/outline/"+ID.substring(4)+".png";

    public MiniMicrophone() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(OUTLINE), RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart()
    {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new ChannelNoteAction(new AttackNote()));
        addToBot(new ChannelNoteAction(new DefendNote()));
        addToBot(new ChannelNoteAction(new DrawNote()));
    }

    public String getUpdatedDescription() { return this.DESCRIPTIONS[0]; }

    @Override
    public AbstractRelic makeCopy() {
        return new MiniMicrophone();
    }

}
