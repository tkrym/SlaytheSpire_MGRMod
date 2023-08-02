package card.UNCOMMON;

import action.KimitomitahosizoraAction;
import action.TheBurningSunAction;
import card.AbstractMGRCard;
import character.MGR_character;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import path.AbstractCardEnum;

public class TheBurningSun extends AbstractMGRCard
{
    public static final String ID = "MGR:TheBurningSun";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "img/card/" + ID.substring(4) + ".png";
    private static final int COST = 1;
    public static final int MAGIC = 1;
    public static final int PLUS_MAGIC = 1;

    public TheBurningSun()
    {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new DrawCardAction(this.magicNumber));
        addToBot(new TheBurningSunAction(2, MGR_character.BigBrotherStanceCheck()));
    }

    public AbstractCard makeCopy() {return new TheBurningSun();}

    @Override
    public void triggerOnGlowCheck(){triggerOnGlowCheck_BigBrother();}

    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeMagicNumber(PLUS_MAGIC);
        }
    }
}
