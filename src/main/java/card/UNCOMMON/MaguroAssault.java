package card.UNCOMMON;

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
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;
import com.megacrit.cardcrawl.vfx.combat.ViolentAttackEffect;
import path.AbstractCardEnum;

public class MaguroAssault extends AbstractMGRCard {
    public static final String ID = "MGR:MaguroAssault";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 1;
    private static final int DMG = 6;
    private static final int PLUS_DMG = 2;
    private static final int MAGIC = 1;
    private static final int PLUS_MAGIC = 1;
    private int OriginDamage;
    private static final Color myColorRed=new Color(1.0F,0.25F,0.0F,1.0F);
    public MaguroAssault() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.MGR_COLOR, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = DMG;
        this.baseMagicNumber=MAGIC;
        this.magicNumber=this.baseMagicNumber;
        this.OriginDamage=this.baseDamage;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        int finaldamage=this.damage;
        boolean NoteCheck=MGR_character.EndingCheck()||MGR_character.StartingCheck();
        boolean BigBrotherCheck=MGR_character.BigBrotherStanceCheck();
        if(NoteCheck) finaldamage*=2;
        if(BigBrotherCheck) finaldamage*=2;
        if(BigBrotherCheck&&NoteCheck)
        {
            addToBot(new VFXAction(new ViolentAttackEffect(m.hb.cX, m.hb.cY, myColorRed)));
            for(int i=0;i<10;i++) addToBot(new VFXAction(new StarBounceEffect(m.hb.cX, m.hb.cY)));
            addToBot(new DamageAction(m, new DamageInfo(p, finaldamage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }else if(BigBrotherCheck) addToBot(new DamageAction(m, new DamageInfo(p, finaldamage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        else if(NoteCheck) addToBot(new DamageAction(m, new DamageInfo(p, finaldamage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        else addToBot(new DamageAction(m, new DamageInfo(p, finaldamage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    public AbstractCard makeCopy() { return new MaguroAssault(); }

    @Override
    public void applyPowers()
    {
        this.baseDamage=this.OriginDamage+this.magicNumber* MGR_character.GetChordCount();
        super.applyPowers();
        boolean NoteCheck=MGR_character.EndingCheck()||MGR_character.StartingCheck();
        boolean BigBrotherCheck=MGR_character.InBigBrotherStance();
        if(NoteCheck) this.damage*=2;
        if(BigBrotherCheck) this.damage*=2;
    }

    @Override
    public void onMoveToDiscard()
    {
        this.baseDamage=this.OriginDamage;
        this.initializeDescription();
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
            this.OriginDamage=this.baseDamage;
        }
    }
}
