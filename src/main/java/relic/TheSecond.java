package relic;
import action.ChannelNoteAction;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import hook.OnChordHook;
import note.*;
import stance.BigBrotherStance;

import java.util.ArrayList;

public class TheSecond extends CustomRelic{
    public static final String ID = "MGR:TheSecond";
    private static final String IMG = "img/relic/"+ID.substring(4)+".png";
    private static final String OUTLINE = "img/relic/outline/"+ID.substring(4)+".png";

    public TheSecond() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(OUTLINE), RelicTier.RARE, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart()
    {
        flash();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(new BigBrotherStance()));
        this.grayscale = true;
    }

    public String getUpdatedDescription() { return this.DESCRIPTIONS[0]; }

}
