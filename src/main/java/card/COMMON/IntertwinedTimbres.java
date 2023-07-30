package card.COMMON;

import card.AbstractMGRCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import note.AbstractNote;
import path.AbstractCardEnum;

public class IntertwinedTimbres extends AbstractMGRCard {
    public static final String ID = "MGR:IntertwinedTimbres";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 1;
    private static final int MAGIC = 2;
    private static final int PLUS_MAGIC = 1;
    private static final int BLOCK = 3;
    private static final int PLUS_BLOCK = 1;
    public IntertwinedTimbres() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.COMMON, CardTarget.SELF);
        this.baseMagicNumber=MAGIC;
        this.magicNumber=this.baseMagicNumber;
        this.baseBlock=BLOCK;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        for(int i=1;i<=this.magicNumber;i++) AbstractNote.GenerateRandomBasicNoteBottom();
        addToBot(new AbstractGameAction() {
            @Override
            public void update()
            {
                for(int i=1;i<=AbstractNote.GetNoteTypeCountThisTurn();i++)
                    addToBot(new GainBlockAction(p,p,IntertwinedTimbres.this.block));
                this.isDone=true;
            }
        });
    }

    public AbstractCard makeCopy() { return new IntertwinedTimbres(); }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(PLUS_MAGIC);
            this.upgradeBlock(PLUS_BLOCK);
        }
    }
}
