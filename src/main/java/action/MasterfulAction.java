package action;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import note.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;
import java.util.Random;

public class MasterfulAction extends AbstractGameAction
{
    public MasterfulAction() {}

    public void update()
    {
        AbstractPlayer p = AbstractDungeon.player;
        {
            ArrayList<AbstractCard> cards = new ArrayList<>(p.drawPile.group);
            Collections.shuffle(cards, new Random(AbstractDungeon.miscRng.randomLong()));
            for (AbstractCard c : cards)
                if (AbstractNote.GetCorrespondingNote(c) instanceof AttackNote)
                {AddToHand(c); break;}
        }
        {
            ArrayList<AbstractCard> cards = new ArrayList<>(p.drawPile.group);
            Collections.shuffle(cards, new Random(AbstractDungeon.miscRng.randomLong()));
            for (AbstractCard c : cards)
                if (AbstractNote.GetCorrespondingNote(c) instanceof DefendNote)
                {AddToHand(c); break;}
        }
        {
            ArrayList<AbstractCard> cards = new ArrayList<>(p.drawPile.group);
            Collections.shuffle(cards, new Random(AbstractDungeon.miscRng.randomLong()));
            for (AbstractCard c : cards)
                if (AbstractNote.GetCorrespondingNote(c) instanceof DrawNote)
                {AddToHand(c); break;}
        }
        {
            ArrayList<AbstractCard> cards = new ArrayList<>(p.drawPile.group);
            Collections.shuffle(cards, new Random(AbstractDungeon.miscRng.randomLong()));
            for (AbstractCard c : cards)
                if (AbstractNote.GetCorrespondingNote(c) instanceof StarryNote)
                {AddToHand(c); break;}
        }
        {
            ArrayList<AbstractCard> cards = new ArrayList<>(p.drawPile.group);
            Collections.shuffle(cards, new Random(AbstractDungeon.miscRng.randomLong()));
            for (AbstractCard c : cards)
                if (AbstractNote.GetCorrespondingNote(c) instanceof DebuffNote)
                {AddToHand(c); break;}
        }
        {
            ArrayList<AbstractCard> cards = new ArrayList<>(p.drawPile.group);
            Collections.shuffle(cards, new Random(AbstractDungeon.miscRng.randomLong()));
            for (AbstractCard c : cards)
                if (AbstractNote.GetCorrespondingNote(c) instanceof ArtifactNote)
                {AddToHand(c); break;}
        }
        p.hand.applyPowers();
        p.hand.refreshHandLayout();
        this.isDone = true;
    }

    private void AddToHand(AbstractCard c)
    {
        c.setCostForTurn(c.costForTurn - 1);
        c.superFlash(Color.GOLD.cpy());
        c.unhover();
        if (AbstractDungeon.player.hand.size() <= 9)
        {
            AbstractDungeon.player.drawPile.moveToHand(c, AbstractDungeon.player.drawPile);
        }
        else
        {
            AbstractDungeon.player.createHandIsFullDialog();
            AbstractDungeon.player.drawPile.moveToDiscardPile(c);
        }
    }
}

