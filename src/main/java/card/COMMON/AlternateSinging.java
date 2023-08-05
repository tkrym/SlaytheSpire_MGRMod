package card.COMMON;

import action.AlternateSingingAction;
import card.AbstractMGRCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import path.AbstractCardEnum;

public class AlternateSinging extends AbstractMGRCard {
    public static final String ID = "MGR:AlternateSinging";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 1;
    private static final int BLOCK = 3;
    private static final int PLUS_BLOCK = 2;
    private static final int MAGIC = 1;
    public AlternateSinging() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.COMMON, CardTarget.SELF);
        this.baseBlock = BLOCK;
        this.baseMagicNumber=MAGIC;
        this.magicNumber=this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int fullnum=p.filledOrbCount();
        int emptynum=p.maxOrbs-fullnum;
        if(!this.upgraded) addToBot(new AlternateSingingAction(this.block,fullnum,emptynum));
        else addToBot(new AlternateSingingAction(this.block,emptynum,fullnum));
    }

    public AbstractCard makeCopy() { return new AlternateSinging(); }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(PLUS_BLOCK);
            this.rawDescription=UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
