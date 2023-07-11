package card.BASIC;

import basemod.abstracts.CustomCard;
import card.AbstractMGRCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import path.AbstractCardEnum;

public class Defend_MGR extends AbstractMGRCard {
    public static final String ID = "MGR:Defend_MGR";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 1;
    private static final int BLOCK = 4;
    private static final int PLUS_BLOCK = 3;
    public Defend_MGR() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.BASIC, CardTarget.SELF);
        this.tags.add(CardTags.STARTER_DEFEND);
        this.baseBlock = BLOCK;
    }

    public void myUse(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    }

    public AbstractCard makeCopy() { return new Defend_MGR(); }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(PLUS_BLOCK);
        }
    }

}
