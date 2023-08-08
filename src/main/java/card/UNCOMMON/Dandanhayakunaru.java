package card.UNCOMMON;

import action.ChannelNoteAction;
import card.AbstractMGRCard;
import character.MGR_character;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import note.AbstractNote;
import note.StarryNote;
import path.AbstractCardEnum;

public class Dandanhayakunaru extends AbstractMGRCard
{
    public static final String ID = "MGR:Dandanhayakunaru";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/" + ID.substring(4) + ".png";
    private static final int COST = -2;
    private static final int DMG = 3;
    private static final int PLUS_DMG = 1;
    private static final int MAGIC = 4;
    private static final int PLUS_MAGIC = 2;

    public Dandanhayakunaru()
    {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseDamage = DMG;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}

    @Override
    public void applyPowers()
    {
        this.damage = this.baseDamage;
        this.isDamageModified = false;
    }

    @Override
    public void calculateCardDamage(AbstractMonster m)
    {
        this.damage = this.baseDamage;
        this.isDamageModified = false;
    }

    @Override
    public void triggerOnManualDiscard()
    {
        for(int i=1;i<=this.magicNumber;i++)
            AbstractNote.GenerateRandomBasicNoteTop();
    }

    @Override
    public void triggerWhenDrawn()
    {
        for(int i=1;i<=this.baseDamage;i++)
            AbstractNote.GenerateRandomBasicNoteTop();
    }

    @Override
    public void triggerOnExhaust()
    {
        this.triggerOnManualDiscard();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {return false;}

    public AbstractCard makeCopy() {return new Dandanhayakunaru();}

    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeDamage(PLUS_DMG);
            this.upgradeMagicNumber(PLUS_MAGIC);
        }
    }
}
