package card;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import path.AbstractCardEnum;

public class SpBullet2 extends CustomCard{
    public static final String ID = "MGR:SpBullet2";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION= cardStrings.EXTENDED_DESCRIPTION;
    public static final String IMG = "img/card/SpBullet.png";
    private static final int COST = 1;
    public SpBullet2() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.MGR_COLOR, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage = 0;
        this.baseMagicNumber=40;
        this.magicNumber=this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int DMG=calculateDMG(m);
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, DMG, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
    }

    private int calculateDMG(AbstractMonster m) {
        return (int)Math.round(m.currentHealth*((float)this.magicNumber/100.0));
    }

    @Override
    public void applyPowers() {
        this.baseDamage = 0;
        DMGdisplay(0);
    }

    private void DMGdisplay(int DMG) {
        this.rawDescription=EXTENDED_DESCRIPTION[0]+DMG+EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        int DMG=(int)(m.currentHealth*((float)this.magicNumber/100.0)+0.5);
        DMGdisplay(DMG);
    }
    public AbstractCard makeCopy() { return new SpBullet2(); }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
            this.upgradeMagicNumber(40);
        }
    }
}
