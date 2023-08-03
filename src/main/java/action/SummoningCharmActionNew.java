package action;

import card.COMMON.Marionette;
import card.COMMON.StardustLaser;
import card.COMMON.StarrySkyObservation;
import card.RARE.Obakenoukenerai;
import card.SPECIAL.Confused;
import card.UNCOMMON.Futariboshi;
import card.UNCOMMON.Kimitomitahosizora;
import card.UNCOMMON.StarryDrift;
import character.MGR_character;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Fusion;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.watcher.MasterRealityPower;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import note.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class SummoningCharmActionNew extends AbstractGameAction
{
    private boolean upgraded;

    public SummoningCharmActionNew(boolean upgraded)
    {
        this.upgraded = upgraded;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update()
    {
        AbstractPlayer p = AbstractDungeon.player;
        if (!(p instanceof MGR_character) || p.filledOrbCount() == 0)
        {
            this.isDone = true;
            return;
        }
        for (int i = p.orbs.size() - 1; i >= 0; --i)
        {
            AbstractOrb orb = p.orbs.get(i);
            if (orb instanceof EmptyNoteSlot || !(orb instanceof AbstractNote)) continue;
            AbstractNote note = (AbstractNote) orb;
            note.triggerEvokeAnimation();
            p.orbs.set(i, new EmptyNoteSlot());
            p.orbs.get(i).setSlot(i, p.maxOrbs);
            addToTop(new WaitAction(0.1f));
            GetCorrespondingCard(note);
        }
        this.isDone = true;
    }

    private void GetCorrespondingCard(AbstractNote note)
    {
        AbstractCard c;
        if (note instanceof AttackNote)
            c = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.ATTACK).makeCopy();
        else if (note instanceof DefendNote)
        {
            c = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.SKILL).makeCopy();
            while (c instanceof Marionette)
                c = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.SKILL).makeCopy();
        }
        else if (note instanceof DrawNote)
            c = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.POWER).makeCopy();
        else if (note instanceof DebuffNote) c = GetRandomStatusCard();
        else if (note instanceof ArtifactNote) c = AbstractDungeon.returnRandomCurse().makeCopy();
        else if (note instanceof StarryNote) c = GetRandomStarryCard();
        else if (note instanceof GhostNote) c = new Obakenoukenerai();
        else c = new Confused();
        if (this.upgraded) c.upgrade();
        addToTop(new MakeTempCardInHandAction(c, 1));
    }

    private AbstractCard GetRandomStarryCard()
    {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        cards.add(new StardustLaser());
        cards.add(new StarrySkyObservation());
        cards.add(new StarryDrift());
        cards.add(new Kimitomitahosizora());
        cards.add(new Futariboshi());
        return cards.get(AbstractDungeon.cardRandomRng.random(cards.size() - 1));
    }

    private AbstractCard GetRandomStatusCard()
    {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        cards.add(new Dazed());
        cards.add(new VoidCard());
        cards.add(new Wound());
        cards.add(new Burn());
        cards.add(new Slimed());
        cards.add(new Confused());
        cards.add(new Marionette());
        return cards.get(AbstractDungeon.cardRandomRng.random(cards.size() - 1));
    }
}

