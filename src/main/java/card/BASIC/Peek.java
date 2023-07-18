package card.BASIC;

import action.ApplyGazeAction;
import card.AbstractMGRCard;
import character.MGR_character;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import path.AbstractCardEnum;

public class Peek extends AbstractMGRCard {
    public static final String ID = "MGR:Peek";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 1;
    private static final int MAGIC = 5;
    private static final int PLUS_MAGIC = 3;
    public Peek() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.BASIC, CardTarget.ENEMY);
        this.baseMagicNumber=MAGIC;
        this.magicNumber=this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyGazeAction(m,this.magicNumber));
        addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, 1, false), 1));
        if(MGR_character.BigBrotherStanceCheck())
        {
            addToBot(new ApplyGazeAction(m,this.magicNumber));
            addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, 1, false), 1));
        }
    }

    public AbstractCard makeCopy() { return new Peek(); }

    @Override
    public void triggerOnGlowCheck() {triggerOnGlowCheck_BigBrother();}

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(PLUS_MAGIC);
        }
    }
}
