package potion;

import action.ChannelNoteAction;
import action.TemporaryDuplicationAction;
import character.MGR_character;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import note.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;

public class Doping extends AbstractPotion
{
    public static final String POTION_ID = "MGR:Doping";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public Doping()
    {
        super(potionStrings.NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.BOLT, PotionColor.WHITE);
        //this.labOutlineColor = MGR_character.MyColor;
        this.isThrown = false;
    }

    @Override
    public void initializeData()
    {
        this.potency = getPotency();
        this.description = potionStrings.DESCRIPTIONS[0] + this.potency + potionStrings.DESCRIPTIONS[1]+(this.potency>>1)+potionStrings.DESCRIPTIONS[2];
        this.tips.clear(); this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public void use(AbstractCreature target)
    {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
        {
            addToBot(new AbstractGameAction() {
                @Override
                public void update()
                {
                    ArrayList<AbstractCard> PowerCards = new ArrayList<>();
                    for (AbstractCard c : AbstractDungeon.player.drawPile.group)
                        if (c.type.equals(AbstractCard.CardType.POWER)) PowerCards.add(c);
                    if (PowerCards.isEmpty())
                        AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, CardCrawlGame.languagePack.getTutorialString("MGR:exception").TEXT[2], true));
                    else
                    {
                        Collections.shuffle(PowerCards, new Random(AbstractDungeon.miscRng.randomLong()));
                        for (int i = 1; i <= Doping.this.potency; i++)
                            if (PowerCards.size() >= i)
                            {
                                AbstractCard c = PowerCards.get(i - 1);
                                AddToHand(c);
                            }
                        for (AbstractCard c : AbstractDungeon.player.hand.group) c.applyPowers();
                        AbstractDungeon.player.hand.refreshHandLayout();
                    }
                    this.isDone=true;
                }
            });
            addToBot(new GainEnergyAction(this.potency>>1));
        }
    }

    @Override
    public int getPotency(int ascensionLevel) {return 2;}

    @Override
    public AbstractPotion makeCopy() {return new Doping();}

    private void AddToHand(AbstractCard c)
    {
        if (AbstractDungeon.player.hand.size() <= 9)
        {
            //c.setCostForTurn(c.costForTurn - 1);
            AbstractDungeon.player.drawPile.removeCard(c);
            AbstractDungeon.player.hand.addToHand(c);
            c.lighten(false);
            c.unhover();
            c.applyPowers();
        }
        else AbstractDungeon.player.createHandIsFullDialog();
    }
}
