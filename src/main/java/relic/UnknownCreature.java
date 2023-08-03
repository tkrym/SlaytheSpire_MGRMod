package relic;
import action.ChannelNoteAction;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import note.*;
import reward.LoseRelicReward;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UnknownCreature extends CustomRelic{
    public static final String ID = "MGR:UnknownCreature";
    private static final String IMG = "img/relic/"+ID.substring(4)+".png";
    private static final String OUTLINE = "img/relic/outline/"+ID.substring(4)+".png";
    private static final int EatNumber=2;
    private boolean HaveAte=true;
    private boolean ShowReward=false;

    public UnknownCreature() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(OUTLINE), RelicTier.BOSS, LandingSound.FLAT);
    }

    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
        this.ShowReward=false;
        this.HaveAte=false;
    }

    @Override
    public void update() {
        super.update();
        if (!this.HaveAte && !AbstractDungeon.isScreenUp&&!this.ShowReward) {
            this.ShowReward=true;
            AbstractDungeon.combatRewardScreen.open();
            AbstractDungeon.combatRewardScreen.rewards.clear();
            ArrayList<AbstractRelic> relics = new ArrayList<>(AbstractDungeon.player.relics);
            Collections.shuffle(relics, new Random(AbstractDungeon.miscRng.randomLong()));
            List<AbstractRelic> food=relics.stream().filter(r->r.tier!=RelicTier.BOSS).collect(Collectors.toList());
            for(int i=1;i<=EatNumber;i++) if(food.size()>=i)
                AbstractDungeon.combatRewardScreen.rewards.add(new LoseRelicReward(food.get(i-1)));
            AbstractDungeon.combatRewardScreen.positionRewards();
            AbstractDungeon.dynamicBanner.appear(this.DESCRIPTIONS[1]);
            AbstractDungeon.overlayMenu.proceedButton.hide();
        }
        else if(AbstractDungeon.combatRewardScreen.rewards.isEmpty()&&this.ShowReward)
        {
            this.HaveAte=true;
            this.ShowReward=false;
            AbstractDungeon.overlayMenu.proceedButton.show();
            AbstractDungeon.overlayMenu.proceedButton.setLabel(this.DESCRIPTIONS[2]);
        }
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
