package card.UNCOMMON;

import action.DiscardLeastCostAction;
import card.AbstractMGRCard;
import card.COMMON.Nocturnal;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.ExpertiseAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import path.AbstractCardEnum;
import power.TinyOrchestraPower;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class FieryPerformance extends AbstractMGRCard {
    public static final String ID = "MGR:FieryPerformance";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 1;
    private static final int MAGIC = 7;
    private static final int PLUS_MAGIC = 1;
    public FieryPerformance() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber=MAGIC;
        this.magicNumber=this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ExpertiseAction(p,this.magicNumber));
        //addToBot(new DiscardLeastCostAction(2));
        addToBot(new AbstractGameAction() {
            @Override
            public void update()
            {
                for(AbstractCard c:AbstractDungeon.player.hand.group)
                    if(c.type.equals(CardType.CURSE)||c.type.equals(CardType.STATUS))
                    {
                        addToTop(new ExhaustSpecificCardAction(c,AbstractDungeon.player.hand));
                        addToTop(new WaitAction(0.1F));
                    }
                this.isDone=true;
            }
        });
    }

    public AbstractCard makeCopy() { return new FieryPerformance(); }

    public void upgrade() {
        if(!this.upgraded)
        {
            this.upgradeName();
            this.upgradeMagicNumber(PLUS_MAGIC);
            initializeDescription();
        }
    }
}
