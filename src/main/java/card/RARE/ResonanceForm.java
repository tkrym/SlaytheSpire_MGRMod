package card.RARE;

import basemod.helpers.BaseModCardTags;
import card.AbstractMGRCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import path.AbstractCardEnum;
import power.ResonanceFormPower;

public class ResonanceForm extends AbstractMGRCard
{
    public static final String ID = "MGR:ResonanceForm";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/" + ID.substring(4) + ".png";
    private static final int COST = 3;
    public static int MAGIC = 14;

    public ResonanceForm()
    {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.POWER,
                AbstractCardEnum.MGR_COLOR, CardRarity.RARE, CardTarget.SELF);
        this.isEthereal = true;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(BaseModCardTags.FORM);
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (!p.hasPower(ResonanceFormPower.POWER_ID))
            this.addToBot(new ApplyPowerAction(p, p, new ResonanceFormPower(p), 1));
    }

    public AbstractCard makeCopy() {return new ResonanceForm();}

    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.isEthereal = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
