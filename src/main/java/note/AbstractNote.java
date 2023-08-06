package note;

import action.ChannelNoteAction;
import card.AbstractMGRCard;
import card.RARE.LAB01;
import card.COMMON.Marionette;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.BobEffect;

import com.megacrit.cardcrawl.vfx.combat.FrostOrbPassiveEffect;
import effect.NotePassiveEffect;
import effect.NoteTriggeredEffect;
import patch.EnergyFontPatch;
import power.*;

import java.util.ArrayList;

public abstract class AbstractNote extends AbstractOrb
{
    public static final String POWER_ID = FortePower.POWER_ID;
    protected float vfxTimer = 0.3f;
    public int forterate = 1;
    protected Color myColor = new Color(1.0F, 1.0F, 1.0F, 1.0F);

    public AbstractNote()
    {
        this.c = Settings.CREAM_COLOR.cpy();
        this.shineColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        this.hb = new Hitbox(96.0F * Settings.scale, 96.0F * Settings.scale);
        this.img = null;
        this.bobEffect = new BobEffect(3.0F * Settings.scale, 3.0F);
        this.fontScale = 0.7F;
        this.channelAnimTimer = 0.5F;
        this.passiveAmount = 0;
        this.basePassiveAmount = 0;
    }

    @Override
    public void applyFocus()
    {
        this.applyForte();
    }

    public void myApplyForte()
    {
        AbstractPower power = AbstractDungeon.player.getPower(POWER_ID);
        if (power != null)
        {
            if (power.amount > 0)
                this.evokeAmount = Math.max(0, this.baseEvokeAmount + Math.floorDiv(power.amount, this.forterate));
            else this.evokeAmount = Math.max(0, this.baseEvokeAmount - Math.floorDiv(-power.amount, this.forterate));
        }
        else this.evokeAmount = this.baseEvokeAmount;
    }


    public void applyForte()
    {
        myApplyForte();
        /*AbstractPlayer p = AbstractDungeon.player;
        int sum = 1;
        if (p.hasPower(UnisonLeft.POWER_ID) && this.equals(p.orbs.get(0)))
            sum += p.getPower(UnisonLeft.POWER_ID).amount;
        if (p.hasPower(UnisonRight.POWER_ID) && this.equals(p.orbs.get(p.maxOrbs - 1)))
            sum += p.getPower(UnisonRight.POWER_ID).amount;
        this.evokeAmount *= sum;*/
    }

    @Override
    protected void renderText(SpriteBatch sb)
    {
        if (!(this instanceof EmptyNoteSlot))
            FontHelper.renderFontCentered(sb, EnergyFontPatch.energyNumFontMGR_blue, Integer.toString(this.evokeAmount),
                    this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, this.c, this.fontScale);
    }

    @Override
    public void setSlot(int slotNum, int maxOrbs)
    {
        this.tX = AbstractDungeon.player.drawX;
        this.tY = 250.0F * Settings.scale + AbstractDungeon.player.drawY + AbstractDungeon.player.hb_h / 2.0F;
        this.tX += ((float) slotNum - ((float) maxOrbs - 1) / 2) * 135.0F * Settings.scale;
        this.hb.move(this.tX, this.tY);
    }

    public static AbstractNote GetCorrespondingNote(AbstractCard c)
    {
        if (c instanceof AbstractMGRCard && ((AbstractMGRCard) c).IsStarryCard) return new StarryNote();
        else if (c instanceof Marionette) return new DebuffNote();
        AbstractNote note;
        switch (c.type)
        {
            case ATTACK:
                note = new AttackNote(); break;
            case SKILL:
                note = new DefendNote(); break;
            case POWER:
                note = new DrawNote(); break;
            case STATUS:
                note = new DebuffNote(); break;
            case CURSE:
                note = new ArtifactNote(); break;
            default:
                note = new EmptyNoteSlot();
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
        int seed = AbstractDungeon.cardRandomRng.random(99);
        if (!AbstractDungeon.player.hasPower(RiverOfNotesPower.POWER_ID))
        {
            if (seed < 40) return new AttackNote();//40%
            else if (seed < 75) return new DefendNote();//35%
            else if (seed < 83) return new DebuffNote();//8%
            else if (seed < 95) return new DrawNote();//14%
            else return new ArtifactNote();//5%
        }
        else
        {
            if (seed < 30) return new AttackNote();//30%
            else if (seed < 60) return new DefendNote();//30%
            else if (seed < 67) return new StarryNote();//7%
            else if (seed < 82) return new DrawNote();//15%
            else if (seed < 92) return new DebuffNote();//10%
            else return new ArtifactNote();//8%
        }
    }

    public static void GenerateRandomBasicNoteBottom()
    {
        AbstractDungeon.actionManager.addToBottom(new ChannelNoteAction(GetRandomBasicNote()));
    }

    public static void GenerateRandomBasicNoteTop()
    {
        AbstractDungeon.actionManager.addToTop(new ChannelNoteAction(GetRandomBasicNote()));
    }

    public abstract void myEvoke();

    public void onEvoke()
    {
        if (this instanceof EmptyNoteSlot) return;
        this.applyForte();
        int sum = 1;
        if (AbstractDungeon.player.hasPower(HarmonyFormPower.POWER_ID) && this.StartOrEnd())
            sum += AbstractDungeon.player.getPower(HarmonyFormPower.POWER_ID).amount;
        for (int i = 1; i <= sum; i++)
        {
            myEvoke();
            LAB01Check();
        }
    }

    public void LAB01Check()
    {
        for (AbstractCard c : AbstractDungeon.player.hand.group)
            if (c instanceof LAB01) ((LAB01) c).AddNote(this);
    }

    public abstract AbstractNote makeCopy();

    public void playChannelSFX()
    {
        CardCrawlGame.sound.play("MGR:NoteChannel", 0.2f);
    }

    @Override
    public void updateAnimation()
    {
        super.updateAnimation();
        updateDescription();
        this.angle += Gdx.graphics.getDeltaTime() * 2.0f;
        this.vfxTimer -= Gdx.graphics.getDeltaTime();
        if (!(this instanceof EmptyNoteSlot) && !(this instanceof GhostNote) && this.vfxTimer < 0.0f)
        {
            AbstractDungeon.effectList.add(new NotePassiveEffect(this.cX, this.cY, this.myColor.cpy()));
            if (MathUtils.randomBoolean())
            {
                AbstractDungeon.effectList.add(new NotePassiveEffect(this.cX, this.cY, this.myColor.cpy()));
            }
            this.vfxTimer = MathUtils.random(0.2F, 0.6F);
        }
    }

    public void render(SpriteBatch sb)
    {
        float scale = 1 + MathUtils.sin(this.angle) * 0.05F + 0.05F;
        this.shineColor = this.c.cpy();
        this.shineColor.a = this.c.a / (2*scale-1);
        sb.setBlendFunction(770, 771);
        sb.setColor(this.shineColor);
        sb.draw(this.img, this.cX - 48.0f, (this.cY - 48.0f) + this.bobEffect.y * 0.5F, 48.0f, 48.0f, 96.0f, 96.0f,
                this.scale * scale, this.scale * scale, 0.0f, 0, 0, 96, 96, false, false);
        if (AbstractDungeon.player.hasPower(HarmonyFormPower.POWER_ID) && this.StartOrEnd())
        {
            Color tmpColor = this.shineColor.cpy();
            tmpColor.a /= 2;
            sb.setColor(tmpColor);
            sb.setBlendFunction(770, 1);
            sb.draw(this.img, this.cX - 48.0f, (this.cY - 48.0f) + this.bobEffect.y * 0.5F, 48.0f, 48.0f, 96.0f,
                    96.0f, this.scale * scale * 1.3F, this.scale * scale * 1.3F, 0.0f, 0, 0, 96, 96, false, false);
            sb.setBlendFunction(770, 771);
        }
        renderText(sb);
        this.hb.render(sb);
    }

    @Override
    public void triggerEvokeAnimation()
    {
        AbstractDungeon.effectsQueue.add(new NoteTriggeredEffect(this.cX - 48.0F, this.cY - 48.0F, this.img, this.scale, this.c.cpy()));
    }

    public boolean StartOrEnd()
    {
        AbstractPlayer p = AbstractDungeon.player;
        return this.equals(p.orbs.get(0)) || this.equals(p.orbs.get(p.orbs.size() - 1));
    }


    public static int GetNoteTypeCountThisTurn()
    {
        return CountNoteType(AbstractDungeon.actionManager.orbsChanneledThisTurn);
    }

    public static int CountNoteType(ArrayList<AbstractOrb> notes)
    {
        boolean AttackPlayed = false;
        boolean DefendPlayed = false;
        boolean DrawPlayed = false;
        boolean DebuffPlayed = false;
        boolean ArtifactPlayed = false;
        boolean StarryPlayed = false;
        boolean GhostPlayed = false;
        for (AbstractOrb orb : notes)
        {
            if (orb instanceof AttackNote) AttackPlayed = true;
            else if (orb instanceof DefendNote) DefendPlayed = true;
            else if (orb instanceof DrawNote) DrawPlayed = true;
            else if (orb instanceof DebuffNote) DebuffPlayed = true;
            else if (orb instanceof ArtifactNote) ArtifactPlayed = true;
            else if (orb instanceof StarryNote) StarryPlayed = true;
            else if (orb instanceof GhostNote) GhostPlayed = true;
        }
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
}
