package card.COMMON;

import action.ChannelNoteAction;
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
import note.AttackNote;
import path.AbstractCardEnum;

public class CrispEnding extends AbstractMGRCard {
    public static final String ID = "MGR:CrispEnding";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 0;
    private static final int DMG = 5;
    private static final int PLUS_DMG = 1;
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
        if(this.upgraded) addToBot(new ChannelNoteAction(new AttackNote()));
        if(MGR_character.EndingCheck()) addToBot(new DrawCardAction(this.magicNumber));
    }

    @Override
    public void triggerOnGlowCheck() {triggerOnGlowCheck_Ending();}

    public AbstractCard makeCopy() { return new CrispEnding(); }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(PLUS_DMG);
            this.rawDescription=UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
