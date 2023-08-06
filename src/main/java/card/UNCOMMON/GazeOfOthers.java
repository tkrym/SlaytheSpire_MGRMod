package card.UNCOMMON;

import card.AbstractMGRCard;
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

public class GazeOfOthers extends AbstractMGRCard
{
    public static final String ID = "MGR:GazeOfOthers";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private String DESC;
    public static final String IMG = "img/card/" + ID.substring(4) + ".png";
    private static final int COST = 1;
    private static final int BLOCK = 4;
    private static final int PLUS_BLOCK = 2;
    private static final int MAGIC = 1;

    public GazeOfOthers()
    {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseBlock = BLOCK;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        DESC = DESCRIPTION;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        int BLOCK = GetAdditionalBlock(m);
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block + BLOCK));
    }

    private int calculateBLOCK(AbstractMonster m)
    {
        AbstractPower p = m.getPower(GazePower.POWER_ID);
        if (p != null) return this.magicNumber * p.amount;
        else return 0;
    }

    private int calculateBLOCKAll()
    {
        int sum = 0;
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            if (!m.isDeadOrEscaped() && m.hasPower(GazePower.POWER_ID))
            {
                sum += m.getPower(GazePower.POWER_ID).amount;
            }
        }
        return sum;
    }

    private int GetAdditionalBlock(AbstractMonster m)
    {
        int amt = this.upgraded ? calculateBLOCKAll() : calculateBLOCK(m);
        //int amt=calculateBLOCK(m);
        return amt;
    }

    private void BLOCKdisplay(int BLOCK)
    {
        this.rawDescription = DESC + EXTENDED_DESCRIPTION[0] + BLOCK + EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }

    @Override
    public void onMoveToDiscard()
    {
        this.rawDescription = DESC;
        initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster m)
    {
        super.calculateCardDamage(m);
        int BLOCK = GetAdditionalBlock(m);
        BLOCKdisplay(BLOCK);
    }

    public AbstractCard makeCopy() {return new GazeOfOthers();}

    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeBlock(PLUS_BLOCK);
            DESC = UPGRADE_DESCRIPTION;
            this.rawDescription = DESC;
            initializeDescription();
        }
    }
}
