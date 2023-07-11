package character;


import action.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.beyond.SpireHeart;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import path.ModClassEnum;
import path.AbstractCardEnum;
import java.util.ArrayList;
import java.util.List;
import note.*;
import stance.BigBrotherStance;
import ui.CounterPanel;

public class MGR_character extends CustomPlayer{
    private static final int ENERGY_PER_TURN = 3;
    private static final String MGR_SHOULDER_2 = "img/character/shoulder2.png";
    private static final String MGR_SHOULDER_1 = "img/character/shoulder1.png";
    private static final String MGR_CORPSE = "img/character/fallen.png";
    private static final String MGR_STAND = "img/character/stand.png";
    private static final String[] ORB_TEXTURES = new String[] { "img/UI/EPanel/layer5.png", "img/UI/EPanel/layer4.png", "img/UI/EPanel/layer3.png", "img/UI/EPanel/layer2.png", "img/UI/EPanel/layer1.png", "img/UI/EPanel/layer0.png", "img/UI/EPanel/layer5d.png", "img/UI/EPanel/layer4d.png", "img/UI/EPanel/layer3d.png", "img/UI/EPanel/layer2d.png", "img/UI/EPanel/layer1d.png" };
    private static final String ORB_VFX = "img/UI/energyBlueVFX.png";
    private static final float[] LAYER_SPEED = new float[] { -40.0F, -32.0F, 20.0F, -20.0F, 0.0F, -10.0F, -8.0F, 5.0F, -5.0F, 0.0F };
    private static final int STARTING_HP = 66;
    private static final int MAX_HP = 66;
    private static final int CARD_DRAW = 10;
    private static final int STARTING_GOLD = 99;
    private static final int HAND_SIZE = 4;
    private static final int ASCENSION_MAX_HP_LOSS = 7;
    public static final Color MyColor = CardHelper.getColor(255, 160, 0);
    public static final Color myBuleColor=new Color(1339620607);//
//    public static final Color YuhColor = CardHelper.getColor(255, 200, 80);
    public int counter;
    public int counter_min;
    public int counter_max;
    public int counter_min_master=0;
    public int counter_max_master=4;
    public static CounterPanel myCounterPanel=new CounterPanel();

    public MGR_character(String name) {
        super(name, ModClassEnum.MGR, ORB_TEXTURES, ORB_VFX, LAYER_SPEED, null, null);
        this.dialogX = this.drawX + 0.0F * Settings.scale;
        this.dialogY = this.drawY + 220.0F * Settings.scale;
        initializeClass(MGR_STAND, MGR_SHOULDER_2, MGR_SHOULDER_1, MGR_CORPSE,
                getLoadout(),
                0.0F, 0.0F, 200.0F, 320.0F,
                new EnergyManager(ENERGY_PER_TURN));
    }

    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add("MGR:Strike_MGR");
        retVal.add("MGR:Strike_MGR");
        retVal.add("MGR:Defend_MGR");
        retVal.add("MGR:Defend_MGR");
        retVal.add("MGR:AttackTied");
        retVal.add("MGR:GentleEnding");
        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add("MGR:TheFirst");
        UnlockTracker.markRelicAsSeen("TheFirst");
        return retVal;
    }

    public CharSelectInfo getLoadout() {
        String title="";
        String flavor="";
        if (Settings.language == Settings.GameLanguage.ZHS) {
            title = "MGR_Test";
            flavor = "测试1 NL 测试2";
        } else if (Settings.language == Settings.GameLanguage.ZHT) {
        } else {
        }

        return new CharSelectInfo(title, flavor, STARTING_HP, MAX_HP,HAND_SIZE , STARTING_GOLD, CARD_DRAW, this, getStartingRelics(), getStartingDeck(), false);
    }

    public String getTitle(PlayerClass playerClass) {
        String title;
        if (Settings.language == Settings.GameLanguage.ZHS) {
            title = "MGR_Title";
        } else if (Settings.language == Settings.GameLanguage.ZHT) {
            title = "MGR";
        } else {
            title = "MGR";
        }

        return title;
    }

    public String getLocalizedCharacterName() {
        String char_name;
        if (Settings.language == Settings.GameLanguage.ZHS) {
            char_name = "MGR_NAME";
        } else if (Settings.language == Settings.GameLanguage.ZHT) {
            char_name = "MGR";
        } else {
            char_name = "MGR";
        }
        return char_name;
    }

    public List<CutscenePanel> getCutscenePanels() {
        List<CutscenePanel> panels = new ArrayList<>();
        panels.add(new CutscenePanel("img/victory/VictoryPart1.png","ATTACK_FIRE"));
        panels.add(new CutscenePanel("img/victory/VictoryPart2.png", "WHEEL"));
        return panels;
    }

    public Texture getCutsceneBg() {
        return ImageMaster.loadImage("img/victory/bg.png");
    }

    public AbstractCard.CardColor getCardColor() {return AbstractCardEnum.MGR_COLOR;}

    public Color getCardRenderColor() {return MyColor;}

    public AbstractCard getStartCardForEvent() {return null;}

    public Color getCardTrailColor() {return MyColor;}

    public int getAscensionMaxHPLoss() {return ASCENSION_MAX_HP_LOSS;}

    public BitmapFont getEnergyNumFont() {return FontHelper.energyNumFontBlue;}

    public void doCharSelectScreenSelectEffect() {}

    public String getCustomModeCharacterButtonSoundKey() {return null;}

    public AbstractPlayer newInstance() {return new MGR_character(this.name);}

    public String getSpireHeartText() {return SpireHeart.DESCRIPTIONS[10];}

    public Color getSlashAttackColor() {return MyColor;}

    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL};
    }

    public String getVampireText() {return "Hello, vampires?";}

    @Override
    public void useCard(AbstractCard targetCard, AbstractMonster monster, int energyOnUse) {
        super.useCard(targetCard, monster, energyOnUse);
        GenerateNote(targetCard);
    }

    public void GenerateNote(AbstractCard targetCard)
    {
        if(!targetCard.dontTriggerOnUseCard)
        {
            AbstractNote note;
            switch (targetCard.type)
            {
                case ATTACK:note=new AttackNote();break;
                case SKILL:note=new DefendNote();break;
                case POWER:note=new DrawNote();break;
                case STATUS:note=new DebuffNote();break;
                case CURSE:note=new ArtifactNote();break;
                default:note=new EmptyNoteSlot();
            }
            AbstractDungeon.actionManager.addToBottom(new ChannelNoteAction(note));
        }
    }

    @Override
    public void channelOrb(AbstractOrb orbToSet) {
        if(orbToSet instanceof EmptyOrbSlot || orbToSet instanceof EmptyNoteSlot)
            return;
        else if (this.maxOrbs <= 0 || !(orbToSet instanceof AbstractNote)) {
            AbstractDungeon.effectList.add(new ThoughtBubble(this.dialogX, this.dialogY, 3.0F, MSG[4], true));
        } else
        {
            int index = -1;
            for(int i=0;i<this.orbs.size();++i)
                if (this.orbs.get(i) instanceof EmptyNoteSlot)
                    {index=i;break;}
            if (index!=-1)
            {
                (orbToSet).cX = (this.orbs.get(index)).cX;
                (orbToSet).cY = (this.orbs.get(index)).cY;
                this.orbs.set(index, orbToSet);
                (this.orbs.get(index)).setSlot(index, this.maxOrbs);
                (orbToSet).playChannelSFX();
                for (AbstractPower p : this.powers) p.onChannel(orbToSet);
                AbstractDungeon.actionManager.orbsChanneledThisCombat.add(orbToSet);
                AbstractDungeon.actionManager.orbsChanneledThisTurn.add(orbToSet);
                (orbToSet).applyFocus();
                if(index==this.orbs.size()-1) AbstractDungeon.actionManager.addToTop(new ChordAction());
            } else
            {
                AbstractDungeon.effectList.add(new ThoughtBubble(this.dialogX, this.dialogY, 5.0F, "This should not happen. Report this to the author.", true));
                AbstractDungeon.actionManager.addToTop(new ChannelAction(orbToSet));
                AbstractDungeon.actionManager.addToTop(new EvokeNoteAction(1));
                AbstractDungeon.actionManager.addToTop(new AnimateNoteAction(1));
            }
        }
    }

    @Override
    public void preBattlePrep()
    {
        super.preBattlePrep();
        this.counter_max=this.counter_max_master;
        this.counter_min=this.counter_min_master;
        this.counter=this.counter_min;
    }

    @Override
    public void triggerEvokeAnimation(int slot) { triggerEvokeAnimation();}

    public void triggerEvokeAnimation()
    {
        if (this.maxOrbs > 0) {
            int index = -1;
            for(int i=0;i<this.orbs.size();++i)
                if (!(this.orbs.get(i) instanceof EmptyNoteSlot))
                    {index=i;break;}
            if(index!=-1) this.orbs.get(index).triggerEvokeAnimation();
        }
    }

    @Override
    public void evokeOrb()
    {
        if (!this.orbs.isEmpty())
        {
            int index = -1;
            for(int i=0;i<this.orbs.size();++i)
                if (!(this.orbs.get(i) instanceof EmptyNoteSlot))
                    {index=i;break;}
            if(index==-1) return;
            (this.orbs.get(index)).onEvoke();
            this.orbs.set(index, new EmptyNoteSlot());
            this.orbs.get(index).setSlot(index,this.maxOrbs);
        }
    }

    @Override
    public void increaseMaxOrbSlots(int amount, boolean playSfx) {
        if (this.maxOrbs == 5) {
            AbstractDungeon.effectList.add(new ThoughtBubble(this.dialogX, this.dialogY, 3.0F, MSG[3], true));
        } else {
            if (playSfx) CardCrawlGame.sound.play("ORB_SLOT_GAIN", 0.1F);
            this.maxOrbs += amount;
            for(int i = 0; i < amount; ++i) this.orbs.add(new EmptyNoteSlot());
            for(int i = 0; i < this.orbs.size(); ++i) (this.orbs.get(i)).setSlot(i, this.maxOrbs);
        }
    }

    @Override
    public int filledOrbCount() {
        int orbCount = 0;
        for (AbstractOrb o : this.orbs) {
            if (!(o instanceof EmptyOrbSlot || o instanceof EmptyNoteSlot)) {
                ++orbCount;
            }
        }
        return orbCount;
    }

    public void Inccounter(int amount)
    {
        int changed_number=this.counter+amount;
        if(InBigBrotherStance()&&changed_number>this.counter_max-1) changed_number=this.counter_max-1;
        if(changed_number<this.counter_min) changed_number=this.counter_min;
        if(changed_number!=this.counter) myCounterPanel.EnlargeFontScale();
        this.counter=changed_number;
        checkCounter();
    }

    public void checkCounter()
    {
        if(this.counter>=this.counter_max)
        {
            this.counter=this.counter_min;
            myCounterPanel.EnlargeFontScale();
            AbstractDungeon.actionManager.addToTop(new ChangeStanceAction(new BigBrotherStance()));
        }
    }

    public void updateCounterPanel() {myCounterPanel.updatePositions();}
    public void showCounterPanel() {myCounterPanel.show();}
    public void hideCounterPanel() {myCounterPanel.hide();}
    public void renderCounterPanel(SpriteBatch sb){myCounterPanel.render(sb);}
    public static boolean BigBrotherStanceCheck()
    {
        AbstractPlayer p=AbstractDungeon.player;
        if(p.stance.ID.equals(BigBrotherStance.STANCE_ID))
        {
            AbstractDungeon.actionManager.addToTop(new ChangeStanceAction(NeutralStance.STANCE_ID));
            return true;
        }
        else return false;
    }
    public static boolean InBigBrotherStance() {return AbstractDungeon.player.stance.ID.equals(BigBrotherStance.STANCE_ID);}
}