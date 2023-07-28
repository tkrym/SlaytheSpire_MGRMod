package card.RARE;

import action.ApplyForteAction;
import card.AbstractMGRCard;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import path.AbstractCardEnum;

public class Shatter extends AbstractMGRCard {
    public static final String ID = "MGR:Shatter";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 2;
    private static final int BLOCK = 3;
    private static final int PLUS_BLOCK = 1;
    private static final int MAGIC = 4;
    public Shatter() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.RARE, CardTarget.SELF);
        this.baseBlock=BLOCK;
        this.baseMagicNumber=MAGIC;
        this.magicNumber=this.baseMagicNumber;
        this.exhaust=true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int cnt=AbstractDungeon.player.hand.size();
                if(cnt>=Shatter.this.magicNumber)
                    addToTop(new ApplyForteAction(1));
                for(int i=1;i<=cnt;i++)
                {
                    addToTop(new GainBlockAction(AbstractDungeon.player,Shatter.this.block));
                    if (Settings.FAST_MODE) addToTop(new ExhaustAction(1, true, true, false, Settings.ACTION_DUR_XFAST));
                    else addToTop(new ExhaustAction(1, true, true));
                }
                this.isDone=true;
            }
        });
        if(this.upgraded) UpdateExhaustiveDescription();
    }

    public AbstractCard makeCopy() { return new Shatter(); }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(PLUS_BLOCK);
            this.exhaust=false;
            ExhaustiveField.ExhaustiveFields.baseExhaustive.set(this, 2);
            ExhaustiveField.ExhaustiveFields.exhaustive.set(this, 2);
            this.rawDescription=UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
