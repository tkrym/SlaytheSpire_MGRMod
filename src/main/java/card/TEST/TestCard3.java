package card.TEST;

import action.ChannelNoteAction;
import card.AbstractMGRCard;
import character.MGR_character;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import note.DrawNote;
import path.AbstractCardEnum;
import power.NextTurnStarryNotePower;
import power.TestCard3Power;

public class TestCard3 extends AbstractMGRCard
{
    public static final String ID = "MGR:TestCard3";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    //public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/" + ID.substring(4) + ".png";
    private static final int COST = 1;
    private static final int MAGIC = 3;
    private static final int PLUS_MAGIC = 1;

    public TestCard3()
    {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.POWER,
                AbstractCardEnum.MGR_COLOR, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new ApplyPowerAction(p,p,new TestCard3Power(this.magicNumber),this.magicNumber));
    }

    public AbstractCard makeCopy() {return new TestCard3();}

    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeMagicNumber(PLUS_MAGIC);
        }
    }
}
