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
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import note.*;

public class TinyOrchestraPower extends AbstractPower
{
    public static final String POWER_ID = "MGR:TinyOrchestraPower";
    private static final String IMG = "img/power/" + POWER_ID.substring(4) + ".png";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean AttackPlayed;
    private boolean DefendPlayed;
    private boolean DrawPlayed;
    private boolean DebuffPlayed;
    private boolean ArtifactPlayed;
    private boolean StarryPlayed;
    private boolean GhostPlayed;

    public TinyOrchestraPower(int amount)
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        updateDescription();
        this.img = new Texture(IMG);
    }

    private int CountType()
    {
        int cnt = 0;
        if (AttackPlayed) cnt++;
        if (DefendPlayed) cnt++;
        if (DrawPlayed) cnt++;
        if (DebuffPlayed) cnt++;
        if (ArtifactPlayed) cnt++;
        if (StarryPlayed) cnt++;
        if (GhostPlayed) cnt++;
        return cnt;
    }


    @Override
    public void atEndOfTurn(boolean isPlayer)
    {
        UpdateNoteTypeCount();
        if (CountType() > 0)
        {
            flashWithoutSound();
            addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(CountType() * this.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
        AttackPlayed = false;
        DefendPlayed = false;
        DrawPlayed = false;
        DebuffPlayed = false;
        ArtifactPlayed = false;
        StarryPlayed = false;
        GhostPlayed = false;
        updateDescription();
    }

    public void UpdateNoteTypeCount()
    {
        for (AbstractOrb orb : AbstractDungeon.actionManager.orbsChanneledThisTurn)
            if (orb instanceof AttackNote) AttackPlayed = true;
            else if (orb instanceof DefendNote) DefendPlayed = true;
            else if (orb instanceof DrawNote) DrawPlayed = true;
            else if (orb instanceof DebuffNote) DebuffPlayed = true;
            else if (orb instanceof ArtifactNote) ArtifactPlayed = true;
            else if (orb instanceof StarryNote) StarryPlayed = true;
            else if (orb instanceof GhostNote) GhostPlayed = true;
        updateDescription();
    }


    @Override
    public void updateDescription()
    {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + CountType() + DESCRIPTIONS[2];
    }
}
