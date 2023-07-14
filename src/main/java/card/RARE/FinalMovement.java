package card.RARE;

import action.ChannelNoteAction;
import basemod.abstracts.CustomCard;
import card.AbstractMGRCard;
import character.MGR_character;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import note.AttackNote;
import path.AbstractCardEnum;

import java.util.Iterator;
import java.util.UUID;

public class FinalMovement extends AbstractMGRCard {
    public static final String ID = "MGR:FinalMovement";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 1;
    public FinalMovement() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.MGR_COLOR, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.baseMagicNumber=2;
        this.magicNumber=this.baseMagicNumber;
        this.misc=2;
        ExhaustiveField.ExhaustiveFields.baseExhaustive.set(this, 2);
        ExhaustiveField.ExhaustiveFields.exhaustive.set(this, 2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToTop(new TalkAction(true,"MAGIC="+this.magicNumber,3.0F,4.0F));
        for(int i=0;i<this.magicNumber;i++)
            AbstractDungeon.actionManager.addToBottom(new ChannelNoteAction(new AttackNote()));
        if(MGR_character.BigBrotherStanceCheck())
            for(int i=0;i<this.magicNumber;i++)
                AbstractDungeon.actionManager.addToBottom(new ChannelNoteAction(new AttackNote()));
        this.UpdateExhaustiveDescription();
    }

    public AbstractCard makeCopy() { return new FinalMovement(); }

    @Override
    public void applyPowers()
    {
        super.applyPowers();
        this.damage=this.baseDamage=0;
        this.isDamageModified=false;
        this.magicNumber=this.baseMagicNumber;
        initializeDescription();
    }

    public void upgrade() {
        if(!this.upgraded)
        {
            this.upgradeName();
            ExhaustiveField.ExhaustiveFields.baseExhaustive.set(this, 3);
            ExhaustiveField.ExhaustiveFields.exhaustive.set(this, 3);
            this.UpdateExhaustiveDescription();
        }
    }

    public static void IncMist(UUID tagetuuid, int amount)
    {
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
            if (c.uuid.equals(tagetuuid)) {
                c.misc += amount;
                c.baseMagicNumber=c.misc;
                c.applyPowers();
            }
        for (AbstractCard c : GetAllInBattleInstances.get(tagetuuid))
        {
            c.misc+=amount;
            c.baseMagicNumber=c.misc;
            c.applyPowers();
        }
    }
}
