package card.UNCOMMON;

import action.ApplyForteAction;
import card.AbstractMGRCard;
import character.MGR_character;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import path.AbstractCardEnum;
import power.ClimaxPower;
import power.HakkeroChargePlusPower;
import power.HakkeroChargePower;
import power.LoseFortePower;

public class HakkeroCharge extends AbstractMGRCard
{
    public static final String ID = "MGR:HakkeroCharge";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/" + ID.substring(4) + ".png";
    private static final int COST = 1;
    private static final int MAGIC = 2;

    public HakkeroCharge()
    {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust=true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if(MGR_character.AwakenedStanceCheck())
            addToBot(new ApplyPowerAction(p,p,new HakkeroChargePlusPower(this.magicNumber>>1),this.magicNumber>>1));
        else addToBot(new ApplyPowerAction(p,p,new HakkeroChargePower(this.magicNumber),this.magicNumber));
    }

    @Override
    public void triggerOnGlowCheck(){triggerOnGlowCheck_Awakened();}

    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.selfRetain=true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
