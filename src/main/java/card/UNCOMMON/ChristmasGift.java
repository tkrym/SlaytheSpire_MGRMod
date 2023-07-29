package card.UNCOMMON;

import action.ChristmasGiftAction;
import card.AbstractMGRCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import path.AbstractCardEnum;

public class ChristmasGift extends AbstractMGRCard {
    public static final String ID = "MGR:ChristmasGift";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 2;
    private static final int MAGIC = 20;
    private static final int PLUS_MAGIC = 5;
    public static final int PRO_POTION=42;
    public static final int PRO_GOLD=43;
    public static final int PRO_CARD=15;
    public static final int PRO_POTION_UPGRADED=37;
    public static final int PRO_GOLD_UPGRADED=38;
    public static final int PRO_CARD_UPGRADED=25;
    public ChristmasGift() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber=MAGIC;
        this.magicNumber=this.baseMagicNumber;
        this.selfRetain=true;
        this.exhaust=true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChristmasGiftAction(this.magicNumber,this.upgraded));
    }

    public AbstractCard makeCopy() { return new ChristmasGift(); }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(PLUS_MAGIC);
            this.rawDescription=UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
