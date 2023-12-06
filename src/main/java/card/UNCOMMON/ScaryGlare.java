package card.UNCOMMON;

import action.ApplyGazeAction;
import action.GazeLoseHpAction;
import card.AbstractMGRCard;
import character.MGR_character;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import com.megacrit.cardcrawl.vfx.combat.FlickCoinEffect;
import path.AbstractCardEnum;
import power.GazePower;

public class ScaryGlare extends AbstractMGRCard
{
    public static final String ID = "MGR:ScaryGlare";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "img/card/" + ID.substring(4) + ".png";
    private static final int COST = 1;
    private static final int DMG = 5;
    private static final int PLUS_DMG = 2;
    private static final int MAGIC = 3;
    private static final int PLUS_MAGIC = 2;
    private static final int GazeRatio = 2;
    private static final int UPGRADE_GazeRatio = 2;

    public ScaryGlare()
    {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.MGR_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = DMG;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        addToBot(new ApplyGazeAction(m, this.magicNumber));
    }

    @Override
    public void calculateCardDamage(AbstractMonster m)
    {
        super.calculateCardDamage(m);
        this.magicNumber = this.baseMagicNumber;
        if (m.hasPower(GazePower.POWER_ID))
            this.magicNumber += Math.floor(((float) m.getPower(GazePower.POWER_ID).amount) / ((float) (this.upgraded?UPGRADE_GazeRatio:GazeRatio)));
        this.isMagicNumberModified = this.magicNumber != this.baseMagicNumber;
    }

    @Override
    public void applyPowers()
    {
        super.applyPowers();
        this.magicNumber = this.baseMagicNumber;
        this.isMagicNumberModified = false;
    }

    public AbstractCard makeCopy() {return new ScaryGlare();}

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
