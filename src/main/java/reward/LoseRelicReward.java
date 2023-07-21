package reward;

import basemod.abstracts.CustomReward;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import path.RewardEnum;

public class LoseRelicReward extends CustomReward {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("LoseRelicReward");
    public static final String[] TEXT = uiStrings.TEXT;

    public LoseRelicReward(AbstractRelic relic) {
        super(ImageMaster.loadImage("images/ui/intent/escape.png"), TEXT[0]+relic.name, RewardEnum.MGR_LOSE_RELIC);
        this.relic=relic;
    }

    public boolean claimReward() {
        AbstractDungeon.player.loseRelic(this.relic.relicId);
        if (AbstractDungeon.topPanel != null) {
            AbstractDungeon.topPanel.adjustRelicHbs();
        }
        return true;
    }
}

