package card.UNCOMMON;

import card.AbstractMGRCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hook.OnExhaustCardHook;
import hook.OnManualDiscardHook;
import path.AbstractCardEnum;

public class SunDescending extends AbstractMGRCard implements OnExhaustCardHook, OnManualDiscardHook
{
    public static final String ID = "MGR:SunDescending";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/" + ID.substring(4) + ".png";
    private static final int COST = -2;
    private static final int MAGIC = 2;
    private static final int PLUS_MAGIC = 1;
    private static final int DMG = 6;
    private static final int PLUS_DMG = 2;

    public SunDescending()
    {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.baseDamage = DMG;
        this.isMultiDamage = true;
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
    public void OnExhaustCard(AbstractCard c) {this.OnManualDiscard(c);}

    @Override
    public void OnManualDiscard(AbstractCard c)
    {
        if (!c.equals(this))
        {
            this.baseDamage += this.magicNumber;
            this.applyPowers();
            initializeDescription();
        }
    }

    public void triggerOnExhaust() {this.triggerOnManualDiscard();}

    @Override
    public void triggerOnManualDiscard()
    {
        addToTop(new WaitAction(0.1f));
        this.superFlash(Color.GREEN.cpy());
        addToTop(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(this.baseDamage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.baseDamage = GetDMG();
        this.applyPowers();
        initializeDescription();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {return false;}

    private int GetDMG() {return this.upgraded ? (DMG + PLUS_DMG) : DMG;}

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
