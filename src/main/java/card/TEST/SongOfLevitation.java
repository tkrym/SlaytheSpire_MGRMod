package card.TEST;

import action.SongOfLevitationAction;
import card.AbstractMGRCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import path.AbstractCardEnum;

public class SongOfLevitation extends AbstractMGRCard {
    public static final String ID = "MGR:SongOfLevitation";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 1;
    private static final int BLOCK = 3;
    private static final int PLUS_BLOCK = 2;
    private static final int MAGIC = 1;
    //private static final int PLUS_MAGIC = 0;
    public SongOfLevitation() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = BLOCK;
        this.baseMagicNumber=MAGIC;
        this.magicNumber=this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SongOfLevitationAction(this.block,AbstractDungeon.player.filledOrbCount(),AbstractDungeon.player.maxOrbs-AbstractDungeon.player.filledOrbCount()));
    }

    public AbstractCard makeCopy() { return new SongOfLevitation(); }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(PLUS_BLOCK);
        }
    }
}
