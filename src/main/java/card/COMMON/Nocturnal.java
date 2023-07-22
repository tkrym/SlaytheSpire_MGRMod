package card.COMMON;

import card.AbstractMGRCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import path.AbstractCardEnum;

public class Nocturnal extends AbstractMGRCard {
    public static final String ID = "MGR:Nocturnal";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 1;
    private static final int DMG = 7;
    private static final int PLUS_DMG = 3;
    public Nocturnal() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.MGR_COLOR, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        this.baseDamage=DMG;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MakeTempCardInDrawPileAction(AbstractDungeon.returnRandomCurse().makeCopy(), 1, true, true));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int cnt=0;
                cnt+=AbstractDungeon.player.hand.group.stream().filter(c->c.type.equals(CardType.CURSE)).count();
                cnt+=AbstractDungeon.player.drawPile.group.stream().filter(c->c.type.equals(CardType.CURSE)).count();
                cnt+=AbstractDungeon.player.discardPile.group.stream().filter(c->c.type.equals(CardType.CURSE)).count();
                for(int i=1;i<=cnt;i++)
                    addToTop(new DamageRandomEnemyAction(new DamageInfo(p, Nocturnal.this.damage, Nocturnal.this.damageTypeForTurn), AttackEffect.FIRE));
                this.isDone=true;
            }
        });
    }

    public AbstractCard makeCopy() { return new Nocturnal(); }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(PLUS_DMG);
        }
    }
}
