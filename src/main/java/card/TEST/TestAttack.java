package card.TEST;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import path.AbstractCardEnum;

public class TestAttack extends CustomCard{
    public static final String ID = "MGR:TestAttack";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 1;
    public TestAttack() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.MGR_COLOR, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new InstantKillAction(m));
    }

    @Override
    public void applyPowers() {
        this.baseDamage = 0;
        this.rawDescription=DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        this.rawDescription = EXTENDED_DESCRIPTION;
        initializeDescription();
    }

    public AbstractCard makeCopy() { return new TestAttack(); }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }
}
