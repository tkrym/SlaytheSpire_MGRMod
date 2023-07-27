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
import power.SongOfSubmersionPower;

public class TestCard2 extends AbstractMGRCard
{
    public static final String ID = "MGR:TestCard2";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/" + ID.substring(4) + ".png";
    private static final int COST = 1;
    private static final int MAGIC = 1;
    //private static final int PLUS_MAGIC = 1;
    private static final int BLOCK = 7;
    private static final int PLUS_BLOCK = 2;

    public TestCard2()
    {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.COMMON, CardTarget.SELF);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseBlock = BLOCK;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new GainBlockAction(p, p, this.block));
        if (!this.upgraded)
        {
            if (MGR_character.EndingCheck()) addToBot(new DrawCardAction(this.magicNumber));
            if (MGR_character.StartingCheck())
                addToBot(new ApplyPowerAction(p, p, new EnergizedBluePower(p, this.magicNumber), this.magicNumber));
        }
        else
        {
            if (MGR_character.EndingCheck())
            {
                for (int i = 1; i <= this.magicNumber; i++)
                    addToBot(new ChannelNoteAction(new DrawNote()));
            }
            if (MGR_character.StartingCheck())
                addToBot(new ApplyPowerAction(p, p, new NextTurnStarryNotePower(this.magicNumber)));
        }
    }

    public AbstractCard makeCopy() {return new TestCard2();}

    @Override
    public void triggerOnGlowCheck()
    {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (MGR_character.EndingCheck() || MGR_character.StartingCheck())
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
    }

    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeBlock(PLUS_BLOCK);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
