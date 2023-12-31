package card.COMMON;

import action.ChannelNoteAction;
import card.AbstractMGRCard;
import character.MGR_character;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;
import note.AttackNote;
import path.AbstractCardEnum;

public class RapidClaw extends AbstractMGRCard {
    public static final String ID = "MGR:RapidClaw";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 0;
    private static final int DMG = 2;
    private static final int PLUS_DMG = 2;
    private static final int MAGIC = 1;
    //private static final int PLUS_MAGIC = -1;
    public RapidClaw() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.MGR_COLOR, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseMagicNumber=MAGIC;
        this.magicNumber=this.baseMagicNumber;
        this.baseDamage = DMG;
        this.exhaust=true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            addToBot(new VFXAction(new ClawEffect(m.hb.cX, m.hb.cY, Color.RED, Color.BLACK), 0.05f));
        }
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        addToBot(new ChannelNoteAction(new AttackNote()));
        if(MGR_character.StartingCheck()||MGR_character.EndingCheck()&&this.baseDamage>=this.baseMagicNumber)
        {
            AbstractCard newCard=this.makeStatEquivalentCopy();
            newCard.baseDamage-=this.baseMagicNumber;
            addToBot(new MakeTempCardInHandAction(newCard,1));
        }
    }

    @Override
    public void triggerOnGlowCheck()
    {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if(MGR_character.StartingCheck()||MGR_character.EndingCheck())
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
    }

    public AbstractCard makeCopy() { return new RapidClaw(); }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(PLUS_DMG);
            //this.upgradeMagicNumber(PLUS_MAGIC);
        }
    }
}
