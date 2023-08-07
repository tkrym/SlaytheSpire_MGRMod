package card;

import action.ChannelNoteAction;
import character.MGR_character;
import com.badlogic.gdx.graphics.Color;
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
    public boolean IsStarryCard=false;

    public AbstractMGRCard(String id, String name, String img, int cost, String description, AbstractCard.CardType type, AbstractCard.CardColor color, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) {
        super(id, name, img, cost, description, type, color, rarity, target);
    }

    public void UpdateExhaustiveDescription()
    {
        if(this.purgeOnUse||this.exhaust) return;
        this.rawDescription=this.rawDescription.substring(0,this.rawDescription.length()-1)+ ExhaustiveField.ExhaustiveFields.exhaustive.get(this);
        initializeDescription();
    }

    protected void triggerOnGlowCheck_Awakened() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if(MGR_character.InAwakenedStance())
            this.glowColor = new Color(1.0F,0.4F,1.0F,1.0F);
    }

    protected void triggerOnGlowCheck_Ending() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if(MGR_character.EndingCheck())
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
    }

    protected void triggerOnGlowCheck_Starting() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if(MGR_character.StartingCheck())
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
    }
}

