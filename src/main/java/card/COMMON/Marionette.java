package card.COMMON;

import action.ChannelNoteAction;
import card.AbstractMGRCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import note.DebuffNote;
import path.AbstractCardEnum;

public class Marionette extends AbstractMGRCard
{
    public static final String ID = "MGR:Marionette";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "img/card/" + ID.substring(4) + ".png";
    private static final int COST = 0;
    private static final int MAGIC = 3;
    private static final int PLUS_MAGIC = 1;

    public Marionette()
    {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust=true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters)
            if (!mo.isDeadOrEscaped())
            {
                addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new StrengthPower(mo, -this.magicNumber), -this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                if (!mo.hasPower(ArtifactPower.POWER_ID))
                    addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new GainStrengthPower(mo, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
            }
    }

    @Override
    public void triggerOnManualDiscard()
    {
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters)
            if (!m.isDeadOrEscaped())
            {
                addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new StrengthPower(m, -this.magicNumber), -this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                if (!m.hasPower(ArtifactPower.POWER_ID))
                    addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new GainStrengthPower(m, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
            }
        //addToTop(new ChannelNoteAction(new DebuffNote()));
    }

    @Override
    public boolean canUpgrade(){return !this.upgraded;}

    public AbstractCard makeCopy() {return new Marionette();}

    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeMagicNumber(PLUS_MAGIC);
        }
    }
}
