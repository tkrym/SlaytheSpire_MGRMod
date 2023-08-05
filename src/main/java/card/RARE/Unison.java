package card.RARE;

import card.AbstractMGRCard;
import character.MGR_character;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import path.AbstractCardEnum;
import power.UnisonLeft;
import power.UnisonPower;
import power.UnisonRight;

public class Unison extends AbstractMGRCard {
    public static final String ID = "MGR:Unison";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 1;
    private static final int MAGIC = 1;
    public Unison() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.POWER,
                AbstractCardEnum.MGR_COLOR, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber=MAGIC;
        this.magicNumber=this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //if(MGR_character.EndingCheck()) addToBot(new ApplyPowerAction(p,p,new UnisonLeft(p,this.magicNumber),this.magicNumber));
        //if(MGR_character.StartingCheck()) addToBot(new ApplyPowerAction(p,p,new UnisonRight(p,this.magicNumber),this.magicNumber));
        addToBot(new ApplyPowerAction(p,p,new UnisonPower(this.magicNumber),this.baseMagicNumber));
    }

    public AbstractCard makeCopy() { return new Unison(); }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.isInnate=true;
            this.rawDescription=UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
