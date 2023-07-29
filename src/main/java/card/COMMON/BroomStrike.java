package card.COMMON;

import action.ChannelNoteAction;
import card.AbstractMGRCard;
import character.MGR_character;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import note.AttackNote;
import note.DefendNote;
import path.AbstractCardEnum;

public class BroomStrike extends AbstractMGRCard {
    public static final String ID = "MGR:BroomStrike";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 1;
    private static final int DMG = 5;
    private static final int PLUS_DMG = 2;
    private static final int BLOCK = 5;
    private static final int PLUS_BLOCK = 2;
    public BroomStrike() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.MGR_COLOR, CardRarity.COMMON, CardTarget.ENEMY);
        this.tags.add(CardTags.STRIKE);
        this.baseDamage = DMG;
        this.baseBlock=BLOCK;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new ChannelNoteAction(new AttackNote()));
        if(MGR_character.GetChordCount()>=1)
        {
            addToBot(new GainBlockAction(p,p,this.block));
            addToBot(new ChannelNoteAction(new DefendNote()));
        }
    }

    public AbstractCard makeCopy() { return new BroomStrike(); }

    @Override
    public void triggerOnGlowCheck()
    {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if(MGR_character.GetChordCount()>=1)
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(PLUS_DMG);
            this.upgradeBlock(PLUS_BLOCK);
        }
    }
}
