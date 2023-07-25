package power;

import card.SPECIAL.Confused;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.Iterator;

public class SongOfSubmersionPower extends AbstractPower {
    public static final String POWER_ID = "MGR:SongOfSubmersionPower";
    private static final String IMG = "img/power/"+POWER_ID.substring(4)+".png";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public SongOfSubmersionPower(int amount) {
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
        flashWithoutSound();
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if(!m.isDeadOrEscaped())
                addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new StrengthPower(m, -1), -1, true, AbstractGameAction.AttackEffect.NONE));
        }
        this.amount--;
        if(this.amount<=0)
            addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player,AbstractDungeon.player,POWER_ID));
        else updateDescription();
    }

    @Override
    public void updateDescription() {this.description = DESCRIPTIONS[0]+this.amount+DESCRIPTIONS[1];}
}
