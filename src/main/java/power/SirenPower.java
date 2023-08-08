package power;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

public class SirenPower extends AbstractPower
{
    public static final String POWER_ID = "MGR:SirenPower";
    private static final String IMG = "img/power/" + POWER_ID.substring(4) + ".png";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public SirenPower(int amount)
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        updateDescription();
        this.img = new Texture(IMG);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer)
    {
        int BlockSum = 0;
        AbstractPlayer p = AbstractDungeon.player;
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
            if (!m.isDeadOrEscaped())
            {
                int Msum = 0;
                for (AbstractPower power : m.powers)
                    if (power.type.equals(PowerType.DEBUFF))
                    {
                        Msum += Math.abs(power.amount);
                        BlockSum++;
                    }
                if (Msum > 0) m.damage(new DamageInfo(AbstractDungeon.player, Msum, DamageInfo.DamageType.HP_LOSS));
            }
        for (AbstractPower power : p.powers)
            if (power.type.equals(PowerType.DEBUFF))
                BlockSum++;
        this.flashWithoutSound();
        if(BlockSum>0) addToBot(new GainBlockAction(p, BlockSum * this.amount));
    }

    @Override
    public void updateDescription() {this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];}
}
