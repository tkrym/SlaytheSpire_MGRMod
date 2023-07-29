package card.UNCOMMON;

import card.AbstractMGRCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import path.AbstractCardEnum;

public class SunDescending extends AbstractMGRCard
{
    public static final String ID = "MGR:SunDescending";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/" + ID.substring(4) + ".png";
    private static final int COST = -2;
    private static final int MAGIC = 7;
    private static final int PLUS_MAGIC = -1;
    private static final int DMG = 21;
    private static final int PLUS_DMG = 5;

    public SunDescending()
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
        super.applyPowers();
        this.damage = this.baseDamage;
        this.isDamageModified = false;
    }

    @Override
    public void calculateCardDamage(AbstractMonster m)
    {
        super.calculateCardDamage(m);
        this.damage = this.baseDamage;
        this.isDamageModified = false;
    }

    @Override
    public void didDiscard() { DecCounter();}

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) { DecCounter();}

    @Override
    public void triggerOnManualDiscard()
    {
        DecCounter();
        DecCounter();
        DecCounter();
    }

    private void DecCounter()
    {
        this.magicNumber--;
        if (this.magicNumber <= 0)
        {
            this.magicNumber = this.baseMagicNumber;
            this.superFlash(Color.GREEN.cpy());
            addToTop(new WaitAction(0.1f));
            addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, this.baseDamage, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
        }
        if(this.magicNumber!=this.baseMagicNumber) this.isMagicNumberModified=true;
        initializeDescription();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {return false;}

    public AbstractCard makeCopy() {return new SunDescending();}

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
