package card.COMMON;

import card.AbstractMGRCard;
import character.MGR_character;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import path.AbstractCardEnum;

public class AccurateShooting extends AbstractMGRCard {
    public static final String ID = "MGR:AccurateShooting";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 2;
    private static final int DMG = 12;
    private static final int PLUS_DMG = 4;
    public AccurateShooting() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.MGR_COLOR, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage = DMG;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p.hand.size()>=2)
        {
            addToBot(new ExhaustAction(2,false));
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage*2, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
        else addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public void triggerOnGlowCheck()
    {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if(AbstractDungeon.player.hand.size()>=3)
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
    }


    public AbstractCard makeCopy() { return new AccurateShooting(); }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(PLUS_DMG);
        }
    }
}
