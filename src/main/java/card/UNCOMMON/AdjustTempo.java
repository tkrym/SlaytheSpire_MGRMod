package card.UNCOMMON;

import action.ChannelNoteAction;
import card.AbstractMGRCard;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import note.DrawNote;
import path.AbstractCardEnum;
import power.AdjustTempoPower;

public class AdjustTempo extends AbstractMGRCard {
    public static final String ID = "MGR:AdjustTempo";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 1;
    private static final int MAGIC = 1;
    public AdjustTempo() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber=MAGIC;
        this.magicNumber=this.baseMagicNumber;
        ExhaustiveField.ExhaustiveFields.baseExhaustive.set(this, 2);
        ExhaustiveField.ExhaustiveFields.exhaustive.set(this, 2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new AdjustTempoPower(p,1),1));
        for(int i=0;i<this.magicNumber;i++)
            addToBot(new ChannelNoteAction(new DrawNote()));
        if(!this.upgraded) UpdateExhaustiveDescription();
    }

    public AbstractCard makeCopy() { return new AdjustTempo(); }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeMagicNumber(MAGIC);
            this.rawDescription=UPGRADE_DESCRIPTION;
            ExhaustiveField.ExhaustiveFields.baseExhaustive.set(this, -1);
            ExhaustiveField.ExhaustiveFields.exhaustive.set(this, -1);
            initializeDescription();
        }
    }
}
