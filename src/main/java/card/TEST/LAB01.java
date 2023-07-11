package card.TEST;

import card.AbstractMGRCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import path.AbstractCardEnum;

public class LAB01 extends AbstractMGRCard {
    public static final String ID = "MGR:LAB01";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 1;
    public LAB01() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.RARE, CardTarget.SELF);
        this.baseBlock = 999;
    }

    public void myUse(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    }

    @Override
    protected void applyPowersToBlock(){
        this.block=this.baseBlock;
    }

    public AbstractCard makeCopy() { return new LAB01(); }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }
}
