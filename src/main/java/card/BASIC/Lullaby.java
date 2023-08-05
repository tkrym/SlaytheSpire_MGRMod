package card.BASIC;

import card.AbstractMGRCard;
import character.MGR_character;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import path.AbstractCardEnum;

public class Lullaby extends AbstractMGRCard {
    public static final String ID = "MGR:Lullaby";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 2;
    private static final int BLOCK = 9;
    private static final int PLUS_BLOCK = 2;
    private static final int MAGIC = 3;
    private boolean AffectSelf=true;
    public Lullaby() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.BASIC, CardTarget.ALL);
        this.baseBlock = BLOCK;
        this.baseMagicNumber=MAGIC;
        this.magicNumber=this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        if(MGR_character.EndingCheck())
        {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters)
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new WeakPower(mo, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
            if(AffectSelf) AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WeakPower(p, this.magicNumber, false), this.magicNumber));
        }
    }

    @Override
    public void triggerOnGlowCheck() {triggerOnGlowCheck_Ending();}

    public AbstractCard makeCopy() { return new Lullaby(); }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(PLUS_BLOCK);
            this.rawDescription=UPGRADE_DESCRIPTION;
            this.AffectSelf=false;
            this.target=CardTarget.ALL_ENEMY;
            this.initializeDescription();
        }
    }
}
