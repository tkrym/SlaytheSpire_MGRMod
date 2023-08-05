package card.UNCOMMON;

import action.ChannelNoteAction;
import card.AbstractMGRCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import note.AttackNote;
import path.AbstractCardEnum;
import power.BurnsRedPower;
import power.TheForsakenPower;

public class BurnsRed extends AbstractMGRCard {
    public static final String ID = "MGR:BurnsRed";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 1;
    public static final int MAGIC = 2;
    public BurnsRed() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.POWER,
                AbstractCardEnum.MGR_COLOR, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber=MAGIC;
        this.magicNumber=this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new BurnsRedPower()));
        if(this.upgraded)
            for(int i=1;i<=this.magicNumber;i++)
                addToBot(new ChannelNoteAction(new AttackNote()));
    }

    public AbstractCard makeCopy() { return new BurnsRed(); }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription=UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
