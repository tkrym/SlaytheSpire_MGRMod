package card.COMMON;

import basemod.abstracts.CustomCard;
import card.AbstractMGRCard;
import character.MGR_character;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import path.AbstractCardEnum;

public class CrispEnding extends AbstractMGRCard {
    public static final String ID = "MGR:CrispEnding";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 0;
    private static final int DMG = 2;
    private static final int PLUS_DMG = 2;
    public boolean myPurgeOnUse=false;
    public CrispEnding() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.MGR_COLOR, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage = DMG;
        this.baseMagicNumber=1;
        this.magicNumber=1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        //addToBot(new DrawCardAction(p, 1));
        if(MGR_character.EndingCheck()&&!this.myPurgeOnUse)
        {
            /*CrispEnding tmp = (CrispEnding) this.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = this.current_x;
            tmp.current_y = this.current_y;
            tmp.target_x = (((float) Settings.WIDTH) / 2.0f) - (300.0f * Settings.scale);
            tmp.target_y = ((float) Settings.HEIGHT) / 2.0f;
            tmp.calculateCardDamage(m);
            tmp.purgeOnUse = true;
            tmp.myPurgeOnUse=true;
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, this.energyOnUse, true, true), true);*/
            addToBot(new DrawCardAction(this.magicNumber));
        }
    }

    @Override
    public void triggerOnGlowCheck() {triggerOnGlowCheck_Ending();}

    public AbstractCard makeCopy() { return new CrispEnding(); }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(PLUS_DMG);
        }
    }
}
