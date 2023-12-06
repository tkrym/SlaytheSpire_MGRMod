package card.COMMON;

import card.AbstractMGRCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import path.AbstractCardEnum;
import power.GazePower;
import relic.BloodshotEyeball;

public class GazeOfOthers extends AbstractMGRCard
{
    public static final String ID = "MGR:GazeOfOthers";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/" + ID.substring(4) + ".png";
    private static final int COST = 1;
    private static final int BLOCK = 7;
    private static final int PLUS_BLOCK = 3;
    private static final int MAGIC = 3;
    //private static final int PLUS_MAGIC = -1;
    public GazeOfOthers()
    {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseBlock=BLOCK;
        this.baseMagicNumber=MAGIC;
        this.magicNumber=this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new GainBlockAction(p,this.block));
        if(m.hasPower(GazePower.POWER_ID)&&m.getPower(GazePower.POWER_ID).amount>=this.magicNumber)
            addToBot(new GainBlockAction(p,this.block));
            /*addToBot(new AbstractGameAction() {
                @Override
                public void update()
                {
                    AbstractPower power=m.getPower(GazePower.POWER_ID);
                    power.amount-= GazeOfOthers.this.magicNumber;
                    if (power.amount < 1 && p.hasRelic(BloodshotEyeball.ID)) power.amount = 1;
                    if (power.amount <= 0) m.powers.remove(power);
                    else power.updateDescription();
                    addToTop(new GainBlockAction(p, GazeOfOthers.this.block));
                    this.isDone=true;
                }
            });*/
    }

    @Override
    public void triggerOnGlowCheck()
    {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
            if (!m.isDeadOrEscaped() && m.hasPower(GazePower.POWER_ID)&&m.getPower(GazePower.POWER_ID).amount>=this.magicNumber)
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
    }

    public AbstractCard makeCopy() {return new GazeOfOthers();}

    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeBlock(PLUS_BLOCK);
            //this.upgradeMagicNumber(PLUS_MAGIC);
        }
    }
}
