package action;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;


public class ResonanceFormAddCardAction extends AbstractGameAction {
    private AbstractCard c;
    private static final float PADDING=25.0F * Settings.scale;
    private static final String EXString= CardCrawlGame.languagePack.getTutorialString("MGR:extend").TEXT[0];

    public ResonanceFormAddCardAction(AbstractCard card) {
        this.amount = 1;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.c = card.makeStatEquivalentCopy();
        if (this.c.type != AbstractCard.CardType.CURSE && this.c.type != AbstractCard.CardType.STATUS && AbstractDungeon.player.hasPower("MasterRealityPower")) {
            this.c.upgrade();
        }
        this.c.setCostForTurn(0);
        this.c.exhaust=true;
        this.c.isEthereal=true;
        this.c.rawDescription=this.c.rawDescription+EXString;
        this.c.initializeDescription();
    }

    public void update() {
        if (AbstractDungeon.player.hand.size() + 1 > 10) {
            AbstractDungeon.player.createHandIsFullDialog();
            addToDiscard();
        }
        else addToHand();
        addToTop(new WaitAction(0.3F));
        this.isDone = true;
    }

    private void addToHand() {AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.c, (float) Settings.WIDTH / 2.0F - (PADDING + AbstractCard.IMG_WIDTH), (float)Settings.HEIGHT / 2.0F));}

    private void addToDiscard() {AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.c, (float)Settings.WIDTH / 2.0F + PADDING + AbstractCard.IMG_WIDTH, (float)Settings.HEIGHT / 2.0F));}

}
