package card.SPECIAL;

import basemod.abstracts.CustomCard;
import card.AbstractMGRCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import path.AbstractCardEnum;

public class Confused extends AbstractMGRCard {
    public static final String ID = "MGR:Confused";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/Confused.png";
    private static final int COST = 0;
    private static final int MAGIC = 1;
    public Confused() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.STATUS,
                CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF);
        this.baseMagicNumber=MAGIC;
        this.magicNumber=this.baseMagicNumber;
        this.exhaust=true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("MGR:souraze"));
        addToBot(new DrawCardAction(this.magicNumber));
    }

    public AbstractCard makeCopy() { return new Confused(); }

    @Override
    public boolean canUpgrade(){ return !this.upgraded;}

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.selfRetain=true;
            this.rawDescription=UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}

