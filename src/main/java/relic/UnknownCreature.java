package relic;
import action.ChannelNoteAction;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import hook.OnChordHook;
import note.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UnknownCreature extends CustomRelic{
    public static final String ID = "MGR:UnknownCreature";
    private static final String IMG = "img/relic/"+ID.substring(4)+".png";
    private static final String OUTLINE = "img/relic/outline/"+ID.substring(4)+".png";
    private static final int EatNumber=2;

    public UnknownCreature() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(OUTLINE), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
        {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(true, CardCrawlGame.languagePack.getTutorialString("MGR:exception").TEXT[0],4.0F,4.0F));
            return;
        }
        for(int i=1;i<=EatNumber;i++)
        {
            ArrayList<AbstractRelic> relics = new ArrayList<>(AbstractDungeon.player.relics);
            Collections.shuffle(relics, new Random(AbstractDungeon.miscRng.randomLong()));
            Optional<AbstractRelic> food=relics.stream().filter(r->r.tier!=RelicTier.BOSS).findFirst();
            if(food.isPresent()) AbstractDungeon.player.loseRelic(food.get().relicId);
        }
        /*
        ArrayList<AbstractRelic> relics = new ArrayList<>(AbstractDungeon.player.relics);
        Collections.shuffle(relics, new Random(AbstractDungeon.miscRng.randomLong()));
        Optional<AbstractRelic> rare=relics.stream().filter(r->r.tier==RelicTier.RARE).findFirst(),
                                uncommon=relics.stream().filter(r->r.tier==RelicTier.UNCOMMON).findFirst(),
                                special=relics.stream().filter(r->r.tier==RelicTier.SPECIAL).findFirst(),
                                shop=relics.stream().filter(r->r.tier==RelicTier.SHOP).findFirst(),
                                common=relics.stream().filter(r->r.tier==RelicTier.COMMON).findFirst(),
                                starter=relics.stream().filter(r->r.tier==RelicTier.STARTER).findFirst();
        if(rare.isPresent()) AbstractDungeon.player.loseRelic(rare.get().relicId);
        else if(special.isPresent()) AbstractDungeon.player.loseRelic(special.get().relicId);
        else if(uncommon.isPresent()) AbstractDungeon.player.loseRelic(uncommon.get().relicId);
        else if(shop.isPresent()) AbstractDungeon.player.loseRelic(shop.get().relicId);
        else if(common.isPresent()) AbstractDungeon.player.loseRelic(common.get().relicId);
        else if(starter.isPresent()) AbstractDungeon.player.loseRelic(starter.get().relicId);*/
    }

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }

    public String getUpdatedDescription() { return this.DESCRIPTIONS[0]; }

    @Override
    public AbstractRelic makeCopy() {
        return new UnknownCreature();
    }

    @Override
    public boolean canSpawn()
    {
        return AbstractDungeon.player.relics.stream().filter(r->r.tier!=RelicTier.BOSS).count()>=EatNumber;
    }
}
