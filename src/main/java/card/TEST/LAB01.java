package card.TEST;

import card.AbstractMGRCard;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import note.AbstractNote;
import path.AbstractCardEnum;

import java.util.ArrayList;

public class LAB01 extends AbstractMGRCard {
    public static final String ID = "MGR:LAB01";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 1;
    private static final int MAGIC = 1;
    private static final int PLUS_MAGIC = 1;
    private int RetainCounter;
    private ArrayList<AbstractNote> RecordNotes;
    public LAB01() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber=MAGIC;
        this.magicNumber=this.baseMagicNumber;
        this.RetainCounter=0;
        this.exhaust=true;
        this.selfRetain=true;
        this.RecordNotes=new ArrayList<>();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for(AbstractNote note:this.RecordNotes) note.myEvoke();
    }

    @Override
    public void onRetained() {
        this.RetainCounter++;
        this.selfRetain = this.RetainCounter < this.magicNumber;
        //this.addToBot(new TalkAction(true,this.name+"||"+this.RetainCounter+"||"+this.selfRetain,2.0F,2.0F));
    }

    @Override
    public void onMoveToDiscard()
    {
        this.RetainCounter=0;
        this.selfRetain=true;
        this.RecordNotes.clear();
    }

    public void AddNote(AbstractNote note){this.RecordNotes.add(note);}

    public AbstractCard makeCopy()
    {
        LAB01 newCard=new LAB01();
        newCard.RetainCounter=this.RetainCounter;
        newCard.RecordNotes= new ArrayList<>(this.RecordNotes);
        return newCard;
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(PLUS_MAGIC);
        }
    }
}
