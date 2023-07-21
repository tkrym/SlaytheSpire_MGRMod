package power;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class TinyOrchestraPower extends AbstractPower {
    public static final String POWER_ID = "MGR:TinyOrchestraPower";
    private static final String IMG = "img/power/"+POWER_ID.substring(4)+".png";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean AttackPlayed;
    private boolean SkillPlayed;
    private boolean PowerPlayed;
    private boolean STATUSPlayed;
    private boolean CURSEPlayed;

    public TinyOrchestraPower(int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        updateDescription();
        this.img = new Texture(IMG);
    }

    private int CountType()
    {
        int cnt=0;
        if(AttackPlayed) cnt++;
        if(SkillPlayed) cnt++;
        if(PowerPlayed) cnt++;
        if(STATUSPlayed) cnt++;
        if(CURSEPlayed) cnt++;
        return cnt;
    }


    @Override
    public void atEndOfTurn(boolean isPlayer)
    {
        if(CountType()>0)
        {
            flashWithoutSound();
            addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(CountType()*this.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            if(CountType()>=3)
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DrawCardNextTurnPower(AbstractDungeon.player, 1), 1));
        }
        AttackPlayed=false;
        SkillPlayed=false;
        PowerPlayed=false;
        STATUSPlayed=false;
        CURSEPlayed=false;
    }

    @Override
    public void onUseCard(AbstractCard c, UseCardAction action)
    {
        switch (c.type)
        {
            case ATTACK:AttackPlayed=true;break;
            case SKILL:SkillPlayed=true;break;
            case POWER:PowerPlayed=true;break;
            case STATUS:STATUSPlayed=true;break;
            case CURSE:CURSEPlayed=true;break;
            default:;
        }
        updateDescription();
    }

    @Override
    public void updateDescription()
    {
        this.description = DESCRIPTIONS[0]+this.amount+DESCRIPTIONS[1]+CountType()+DESCRIPTIONS[2];
    }
}
