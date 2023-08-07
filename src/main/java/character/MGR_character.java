package character;


import action.*;
import card.BASIC.Lullaby;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.vfx.scene.DefectVictoryEyesEffect;
import com.megacrit.cardcrawl.vfx.scene.DefectVictoryNumberEffect;
import com.megacrit.cardcrawl.vfx.scene.IroncladVictoryFlameEffect;
import com.megacrit.cardcrawl.vfx.scene.SlowFireParticleEffect;
import effect.MGRVictoryEffect;
import patch.EnergyFontPatch;
import path.ModClassEnum;
import path.AbstractCardEnum;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import note.*;
import power.FolkRhymesPower;
import power.StereoPlusPower;
import power.StereoPower;
import power.TinyOrchestraPower;
import relic.YourExclusiveStage;
import stance.AwakenedStance;
import ui.CounterPanel;

public class MGR_character extends CustomPlayer
{
    private static final int ENERGY_PER_TURN = 3;
    private static final String MGR_SHOULDER_2 = "img/character/shoulder2.png";
    private static final String MGR_SHOULDER_1 = "img/character/shoulder1.png";
    private static final String MGR_CORPSE = "img/character/fallen.png";
    private static final String MGR_STAND = "img/character/stand.png";
    private static final String[] ORB_TEXTURES = new String[]{"img/UI/EPanel/layer5.png", "img/UI/EPanel/layer4.png", "img/UI/EPanel/layer3.png", "img/UI/EPanel/layer2.png", "img/UI/EPanel/layer1.png", "img/UI/EPanel/layer0.png", "img/UI/EPanel/layer5d.png", "img/UI/EPanel/layer4d.png", "img/UI/EPanel/layer3d.png", "img/UI/EPanel/layer2d.png", "img/UI/EPanel/layer1d.png"};
    private static final String ORB_VFX = "img/UI/energyRefreshVFX.png";
    private static final float[] LAYER_SPEED = new float[]{-60.0F, 60.0F, -40.0F, 60.0F, 360.0F};
    private static final int STARTING_HP = 66;
    private static final int MAX_HP = 66;
    private static final int CARD_DRAW = 5;
    private static final int STARTING_GOLD = 99;
    private static final int HAND_SIZE = 4;
    private static final int ASCENSION_MAX_HP_LOSS = 7;
    public static final Color MyColor = CardHelper.getColor(255, 110, 0);
    public static final Color myBuleColor = new Color(1339620607);//
    //    public static final Color YUHColor = CardHelper.getColor(255, 200, 80);
    public int ChordTriggeredThisTurn;
    public int counter;
    public int counter_min;
    public int counter_max;
    public static final int counter_max_master = 5;
    public static CounterPanel myCounterPanel = new CounterPanel();
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString("MGR:character");
    private float VictoryEffectTimer = 0.0f;

    public MGR_character(String name)
    {
        super(name, ModClassEnum.MGR, ORB_TEXTURES, ORB_VFX, LAYER_SPEED, null, null);
        this.dialogX = this.drawX + 0.0F * Settings.scale;
        this.dialogY = this.drawY + 220.0F * Settings.scale;
        initializeClass(MGR_STAND, MGR_SHOULDER_2, MGR_SHOULDER_1, MGR_CORPSE,
                getLoadout(),
                0.0F, 0.0F, 150.0F, 350.0F,
                new EnergyManager(ENERGY_PER_TURN));
    }

    public ArrayList<String> getStartingDeck()
    {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add("MGR:Strike_MGR");
        retVal.add("MGR:Strike_MGR");
        retVal.add("MGR:Strike_MGR");
        retVal.add("MGR:Strike_MGR");
        retVal.add("MGR:Defend_MGR");
        retVal.add("MGR:Defend_MGR");
        retVal.add("MGR:Defend_MGR");
        retVal.add("MGR:Defend_MGR");
        retVal.add("MGR:Peek");
        retVal.add("MGR:Lullaby");
        return retVal;
    }

    public ArrayList<String> getStartingRelics()
    {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add("MGR:MiniMicrophone");
        return retVal;
    }

    public CharSelectInfo getLoadout() {return new CharSelectInfo(characterStrings.NAMES[0], characterStrings.TEXT[0], STARTING_HP, MAX_HP, HAND_SIZE, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(), getStartingDeck(), false);}

    public String getTitle(PlayerClass playerClass) {return characterStrings.NAMES[0];}

    public String getLocalizedCharacterName() {return characterStrings.NAMES[1];}

    public List<CutscenePanel> getCutscenePanels()
    {
        List<CutscenePanel> panels = new ArrayList<>();
        panels.add(new CutscenePanel("img/victory/VictoryPart1.png", "BLOCK_GAIN_1"));
        panels.add(new CutscenePanel("img/victory/VictoryPart2.png"));
        panels.add(new CutscenePanel("img/victory/VictoryPart3.png", "HEAL_3"));
        return panels;
    }

    public Texture getCutsceneBg()
    {
        return ImageMaster.loadImage("img/victory/bg.png");
    }

    public AbstractCard.CardColor getCardColor() {return AbstractCardEnum.MGR_COLOR;}

    public Color getCardRenderColor() {return MyColor;}

    public AbstractCard getStartCardForEvent() {return new Lullaby();}

    public Color getCardTrailColor() {return MyColor;}

    public int getAscensionMaxHPLoss() {return ASCENSION_MAX_HP_LOSS;}

    public BitmapFont getEnergyNumFont() {return EnergyFontPatch.energyNumFontMGR_orange;}

    public void doCharSelectScreenSelectEffect()
    {
        CardCrawlGame.sound.play("MGR:CharSelect", 0.0F);
    }

    public String getCustomModeCharacterButtonSoundKey() {return null;}

    public AbstractPlayer newInstance() {return new MGR_character(this.name);}

    public String getSpireHeartText()
    {
        return AbstractDungeon.cardRandomRng.random(1) == 0 ? characterStrings.TEXT[1] : characterStrings.TEXT[2];
    }

    public Color getSlashAttackColor() {return MyColor;}

    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect()
    {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL};
    }

    public String getVampireText() {return characterStrings.TEXT[3];}

    @Override
    public void channelOrb(AbstractOrb orbToSet)
    {
        if (orbToSet instanceof EmptyOrbSlot || orbToSet instanceof EmptyNoteSlot) return;
        else if (!(orbToSet instanceof AbstractNote))
            AbstractDungeon.effectList.add(new ThoughtBubble(this.dialogX, this.dialogY, 3.0F, CardCrawlGame.languagePack.getTutorialString("MGR:exception").TEXT[1], true));
        else if (this.maxOrbs <= 0)
            AbstractDungeon.effectList.add(new ThoughtBubble(this.dialogX, this.dialogY, 3.0F, MSG[4], true));
        else
        {
            int index = -1;
            for (int i = 0; i < this.orbs.size(); ++i)
                if (this.orbs.get(i) instanceof EmptyNoteSlot)
                {index = i; break;}
            if (index != -1)
            {
                (orbToSet).cX = (this.orbs.get(index)).cX;
                (orbToSet).cY = (this.orbs.get(index)).cY;
                this.orbs.set(index, orbToSet);
                (this.orbs.get(index)).setSlot(index, this.maxOrbs);
                (orbToSet).playChannelSFX();
                for (AbstractPower p : this.powers) p.onChannel(orbToSet);
                AbstractDungeon.actionManager.orbsChanneledThisCombat.add(orbToSet);
                AbstractDungeon.actionManager.orbsChanneledThisTurn.add(orbToSet);
                if (this.hasPower(TinyOrchestraPower.POWER_ID))
                    this.getPower(TinyOrchestraPower.POWER_ID).updateDescription();
                (orbToSet).applyFocus();
                if (index == this.orbs.size() - 1)
                {
                    AbstractDungeon.actionManager.addToTop(new ChordAction());
                    AbstractDungeon.actionManager.addToTop(new WaitAction(0.05F));
                }
            }
            else
            {
                AbstractDungeon.effectList.add(new ThoughtBubble(this.dialogX, this.dialogY, 5.0F, CardCrawlGame.languagePack.getTutorialString("MGR:exception").TEXT[0], true));
            }
        }
    }

    @Override
    public void preBattlePrep()
    {
        super.preBattlePrep();
        this.counter_max = counter_max_master;
        this.counter_min = AbstractDungeon.player.hasRelic(YourExclusiveStage.ID) ? 1 : 0;
        this.counter = this.counter_min;
    }

    @Override
    public void useCard(AbstractCard c, AbstractMonster m, int e)
    {
        //boolean dt = c.dontTriggerOnUseCard;
        super.useCard(c, m, e);
        if(!c.dontTriggerOnUseCard) AbstractNote.GenerateNoteBottom(c);
    }


    @Override
    public void triggerEvokeAnimation(int slot) {}

    public void EvokeAll()
    {
        for (int i = this.orbs.size() - 1; i >= 0; --i)
        {
            this.orbs.get(i).triggerEvokeAnimation();
            this.orbs.get(i).onEvoke();
            this.orbs.set(i, new EmptyNoteSlot());
            this.orbs.get(i).setSlot(i, this.maxOrbs);
        }
    }


    @Override
    public void evokeOrb() {}

    @Override
    public void increaseMaxOrbSlots(int amount, boolean playSfx)
    {
        if (this.maxOrbs >= 4)
        {
            AbstractDungeon.effectList.add(new ThoughtBubble(this.dialogX, this.dialogY, 3.0F, MSG[3], true));
        }
        else
        {
            if (playSfx) CardCrawlGame.sound.play("ORB_SLOT_GAIN", 0.1F);
            this.maxOrbs += amount;
            for (int i = 0; i < amount; ++i) this.orbs.add(new EmptyNoteSlot());
            for (int i = 0; i < this.orbs.size(); ++i) (this.orbs.get(i)).setSlot(i, this.maxOrbs);
        }
    }

    @Override
    public int filledOrbCount()
    {
        int orbCount = 0;
        for (AbstractOrb o : this.orbs)
        {
            if (!(o instanceof EmptyOrbSlot || o instanceof EmptyNoteSlot))
            {
                ++orbCount;
            }
        }
        return orbCount;
    }

    public static boolean EndingCheck()
    {
        if (AbstractDungeon.player.filledOrbCount() == AbstractDungeon.player.maxOrbs - 1) return true;
        return false;
    }

    public static boolean StartingCheck()
    {
        if (AbstractDungeon.player.filledOrbCount() == 0) return true;
        return false;
    }

    public void inccounter(int amount)
    {
        int changed_number = this.counter + amount;
        if (InAwakenedStance() && changed_number > this.counter_max - 1) changed_number = this.counter_max - 1;
        if (changed_number < this.counter_min) changed_number = this.counter_min;
        if (changed_number != this.counter) myCounterPanel.EnlargeFontScale();
        this.counter = changed_number;
        checkCounter();
    }

    public void checkCounter()
    {
        if (this.counter >= this.counter_max)
        {
            this.counter = this.counter_min;
            myCounterPanel.EnlargeFontScale();
            AbstractDungeon.actionManager.addToTop(new ChangeStanceAction(new AwakenedStance()));
        }
    }

    public void updateCounterPanel() {myCounterPanel.updatePositions();}

    public void showCounterPanel() {myCounterPanel.show();}

    public void hideCounterPanel() {myCounterPanel.hide();}

    public void renderCounterPanel(SpriteBatch sb) {myCounterPanel.render(sb);}

    public static boolean AwakenedStanceCheck()
    {
        if (AbstractDungeon.player.stance.ID.equals(AwakenedStance.STANCE_ID))
        {
            AbstractDungeon.actionManager.addToTop(new ChangeStanceAction(NeutralStance.STANCE_ID));
            return true;
        }
        else return false;
    }

    public static boolean InAwakenedStance() {return AbstractDungeon.player.stance.ID.equals(AwakenedStance.STANCE_ID);}

    public static int GetChordCount()
    {
        if (!(AbstractDungeon.player instanceof MGR_character))
            return 0;
        else return ((MGR_character) AbstractDungeon.player).ChordTriggeredThisTurn;
    }

    public static void IncChordCount(int amount)
    {
        if (AbstractDungeon.player instanceof MGR_character)
            ((MGR_character) AbstractDungeon.player).ChordTriggeredThisTurn += amount;
    }

    public static void IncCounter(int amount)
    {
        if (AbstractDungeon.player instanceof MGR_character)
            ((MGR_character) AbstractDungeon.player).inccounter(amount);
    }

    @Override
    public void applyStartOfTurnRelics() {this.ChordTriggeredThisTurn = 0; super.applyStartOfTurnRelics();}

    public void updateVictoryVfx(ArrayList<AbstractGameEffect> effects)
    {
        boolean foundEyeVfx = false;
        for (AbstractGameEffect e : effects)
            if (e instanceof MGRVictoryEffect)
            {
                foundEyeVfx = true;
                break;
            }
        if (!foundEyeVfx) effects.add(new MGRVictoryEffect());
        this.VictoryEffectTimer -= Gdx.graphics.getDeltaTime();
        if (this.VictoryEffectTimer < 0.0f)
        {
            effects.add(new SlowFireParticleEffect());
            effects.add(new SlowFireParticleEffect());
            effects.add(new SlowFireParticleEffect());
            effects.add(new SlowFireParticleEffect());
            effects.add(new SlowFireParticleEffect());
            this.VictoryEffectTimer = 0.12f;
        }
    }
}