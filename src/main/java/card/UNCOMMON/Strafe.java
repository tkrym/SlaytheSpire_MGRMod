package card.UNCOMMON;

import action.ChannelNoteAction;
import card.AbstractMGRCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import note.AttackNote;
import path.AbstractCardEnum;

public class Strafe extends AbstractMGRCard {
    public static final String ID = "MGR:Strafe";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 2;
    private static final int MAGIC = 5;
    private static final int PLUS_MAGIC = 2;
    public Strafe() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.MGR_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseMagicNumber=MAGIC;
        this.magicNumber=this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i=1;i<=this.magicNumber;i++)
            addToBot(new DamageAction(m, new DamageInfo(p, 1, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        for(int i=1;i<=this.magicNumber;i++)
            addToBot(new ChannelNoteAction(new AttackNote()));
    }

    public AbstractCard makeCopy() { return new Strafe(); }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(PLUS_MAGIC);
        }
    }
}
