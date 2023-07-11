package relic;
import action.ChannelNoteAction;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import hook.OnChordHook;
import note.*;

import java.util.ArrayList;

public class UnknownCreature extends CustomRelic{
    public static final String ID = "MGR:UnknownCreature";
    private static final String IMG = "img/relic/"+ID.substring(4)+".png";
    private static final String OUTLINE = "img/relic/outline/"+ID.substring(4)+".png";
    private boolean PowerCardUsed;

    public UnknownCreature() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(OUTLINE), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }

    @Override
    public void atPreBattle()
    {
        this.PowerCardUsed=false;
    }

    public boolean Check()
    {
        if(this.PowerCardUsed) return false;
        this.PowerCardUsed=true;
        this.flash();
        this.grayscale=true;
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        return true;
    }

    public String getUpdatedDescription() { return this.DESCRIPTIONS[0]; }

}
