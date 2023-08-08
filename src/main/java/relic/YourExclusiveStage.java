package relic;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import note.AbstractNote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;

public class YourExclusiveStage extends CustomRelic{
    public static final String ID = "MGR:YourExclusiveStage";
    private static final String IMG = "img/relic/"+ID.substring(4)+".png";
    private static final String OUTLINE = "img/relic/outline/"+ID.substring(4)+".png";

    public YourExclusiveStage() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(OUTLINE), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    public void obtain() {
        if (AbstractDungeon.player.hasRelic(MiniMicrophone.ID)) {
            for(int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if ((AbstractDungeon.player.relics.get(i)).relicId.equals(MiniMicrophone.ID)) {
                    this.instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    @Override
    public void atTurnStart()
    {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractNote.GenerateRandomBasicNoteBottom();
        AbstractNote.GenerateRandomBasicNoteBottom();
        AbstractNote.GenerateRandomBasicNoteBottom();
    }

    public String getUpdatedDescription() { return this.DESCRIPTIONS[0]; }

    @Override
    public AbstractRelic makeCopy() {
        return new YourExclusiveStage();
    }

    @Override
    public boolean canSpawn() {return AbstractDungeon.player.hasRelic(MiniMicrophone.ID);}
}
