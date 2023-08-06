package card.UNCOMMON;

import card.AbstractMGRCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import path.AbstractCardEnum;

public class Swallow extends AbstractMGRCard {
    public static final String ID = "MGR:Swallow";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 1;
    private static final int DMG = 5;
    private static final int PLUS_DMG = 2;
    private static final int MAGIC = 2;
    private static final int PLUS_MAGIC = 1;
    public Swallow() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.MGR_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage=DMG;
        this.baseMagicNumber=MAGIC;
        this.magicNumber=this.baseMagicNumber;
        this.tags.add(CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if(m!=null)
        {
            addToBot(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY - (40.0f * Settings.scale), Color.RED.cpy()), Settings.FAST_MODE?0.1F:0.3F));
            if(m.hasPower(MinionPower.POWER_ID))
            {
                addToBot(new InstantKillAction(m));
                addToBot(new HealAction(p,p,this.magicNumber*3));
            }else
            {
                addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
                addToBot(new HealAction(p,p,this.magicNumber));
            }
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.rawDescription=DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        if(m.hasPower(MinionPower.POWER_ID)) this.rawDescription = EXTENDED_DESCRIPTION;
        else this.rawDescription=DESCRIPTION;
        initializeDescription();
    }

    public AbstractCard makeCopy() { return new Swallow(); }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(PLUS_DMG);
            this.upgradeMagicNumber(PLUS_MAGIC);
        }
    }
}
