package card.UNCOMMON;

import action.ChannelNoteAction;
import card.AbstractMGRCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import note.StarryNote;
import path.AbstractCardEnum;

public class StarryDrift extends AbstractMGRCard
{
    public static final String ID = "MGR:StarryDrift";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/" + ID.substring(4) + ".png";
    private static final int COST = -2;
    private static final int MAGIC = 1;

    public StarryDrift()
    {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}

    @Override
    public void triggerOnManualDiscard()
    {
        addToTop(new ChannelNoteAction(new StarryNote()));
    }

    @Override
    public void triggerWhenDrawn() {if (this.upgraded) addToTop(new ChannelNoteAction(new StarryNote()));}

    @Override
    public void triggerOnExhaust() {addToTop(new ChannelNoteAction(new StarryNote()));}

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {return false;}

    public AbstractCard makeCopy() {return new StarryDrift();}

    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
