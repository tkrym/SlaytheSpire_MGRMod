package relic;
import action.ChannelNoteAction;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import note.*;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;

public class TheFirst extends CustomRelic {
    public static final String ID = "MGR:TheFirst";
    private static final String IMG = "img/relic/"+ID.substring(4)+".png";
    private static final String OUTLINE = "img/relic/outline/"+ID.substring(4)+".png";
    private ArrayList<AbstractNote> notes=new ArrayList();
    private int cnt=0;

    public TheFirst() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(OUTLINE), RelicTier.STARTER, AbstractRelic.LandingSound.CLINK);
    }

    @Override
    public void atBattleStart()
    {
        notes.clear();
        notes.add(new AttackNote());
        notes.add(new DefendNote());
        notes.add(new PowerNote());
        notes.add(new DrawNote());
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

}
