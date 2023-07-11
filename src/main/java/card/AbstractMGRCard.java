package card;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
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

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower("Realm")) {
            if (this.Realmneed > 0 && p.getPower("Realm").amount < this.Realmneed && this.ALL) {
                if (Settings.language == Settings.GameLanguage.ZHS) {
                    this.cantUseMessage = "境界层数不足，无法打出！";
                    return false;
                }
                this.cantUseMessage = "Realm isn‘t enough!";
                return false;
            }
        } else if (!p.hasPower("Realm") && this.Realmneed > 0 && this.ALL) {
            if (Settings.language == Settings.GameLanguage.ZHS) {
                this.cantUseMessage = "境界层数不足，无法打出！";
                return false;
            }
            this.cantUseMessage = "Realm isn‘t enough!";
            return false;
        }
        if (this.type == AbstractCard.CardType.STATUS && this.costForTurn < -1 && !AbstractDungeon.player.hasRelic("Medical Kit")) {
            return false;
        }
        if (this.type != AbstractCard.CardType.CURSE || this.costForTurn >= -1 || AbstractDungeon.player.hasRelic("Blue Candle")) {
            return hasEnoughEnergy();
        }
        return false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if(beforeUse(p,m)) return;
        myUse(p,m);
        afterUse(p,m);
    }

    public abstract void myUse(AbstractPlayer p, AbstractMonster m);

    public boolean beforeUse(AbstractPlayer p,AbstractMonster m)
    {
        if(this.type==CardType.POWER&&p.hasRelic(UnknownCreature.ID))
        {
            UnknownCreature r=(UnknownCreature) p.getRelic(UnknownCreature.ID);
            if(r.Check()) return true;
        }
        return false;
    }

    public void afterUse(AbstractPlayer p,AbstractMonster m)
    {

    }

    private void UpdateExhaustiveDescription()
    {
        if(this.purgeOnUse) return;
        this.rawDescription=this.rawDescription.substring(0,this.rawDescription.length()-1)+ ExhaustiveField.ExhaustiveFields.exhaustive.get(this);
        initializeDescription();
    }
}

