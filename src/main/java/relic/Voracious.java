package relic;

import action.ApplyGazeAction;
import basemod.abstracts.CustomRelic;
import card.COMMON.Marionette;
import card.SPECIAL.Confused;
import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnSmithRelic;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class Voracious extends CustomRelic
{
    public static final String ID = "MGR:Voracious";
    private static final String IMG = "img/relic/" + ID.substring(4) + ".png";
    private static final String OUTLINE = "img/relic/outline/" + ID.substring(4) + ".png";

    public Voracious()
    {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(OUTLINE), RelicTier.BOSS, LandingSound.HEAVY);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if (card.type.equals(AbstractCard.CardType.STATUS) || card.type.equals(AbstractCard.CardType.CURSE))
        {
            flash();
            card.exhaust = true;
            action.exhaustCard = true;
        }
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
        addToBot(new MakeTempCardInHandAction(GetRandomStatusCard()));
        addToBot(new MakeTempCardInHandAction(AbstractDungeon.returnRandomCurse().makeCopy()));
    }

    private AbstractCard GetRandomStatusCard()
    {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        cards.add(new Dazed());
        cards.add(new VoidCard());
        cards.add(new Wound());
        cards.add(new Burn());
        cards.add(new Slimed());
        cards.add(new Confused());
        cards.add(new Marionette());
        return cards.get(AbstractDungeon.cardRandomRng.random(cards.size() - 1));
    }

    public String getUpdatedDescription() {return this.DESCRIPTIONS[0];}

    @Override
    public AbstractRelic makeCopy()
    {
        return new Voracious();
    }

}
