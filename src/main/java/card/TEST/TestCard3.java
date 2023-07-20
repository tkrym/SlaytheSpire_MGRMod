package card.TEST;

import action.ChannelNoteAction;
import card.AbstractMGRCard;
import character.MGR_character;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import note.AbstractNote;
import note.AttackNote;
import path.AbstractCardEnum;

import java.util.UUID;

public class TestCard3 extends AbstractMGRCard {
    public static final String ID = "MGR:TestCard3";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 1;
    private static final int MAGIC = 3;
    private static final int PLUS_MAGIC = 1;
    private static final int EXNOTE = 1;
    private static final int UPGRADED_EXNOTE = 2;
    public TestCard3() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.COMMON, CardTarget.SELF);
        this.baseMagicNumber=MAGIC;
        this.magicNumber=this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int total=this.magicNumber;
        if(MGR_character.StartingCheck())
            total+=this.upgraded?UPGRADED_EXNOTE:EXNOTE;
        for(int i=1;i<=total;i++)
            AbstractNote.GenerateRandomBasicNoteBottom();
    }

    @Override
    public void triggerOnGlowCheck(){ triggerOnGlowCheck_Starting();}

    public AbstractCard makeCopy() { return new TestCard3(); }

    public void upgrade() {
        if(!this.upgraded)
        {
            this.upgradeName();
            this.upgradeMagicNumber(PLUS_MAGIC);
            this.rawDescription=UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
