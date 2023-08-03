package card.COMMON;

import card.AbstractMGRCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import path.AbstractCardEnum;
import power.NextTurnStarryNotePower;

public class StarrySkyObservation extends AbstractMGRCard {
    public static final String ID = "MGR:StarrySkyObservation";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 1;
    public static final int MAGIC = 3;
    public static final int PLUS_MAGIC = 1;
    public StarrySkyObservation() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.COMMON, CardTarget.SELF);
        this.baseMagicNumber = MAGIC;
        this.magicNumber=this.baseMagicNumber;
        this.IsStarryCard=true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ScryAction(this.magicNumber));
        addToBot(new DiscardAction(p,p,p.hand.size(),true));
        addToBot(new DrawCardAction(this.magicNumber));
    }

    public AbstractCard makeCopy() { return new StarrySkyObservation(); }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(PLUS_MAGIC);
        }
    }
}
