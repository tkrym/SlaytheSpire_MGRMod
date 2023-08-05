package card.UNCOMMON;

import card.AbstractMGRCard;
import card.SPECIAL.Confused;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import path.AbstractCardEnum;

public class Romp extends AbstractMGRCard {
    public static final String ID = "MGR:Romp";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 1;
    /*private static final int BLOCK = 6;
    private static final int PLUS_BLOCK = 2;*/
    private static final int MAGIC = 2;
    public Romp() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.UNCOMMON, CardTarget.SELF);
        //this.baseBlock = BLOCK;
        this.baseMagicNumber=MAGIC;
        this.magicNumber=this.baseMagicNumber;
        this.cardsToPreview=new Confused();
        this.exhaust=true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //addToBot(new GainBlockAction(p, p, this.block));
        AbstractCard newCard=new Confused();
        if(this.upgraded) newCard.upgrade();
        addToBot(new MakeTempCardInHandAction(newCard,this.magicNumber));
    }

    public AbstractCard makeCopy() { return new Romp(); }

    public void upgrade()
    {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeBlock(PLUS_BLOCK);
            this.selfRetain=true;
            this.rawDescription=UPGRADE_DESCRIPTION;
            this.cardsToPreview.upgrade();
            initializeDescription();
        }
    }
}
