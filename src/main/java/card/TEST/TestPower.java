package card.TEST;

import action.ApplyForteAction;
import basemod.abstracts.CustomCard;
import card.AbstractMGRCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import path.AbstractCardEnum;

public class TestPower extends AbstractMGRCard {
    public static final String ID = "MGR:TestPower";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 1;
    public TestPower() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.POWER,
                AbstractCardEnum.MGR_COLOR, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber=this.baseMagicNumber;
    }

    public void myUse(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyForteAction(this.baseMagicNumber));
    }

    public AbstractCard makeCopy() { return new TestPower(); }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }
}
