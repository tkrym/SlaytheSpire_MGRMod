package card.RARE;

import card.AbstractMGRCard;
import card.SPECIAL.FrenziedDragonBite;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import path.AbstractCardEnum;

public class DragonClaw extends AbstractMGRCard
{
    public static final String ID = "MGR:DragonClaw";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/" + ID.substring(4) + ".png";
    private static final int COST = 1;
    private static final int DMG = 4;
    private static final int PLUS_DMG = 2;
    private static final int MAGIC = 2;

    public DragonClaw()
    {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.MGR_COLOR, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = DMG;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.cardsToPreview = new FrenziedDragonBite();
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.POISON));
        addToBot(new ApplyPowerAction(m, p, new PoisonPower(m, p, this.damage), this.damage));
        DecMagicNumber();
        if(this.baseMagicNumber<=0&&this.exhaust) addToBot(new MakeTempCardInDiscardAction(GetMyCard(this.upgraded),1));
    }

    @Override
    public void triggerOnManualDiscard()
    {
        DecMagicNumber();
        if(this.baseMagicNumber<=0&&this.exhaust)
        {
            addToTop(new ExhaustSpecificCardAction(this,AbstractDungeon.player.discardPile));
            //AbstractDungeon.player.discardPile.moveToExhaustPile(this);
            addToTop(new MakeTempCardInDiscardAction(GetMyCard(this.upgraded),1));
        }
    }

    AbstractCard GetMyCard(boolean upgraded)
    {
        AbstractCard newCard=new FrenziedDragonBite();
        if(upgraded) newCard.upgrade();
        return newCard;
    }

    public AbstractCard makeCopy()
    {
        AbstractCard newCard=new DragonClaw();
        newCard.magicNumber=this.magicNumber;
        newCard.baseMagicNumber=this.baseMagicNumber;
        //newCard.applyPowers();
        return newCard;
    }

    private void DecMagicNumber()
    {
        if(this.baseMagicNumber>=1) this.baseMagicNumber--;
        if(this.baseMagicNumber<=0) this.exhaust=true;
        this.magicNumber=this.baseMagicNumber;
        if(this.magicNumber<0) this.magicNumber=1;
        this.isMagicNumberModified=false;
        initializeDescription();
    }

    @Override
    public void applyPowers()
    {
        super.applyPowers();
        if(this.baseMagicNumber<=0) this.exhaust=true;
    }


    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeDamage(PLUS_DMG);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.cardsToPreview.upgrade();
            initializeDescription();
        }
    }
}
