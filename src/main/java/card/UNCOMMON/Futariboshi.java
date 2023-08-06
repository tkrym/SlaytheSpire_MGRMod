package card.UNCOMMON;

import action.ChannelNoteAction;
import action.StardustLaserAction;
import card.AbstractMGRCard;
import character.MGR_character;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import note.StarryNote;
import path.AbstractCardEnum;

public class Futariboshi extends AbstractMGRCard {
    public static final String ID = "MGR:Futariboshi";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 1;
    public static final int MAGIC = 1;
    public static final int PLUS_MAGIC = 1;
    public Futariboshi() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = MAGIC;
        this.magicNumber=this.baseMagicNumber;
        this.IsStarryCard=true;
        //this.exhaust=true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new StardustLaserAction(this.magicNumber));
        /*addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if(AbstractDungeon.player instanceof MGR_character)
                {
                    MGR_character mp=(MGR_character)AbstractDungeon.player;
                    int myCounter=mp.counter;
                    //if(Futariboshi.this.upgraded) myCounter+=Futariboshi.MAGIC;
                    mp.inccounter(-myCounter);
                    addToTop(new GainEnergyAction());
                    for(int i=1;i<=myCounter;i++)
                        addToTop(new ChannelNoteAction(new StarryNote()));
                }
                this.isDone=true;
            }
        });*/
    }

    public AbstractCard makeCopy() { return new Futariboshi(); }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(PLUS_MAGIC);
            /*this.rawDescription=UPGRADE_DESCRIPTION;
            initializeDescription();*/
        }
    }
}
