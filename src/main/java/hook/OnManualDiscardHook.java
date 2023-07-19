package hook;

import com.megacrit.cardcrawl.cards.AbstractCard;

public interface OnManualDiscardHook {
    void OnManualDiscard(AbstractCard c);
}
