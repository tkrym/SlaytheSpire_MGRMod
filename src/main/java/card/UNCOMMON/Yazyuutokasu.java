package card.UNCOMMON;

import card.AbstractMGRCard;
import card.SPECIAL.Confused;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import path.AbstractCardEnum;
import power.YazyuutokasuPlusPower;
import power.YazyuutokasuPower;

public class Yazyuutokasu extends AbstractMGRCard {
    public static final String ID = "MGR:Yazyuutokasu";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 1;
    private static final int MAGIC = 1;
    public Yazyuutokasu() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.POWER,
                AbstractCardEnum.MGR_COLOR, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber=MAGIC;
        this.magicNumber=this.baseMagicNumber;
        this.cardsToPreview=new Confused();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.upgraded)
            addToBot(new ApplyPowerAction(p,p,new YazyuutokasuPlusPower(this.magicNumber)));
        else
            addToBot(new ApplyPowerAction(p,p,new YazyuutokasuPower(this.magicNumber)));
    }

    public AbstractCard makeCopy() { return new Yazyuutokasu(); }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription=UPGRADE_DESCRIPTION;
            this.cardsToPreview.upgrade();
            initializeDescription();
        }
    }
}
