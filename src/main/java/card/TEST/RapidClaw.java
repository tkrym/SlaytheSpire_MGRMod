package card.TEST;

import action.ChannelNoteAction;
import card.AbstractMGRCard;
import character.MGR_character;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import note.AttackNote;
import path.AbstractCardEnum;

public class RapidClaw extends AbstractMGRCard {
    public static final String ID = "MGR:RapidClaw";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 0;
    private static final int DMG = 5;
    private static final int PLUS_DMG = 1;
    private static final int MAGIC = 2;
    private static final int PLUS_MAGIC = -1;
    public RapidClaw() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.MGR_COLOR, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseMagicNumber=MAGIC;
        this.magicNumber=this.baseMagicNumber;
        this.baseDamage = DMG;
        this.exhaust=true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new ChannelNoteAction(new AttackNote()));
        if(MGR_character.StartingCheck()&&this.baseDamage>=this.baseMagicNumber)
        {
            AbstractCard newCard=this.makeStatEquivalentCopy();
            newCard.baseDamage-=this.baseMagicNumber;
            addToBot(new MakeTempCardInHandAction(newCard,1));
        }
    }

    @Override
    public void triggerOnGlowCheck(){triggerOnGlowCheck_Starting();}

    public AbstractCard makeCopy() { return new RapidClaw(); }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(PLUS_DMG);
            this.upgradeMagicNumber(PLUS_MAGIC);
        }
    }
}
