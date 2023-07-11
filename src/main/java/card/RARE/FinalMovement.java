package card.RARE;

import action.ChannelNoteAction;
import basemod.abstracts.CustomCard;
import card.AbstractMGRCard;
import character.MGR_character;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import note.AttackNote;
import path.AbstractCardEnum;

public class FinalMovement extends AbstractMGRCard {
    public static final String ID = "MGR:FinalMovement";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 1;
    private static final int MAGIC = 2;

    public FinalMovement(){this(0);}

    public FinalMovement(int upgrades) {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.MGR_COLOR, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.baseDamage = 0;
        this.baseMagicNumber=MAGIC;
        this.magicNumber=this.baseMagicNumber;
        this.timesUpgraded = upgrades;
    }

    public void myUse(AbstractPlayer p, AbstractMonster m) {
        for(int i=0;i<this.magicNumber;i++)
            AbstractDungeon.actionManager.addToBottom(new ChannelNoteAction(new AttackNote()));
        if(MGR_character.BigBrotherStanceCheck())
            for(int i=0;i<this.magicNumber;i++)
                AbstractDungeon.actionManager.addToBottom(new ChannelNoteAction(new AttackNote()));
    }

    @Override
    public void applyPowers() {
        this.damage = 0;
        initializeDescription();
    }

    public AbstractCard makeCopy() { return new FinalMovement(); }

    public void upgrade() {
        this.upgraded=true;
        this.timesUpgraded++;
        upgradeMagicNumber(1);
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        initializeTitle();
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if(MGR_character.InBigBrotherStance())
            this.glowColor = new Color(1.0F,0.4F,1.0F,1.0F);
    }
}
