package card.UNCOMMON;

import action.EchoPhantomAction;
import card.AbstractMGRCard;
import character.MGR_character;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import path.AbstractCardEnum;

public class EchoPhantom extends AbstractMGRCard
{
    public static final String ID = "MGR:EchoPhantom";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/" + ID.substring(4) + ".png";
    private static final int COST = 1;
    private static final int MAGIC = 1;

    public EchoPhantom()
    {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        //addToBot(new DrawCardAction(this.magicNumber));
        addToBot(new DiscardAction(p,p,this.magicNumber,false));
        boolean B=MGR_character.BigBrotherStanceCheck();
        addToBot(new AbstractGameAction() {
            @Override
            public void update()
            {
                addToBot(new EchoPhantomAction(EchoPhantom.this.magicNumber,B));
                this.isDone=true;
            }
        });
    }

    public AbstractCard makeCopy() {return new EchoPhantom();}

    @Override
    public void triggerOnGlowCheck(){triggerOnGlowCheck_BigBrother();}

    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeBaseCost(0);
            //this.rawDescription=UPGRADE_DESCRIPTION;
            //initializeDescription();
        }
    }
}
