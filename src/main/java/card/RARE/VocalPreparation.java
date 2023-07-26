package card.RARE;

import action.ChannelNoteAction;
import card.AbstractMGRCard;
import card.SPECIAL.Climax;
import character.MGR_character;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import note.DefendNote;
import path.AbstractCardEnum;

public class VocalPreparation extends AbstractMGRCard
{
    public static final String ID = "MGR:VocalPreparation";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/" + ID.substring(4) + ".png";
    private static final int COST = 1;
    private static final int MAGIC = 2;

    public VocalPreparation()
    {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.cardsToPreview = new Climax();
        this.exhaust=true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        for(int i=1;i<=this.magicNumber;i++)
            addToBot(new ChannelNoteAction(new DefendNote()));
        if(MGR_character.BigBrotherStanceCheck())
            addToBot(new MakeTempCardInDrawPileAction(GetMyCard(this.upgraded),1,true,true));
        else
            addToBot(new MakeTempCardInDiscardAction(GetMyCard(this.upgraded),1));
    }

    AbstractCard GetMyCard(boolean upgraded)
    {
        AbstractCard newCard=new Climax();
        if(upgraded) newCard.upgrade();
        return newCard;
    }


    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.selfRetain=true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.cardsToPreview.upgrade();
            initializeDescription();
        }
    }
}
