package card;

import action.ChannelNoteAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import note.*;
import path.AbstractCardEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import relic.UnknownCreature;

public abstract class AbstractMGRCard extends CustomCard {
    public int Realmneed = -1;
    public boolean ALL = false;

    public AbstractMGRCard(String id, String name, String img, int cost, String description, AbstractCard.CardType type, AbstractCard.CardColor color, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) {
        super(id, name, img, cost, description, type, color, rarity, target);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (this.type == AbstractCard.CardType.STATUS && this.costForTurn < -1 && !AbstractDungeon.player.hasRelic("Medical Kit")) {
            return false;
        } else if (this.type == AbstractCard.CardType.CURSE && this.costForTurn < -1 && !AbstractDungeon.player.hasRelic("Blue Candle")) {
            return false;
        } else {
            return this.cardPlayable(m) && this.hasEnoughEnergy();
        }
    }

    public void UpdateExhaustiveDescription()
    {
        if(this.purgeOnUse) return;
        this.rawDescription=this.rawDescription.substring(0,this.rawDescription.length()-1)+ ExhaustiveField.ExhaustiveFields.exhaustive.get(this);
        initializeDescription();
    }
}

