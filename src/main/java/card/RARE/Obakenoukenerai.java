package card.RARE;

import card.AbstractMGRCard;
import character.MGR_character;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import path.AbstractCardEnum;

public class Obakenoukenerai extends AbstractMGRCard {
    public static final String ID = "MGR:Obakenoukenerai";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    public static final String UPGRADED_IMG = "img/card/"+ID.substring(4)+"Upgraded"+".png";
    private static final int COST = 2;
    private static final int MAGIC = 1;
    public Obakenoukenerai() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber=MAGIC;
        this.magicNumber=this.baseMagicNumber;
        this.isEthereal=true;
        this.exhaust=true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, this.magicNumber), this.magicNumber));
        if(this.upgraded&& MGR_character.BigBrotherStanceCheck())
            addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, this.magicNumber), this.magicNumber));
    }

    public AbstractCard makeCopy() { return new Obakenoukenerai(); }

    @Override
    public void triggerOnGlowCheck() {if(this.upgraded) triggerOnGlowCheck_BigBrother();}

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription=UPGRADE_DESCRIPTION;
            this.isEthereal=false;
            this.textureImg=UPGRADED_IMG;
            initializeDescription();
        }
    }
}
