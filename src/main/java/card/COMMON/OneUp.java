package card.COMMON;

import action.IncCounterAction;
import card.AbstractMGRCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import path.AbstractCardEnum;

public class OneUp extends AbstractMGRCard {
    public static final String ID = "MGR:OneUp";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 1;
    private static final int BLOCK = 7;
    private static final int PLUS_BLOCK = 2;
    private static final int MAGIC = 1;
    private static final int PLUS_MAGIC = 1;
    public OneUp() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.COMMON, CardTarget.SELF);
        this.baseBlock = BLOCK;
        this.baseMagicNumber=MAGIC;
        this.magicNumber=this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,p,this.block));
        addToBot(new IncCounterAction(this.magicNumber));
    }

    public AbstractCard makeCopy() { return new OneUp(); }

    public void upgrade() {
        if (!this.upgraded) {
            ++this.timesUpgraded;
            this.upgraded = true;
            this.name = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeTitle();
            this.upgradeBlock(PLUS_BLOCK);
            this.upgradeMagicNumber(PLUS_MAGIC);
        }
    }
}
