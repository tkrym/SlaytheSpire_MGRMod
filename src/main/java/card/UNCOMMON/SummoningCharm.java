package card.UNCOMMON;

import action.SummoningCharmAction;
import action.SummoningCharmActionNew;
import card.AbstractMGRCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import path.AbstractCardEnum;

public class SummoningCharm extends AbstractMGRCard
{
    public static final String ID = "MGR:SummoningCharm";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/" + ID.substring(4) + ".png";
    private static final int COST = 0;
    private static final int MAGIC = 1;

    public SummoningCharm()
    {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new SummoningCharmActionNew(this.upgraded));
    }

    public AbstractCard makeCopy() {return new SummoningCharm();}

    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.rawDescription=UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
