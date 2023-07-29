package card.RARE;

import card.AbstractMGRCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import path.AbstractCardEnum;
import power.StereoPlusPower;
import power.HarmonyFormPower;
import power.StereoPower;

public class Stereophonic extends AbstractMGRCard
{
    public static final String ID = "MGR:Stereophonic";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/" + ID.substring(4) + ".png";
    private static final int COST = 1;
    private static final int MAGIC = 2;

    public Stereophonic()
    {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.POWER,
                AbstractCardEnum.MGR_COLOR, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (!this.upgraded)
        {
            if (p.hasPower(StereoPlusPower.POWER_ID) || p.hasPower(StereoPower.POWER_ID)) return;
            this.addToBot(new ApplyPowerAction(p, p, new StereoPower(p)));
        }
        else
        {
            if (p.hasPower(StereoPlusPower.POWER_ID)) return;
            if (p.hasPower(StereoPower.POWER_ID))
                this.addToBot(new RemoveSpecificPowerAction(p, p, StereoPower.POWER_ID));
            this.addToBot(new ApplyPowerAction(p, p, new StereoPlusPower(p)));
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters)
            {
                if (mo.isDeadOrEscaped()) continue;
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mo, p, new VulnerablePower(mo, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
            }
        }
    }

    public AbstractCard makeCopy() {return new Stereophonic();}

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
