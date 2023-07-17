package card.UNCOMMON;

import card.AbstractMGRCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import path.AbstractCardEnum;

public class WaitingForFood extends AbstractMGRCard {
    public static final String ID = "MGR:WaitingForFood";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 1;
    private static final int BLOCK = 7;
    private static final int PLUS_BLOCK = 3;
    private static final int MAGIC = 6;
    private static final int PLUS_MAGIC = 2;
    private boolean TookDamage;
    public WaitingForFood() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock=BLOCK;
        this.baseMagicNumber=MAGIC;
        this.magicNumber=this.baseMagicNumber;
        TookDamage=false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!this.TookDamage)
            addToBot(new GainBlockAction(p, p, this.block));
        else
            addToBot(new HealAction(p,p,this.magicNumber));
    }

    @Override
    public void tookDamage()
    {
        this.TookDamage=true;
        this.exhaust=true;
    }

    @Override
    public void triggerOnGlowCheck()
    {
        if(this.TookDamage) this.glowColor = AbstractCard.GREEN_BORDER_GLOW_COLOR.cpy();
        else this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
    }


    public AbstractCard makeCopy()
    {
        AbstractCard newCard=new WaitingForFood();
        if(this.TookDamage) newCard.tookDamage();
        return newCard;
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(PLUS_BLOCK);
            this.upgradeMagicNumber(PLUS_MAGIC);
            initializeDescription();
        }
    }
}
