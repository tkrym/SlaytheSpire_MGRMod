package relic;

import action.ChannelNoteAction;
import action.TemporaryDuplicationAction;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import hook.OnChordHook;
import note.AbstractNote;
import note.AttackNote;
import note.DefendNote;

import java.util.ArrayList;

public class FriendsSpirit extends CustomRelic
{
    public static final String ID = "MGR:FriendsSpirit";
    private static final String IMG = "img/relic/" + ID.substring(4) + ".png";
    private static final String OUTLINE = "img/relic/outline/" + ID.substring(4) + ".png";

    public FriendsSpirit()
    {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(OUTLINE), RelicTier.RARE, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription() {return this.DESCRIPTIONS[0];}

    @Override
    public void onExhaust(AbstractCard card)
    {
        if (!(card.type.equals(AbstractCard.CardType.CURSE) || card.type.equals(AbstractCard.CardType.STATUS)) && !this.grayscale)
        {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, FriendsSpirit.this));
            addToBot(new TemporaryDuplicationAction(card));
            this.grayscale = true;
            stopPulse();
        }
    }

    @Override
    public void atBattleStart()
    {
        this.grayscale = false;
        beginLongPulse();
    }

    @Override
    public void onVictory()
    {
        this.grayscale = false;
        stopPulse();
    }

    @Override
    public AbstractRelic makeCopy()
    {
        return new FriendsSpirit();
    }


}
