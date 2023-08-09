package card.RARE;

import action.ChannelNoteAction;
import card.AbstractMGRCard;
import card.SPECIAL.FrenziedDragonBite;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.vfx.SpotlightEffect;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;
import note.AttackNote;
import path.AbstractCardEnum;

public class LightUpTheStage extends AbstractMGRCard
{
    public static final String ID = "MGR:LightUpTheStage";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "img/card/" + ID.substring(4) + ".png";
    private static final int COST = 1;
    private static final int DMG = 3;
    private static final int MAGIC = 3;
    private static final int PLUS_MAGIC = 1;
    private static final int INC_DMG = 3;
    private static final int UPGRADE_INC_DMG=3;

    public LightUpTheStage()
    {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.MGR_COLOR, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.baseDamage = DMG;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new VFXAction(new SpotlightEffect(), (Settings.FAST_MODE?0.3f:0.5f)));
        this.magicNumber=this.baseMagicNumber;
        for(int i=1;i<=this.magicNumber;i++)
        {
            AbstractGameAction.AttackEffect myeffect;
            int rnd= MathUtils.random(2);
            if(rnd==0) myeffect= AbstractGameAction.AttackEffect.SLASH_DIAGONAL;
            else if(rnd==1) myeffect= AbstractGameAction.AttackEffect.SLASH_HORIZONTAL;
            else myeffect= AbstractGameAction.AttackEffect.SLASH_VERTICAL;
            addToBot(new DamageRandomEnemyAction(new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    myeffect));
        }
        this.baseDamage+=3;
    }

    @Override
    public void triggerOnManualDiscard()
    {
        this.baseMagicNumber++;
        this.baseMagicNumber++;
        this.magicNumber=this.baseMagicNumber;
    }

    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeMagicNumber(PLUS_MAGIC);
        }
    }
}
