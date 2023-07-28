package power;

import action.GazeLoseHpAction;
import character.MGR_character;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import hook.OnChordHook;
import note.AbstractNote;
import relic.Sunglasses;

import java.util.ArrayList;

public class GazePower extends AbstractPower implements OnChordHook, HealthBarRenderPower
{
    public static final String POWER_ID = "MGR:Gaze";
    private static final String IMG = "img/power/"+POWER_ID.substring(4)+".png";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final int DecreaseAmount=30;

    public GazePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.type=PowerType.DEBUFF;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        this.img = new Texture(IMG);
        this.canGoNegative = false;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
    }

    @Override
    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_POISON", 0.05f);
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0f;
        this.amount += stackAmount;
        if (this.amount == 0) {
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
        if (this.amount >= 999) {
            this.amount = 999;
        }
    }

    @Override
    public void updateDescription() {
        int DecAmount=AbstractDungeon.player.hasRelic(Sunglasses.ID)?Sunglasses.SunglassesNumber:DecreaseAmount;
        this.description=DESCRIPTIONS[0]+this.amount+DESCRIPTIONS[1]+DecAmount+DESCRIPTIONS[2];
    }

    @Override
    public void OnChord(ArrayList<AbstractNote> notes) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.addToTop(new GazeLoseHpAction(this.owner));
        }
    }

    @Override
    public int getHealthBarAmount()
    {
        return this.amount;
    }

    @Override
    public Color getColor()
    {
        return MGR_character.myBuleColor;
    }
}
