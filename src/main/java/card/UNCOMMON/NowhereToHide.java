package card.UNCOMMON;

import action.ApplyGazeAction;
import action.GazeLoseHpAction;
import card.AbstractMGRCard;
import character.MGR_character;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import path.AbstractCardEnum;
import power.GazePower;

public class NowhereToHide extends AbstractMGRCard
{
    public static final String ID = "MGR:NowhereToHide";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "img/card/" + ID.substring(4) + ".png";
    private static final int COST = 2;
    /*private static final int DMG = 8;
    private static final int PLUS_DMG = 3;*/
    private static final int MAGIC = 9;
    private static final int PLUS_MAGIC = 3;
    /*private static final int BLOCK = 2;
    private static final int PLUS_BLOCK = 1;*/

    public NowhereToHide()
    {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.MGR_COLOR, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.baseMagicNumber=MAGIC;
        this.magicNumber=this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        //addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            if (!mo.isDeadOrEscaped())
                addToBot(new ApplyGazeAction(mo, this.magicNumber));
        }
        addToBot(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.RED_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), Settings.FAST_MODE ? 0.3F : 0.75F));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            if (!mo.isDeadOrEscaped())
                addToBot(new GazeLoseHpAction(mo, this));
        }
        /*if (MGR_character.AwakenedStanceCheck())
            addToBot(new AbstractGameAction()
            {
                @Override
                public void update()
                {
                    for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters)
                    {
                        if (!mo.isDeadOrEscaped())
                        {
                            for (int i = 1; i <= NowhereToHide.this.block; i++)
                            {
                                addToTop(new WaitAction(0.1f));
                                addToTop(new GazeLoseHpAction(mo));
                            }
                        }
                    }
                    this.isDone = true;
                }
            });*/
    }

    public AbstractCard makeCopy() {return new NowhereToHide();}

    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeMagicNumber(PLUS_MAGIC);
        }
    }
}
