package relic;
import action.ChannelNoteAction;
import basemod.abstracts.CustomRelic;
import character.MGR_character;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import hook.OnChordHook;
import note.*;

import java.util.ArrayList;

public class TheFirst extends CustomRelic implements OnChordHook {
    public static final String ID = "MGR:TheFirst";
    private static final String IMG = "img/relic/"+ID.substring(4)+".png";
    private static final String OUTLINE = "img/relic/outline/"+ID.substring(4)+".png";
    private ArrayList<AbstractNote> notes=new ArrayList();
    private int cnt=0;

    public TheFirst() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(OUTLINE), RelicTier.STARTER, AbstractRelic.LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart()
    {
        notes.clear();
        notes.add(new AttackNote());
        notes.add(new DefendNote());
        notes.add(new DrawNote());
        notes.add(new ArtifactNote());
        notes.add(new DebuffNote());
        cnt=0;
    }

    @Override
    public void atTurnStart()
    {
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new ChannelNoteAction(notes.get(cnt)));
        cnt=(cnt+1)%notes.size();
    }

    public String getUpdatedDescription() { return this.DESCRIPTIONS[0]; }

    public void OnChord(ArrayList<AbstractNote> notes)
    {
        //AbstractDungeon.actionManager.addToBottom(new TalkAction(true,notes.get(0).name,3.0F,3.0F));
    }

    @Override
    public void onPlayerEndTurn()
    {
        //if(MGR_character.BigBrotherStanceCheck())
            //AbstractDungeon.actionManager.addToBottom(new TalkAction(true,"Hi!",2.0F,2.0F));
    }

}
