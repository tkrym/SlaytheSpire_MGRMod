package card.UNCOMMON;

import action.KimitomitahosizoraAction;
import card.AbstractMGRCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import path.AbstractCardEnum;

public class Kimitomitahosizora extends AbstractMGRCard
{
    public static final String ID = "MGR:Kimitomitahosizora";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "img/card/" + ID.substring(4) + ".png";
    private static final int COST = 1;
    public static final int MAGIC = 4;
    public static final int PLUS_MAGIC = 2;

    public Kimitomitahosizora()
    {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, AbstractCard.CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.IsStarryCard=true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new KimitomitahosizoraAction(this.magicNumber));
    }

    public AbstractCard makeCopy() {return new Kimitomitahosizora();}

    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeMagicNumber(PLUS_MAGIC);
        }
    }
}
