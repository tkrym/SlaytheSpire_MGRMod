package card.COMMON;

import action.RoundRobinAction;
import card.AbstractMGRCard;
import character.MGR_character;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import path.AbstractCardEnum;

public class RoundRobin extends AbstractMGRCard
{
    public static final String ID = "MGR:RoundRobin";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/" + ID.substring(4) + ".png";
    private static final int COST = 1;
    private static final int MAGIC = 2;
    private static final int PLUS_MAGIC = 1;
    private static final int BLOCK = 7;
    private static final int PLUS_BLOCK = 1;

    public RoundRobin()
    {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.COMMON, CardTarget.SELF);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseBlock=BLOCK;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new GainBlockAction(p,p,this.block));
        addToBot(new RoundRobinAction(this.magicNumber,MGR_character.BigBrotherStanceCheck()));
    }

    @Override
    public void triggerOnGlowCheck()
    {
        triggerOnGlowCheck_BigBrother();
    }

    public AbstractCard makeCopy() {return new RoundRobin();}

    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeMagicNumber(PLUS_MAGIC);
            this.upgradeBlock(PLUS_BLOCK);
            //this.rawDescription=UPGRADE_DESCRIPTION;
            //initializeDescription();
        }
    }
}
