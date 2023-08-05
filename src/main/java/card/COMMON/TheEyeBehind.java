package card.COMMON;

import action.IncDebuffAction;
import card.AbstractMGRCard;
import character.MGR_character;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import path.AbstractCardEnum;

public class TheEyeBehind extends AbstractMGRCard
{
    public static final String ID = "MGR:TheEyeBehind";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/" + ID.substring(4) + ".png";
    private static final int COST = 0;
    private static final int MAGIC = 4;
    private static final int PLUS_MAGIC = 3;

    public TheEyeBehind()
    {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        int amt=this.upgraded?1:1;
        addToBot(new IncDebuffAction(m,amt,this.magicNumber));
        if(MGR_character.BigBrotherStanceCheck()) addToBot(new IncDebuffAction(m,amt,amt));
    }

    public AbstractCard makeCopy() {return new TheEyeBehind();}

    @Override
    public void triggerOnGlowCheck(){triggerOnGlowCheck_BigBrother();}

    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeMagicNumber(PLUS_MAGIC);
            this.rawDescription=UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
