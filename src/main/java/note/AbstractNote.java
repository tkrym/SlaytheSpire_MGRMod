package note;

import action.ChannelNoteAction;
import card.RARE.LAB01;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.BobEffect;

import power.*;

import java.util.ArrayList;

public abstract class AbstractNote extends AbstractOrb
{
    public static final String POWER_ID=FortePower.POWER_ID;
    public int forterate = 1;
    public AbstractNote() {
        this.c = Settings.CREAM_COLOR.cpy();
        this.shineColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        this.hb = new Hitbox(96.0F * Settings.scale, 96.0F * Settings.scale);
        this.img = null;
        this.bobEffect = new BobEffect(3.0F * Settings.scale, 3.0F);
        this.fontScale = 0.7F;
        this.channelAnimTimer = 0.5F;
        this.passiveAmount=0;
        this.basePassiveAmount=0;
    }

    @Override
    public void applyFocus()
    {
        this.applyForte();
    }

    public void myApplyForte()
    {
        AbstractPower power = AbstractDungeon.player.getPower(POWER_ID);
        if (power!=null)
        {
            if(power.amount>0) this.evokeAmount = Math.max(0,this.baseEvokeAmount+Math.floorDiv(power.amount,this.forterate));
            else this.evokeAmount = Math.max(0,this.baseEvokeAmount-Math.floorDiv(-power.amount,this.forterate));
        }
        else this.evokeAmount = this.baseEvokeAmount;
    }


    public void applyForte()
    {
        myApplyForte();
        AbstractPlayer p=AbstractDungeon.player;
        int sum=1;
        if(p.hasPower(UnisonLeft.POWER_ID)&&this.equals(p.orbs.get(0))) sum+=p.getPower(UnisonLeft.POWER_ID).amount;
        if(p.hasPower(UnisonRight.POWER_ID)&&this.equals(p.orbs.get(p.maxOrbs-1))) sum+=p.getPower(UnisonRight.POWER_ID).amount;
        this.evokeAmount*=sum;
    }

    @Override
    protected void renderText(SpriteBatch sb)
    {
        if(!(this instanceof EmptyNoteSlot))
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.evokeAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, this.c, this.fontScale);
    }

    @Override
    public void setSlot(int slotNum, int maxOrbs)
    {
        this.tX = AbstractDungeon.player.drawX;
        this.tY = 250.0F * Settings.scale + AbstractDungeon.player.drawY + AbstractDungeon.player.hb_h / 2.0F;
        this.tX+=((float)slotNum-((float)maxOrbs-1)/2)*135.0F*Settings.scale;
        this.hb.move(this.tX, this.tY);
    }

    public static AbstractNote GetCorrespondingNote(AbstractCard c)
    {
        AbstractNote note;
        switch (c.type)
        {
            case ATTACK:note=new AttackNote();break;
            case SKILL:note=new DefendNote();break;
            case POWER:note=new DrawNote();break;
            case STATUS:note=new DebuffNote();break;
            case CURSE:note=new ArtifactNote();break;
            default:note=new EmptyNoteSlot();
        }
        return note;
    }

    public static void GenerateNoteBottom(AbstractCard c)
    {
        AbstractDungeon.actionManager.addToBottom(new ChannelNoteAction(GetCorrespondingNote(c)));
    }
    public static void GenerateNoteTop(AbstractCard c)
    {
        AbstractDungeon.actionManager.addToTop(new ChannelNoteAction(GetCorrespondingNote(c)));
    }
    public static AbstractNote GetRandomBasicNote()
    {
        ArrayList<AbstractNote> notes = new ArrayList<>();
        notes.add(new AttackNote());
        notes.add(new DefendNote());
        notes.add(new DrawNote());
        notes.add(new DebuffNote());
        notes.add(new ArtifactNote());
        return notes.get(AbstractDungeon.cardRandomRng.random(notes.size() - 1));
    }
    public static void GenerateRandomBasicNoteBottom()
    {
        AbstractDungeon.actionManager.addToBottom(new ChannelNoteAction(GetRandomBasicNote()));
    }

    public abstract void myEvoke();

    public void onEvoke()
    {
        this.TriggerEvokeEffect();
        if(!(this instanceof EmptyNoteSlot)) LAB01Check();
    }

    public void TriggerEvokeEffect()
    {
        myEvoke();
        if(AbstractDungeon.player.hasPower(HarmonyFormPower.POWER_ID)) myEvoke();
    }


    public void LAB01Check()
    {
        for (AbstractCard c : AbstractDungeon.player.hand.group)
            if(c instanceof LAB01)
                ((LAB01)c).AddNote(this);
    }

    public static int applyVulnerable(AbstractCreature m, int dmg) {
        int retVal = dmg;
        if (m.hasPower(VulnerablePower.POWER_ID)&&AbstractDungeon.player.hasPower(StereoPlusPower.POWER_ID)) {
            retVal = (int)(m.getPower(VulnerablePower.POWER_ID).atDamageReceive((float)dmg, DamageInfo.DamageType.NORMAL));
        }
        return retVal;
    }

    public abstract AbstractNote makeCopy();
}
