package card.RARE;

import card.AbstractMGRCard;
import character.MGR_character;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.StarBounceEffect;
import com.megacrit.cardcrawl.vfx.combat.ViolentAttackEffect;
import path.AbstractCardEnum;

public class MaguroAssault extends AbstractMGRCard {
    public static final String ID = "MGR:MaguroAssault";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 1;
    private static final int DMG = 7;
    private static final int PLUS_DMG = 1;
    private static final int MAGIC = 2;
    private static final int PLUS_MAGIC = 1;
    private static final Color myColorRed=new Color(1.0F,0.25F,0.0F,1.0F);
    public MaguroAssault() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.MGR_COLOR, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = DMG;
        this.baseMagicNumber=MAGIC;
        this.magicNumber=this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        int finaldamage=this.damage;
        boolean NoteCheck=MGR_character.EndingCheck()||MGR_character.StartingCheck();
        boolean BigBrotherCheck=MGR_character.InBigBrotherStance();
        if(BigBrotherCheck&&NoteCheck)
        {
            addToBot(new VFXAction(new ViolentAttackEffect(m.hb.cX, m.hb.cY, myColorRed)));
            for(int i=0;i<10;i++) addToBot(new VFXAction(new StarBounceEffect(m.hb.cX, m.hb.cY)));
            addToBot(new DamageAction(m, new DamageInfo(p, finaldamage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }else if(BigBrotherCheck) addToBot(new DamageAction(m, new DamageInfo(p, finaldamage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        else if(NoteCheck) addToBot(new DamageAction(m, new DamageInfo(p, finaldamage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        else addToBot(new DamageAction(m, new DamageInfo(p, finaldamage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        MGR_character.BigBrotherStanceCheck();
    }

    public AbstractCard makeCopy() { return new MaguroAssault(); }

    public void calculateCardDamage(AbstractMonster m) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber*MGR_character.GetChordCount();
        super.calculateCardDamage(m);
        boolean NoteCheck=MGR_character.EndingCheck()||MGR_character.StartingCheck();
        boolean BigBrotherCheck=MGR_character.InBigBrotherStance();
        if(NoteCheck) this.damage*=2;
        if(BigBrotherCheck) this.damage*=2;
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber*MGR_character.GetChordCount();
        super.applyPowers();
        boolean NoteCheck=MGR_character.EndingCheck()||MGR_character.StartingCheck();
        boolean BigBrotherCheck=MGR_character.InBigBrotherStance();
        if(NoteCheck) this.damage*=2;
        if(BigBrotherCheck) this.damage*=2;
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public void triggerOnGlowCheck()
    {
        boolean NoteCheck=MGR_character.EndingCheck()||MGR_character.StartingCheck();
        boolean BigBrotherCheck=MGR_character.InBigBrotherStance();
        if(NoteCheck&&BigBrotherCheck) this.glowColor=myColorRed.cpy();
        else if(NoteCheck) this.glowColor=AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        else if(BigBrotherCheck) this.glowColor=new Color(1.0F,0.4F,1.0F,1.0F);
        else this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(PLUS_DMG);
            this.upgradeMagicNumber(PLUS_MAGIC);
        }
    }
}
