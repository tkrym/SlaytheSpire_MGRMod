package card.RARE;

import action.ChannelNoteAction;
import card.AbstractMGRCard;
import character.MGR_character;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import note.*;
import path.AbstractCardEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;

public class Masterful extends AbstractMGRCard {
    public static final String ID = "MGR:Masterful";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 0;
    public Masterful() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.RARE, CardTarget.SELF);
        this.exhaust=true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new AbstractGameAction() {
            @Override
            public void update()
            {
                if(AbstractDungeon.actionManager.orbsChanneledThisTurn.stream().anyMatch(o->(o instanceof GhostNote)))
                    addToTop(new ChannelNoteAction(new GhostNote()));
                if(AbstractDungeon.actionManager.orbsChanneledThisTurn.stream().anyMatch(o->(o instanceof StarryNote)))
                    addToTop(new ChannelNoteAction(new StarryNote()));
                if(AbstractDungeon.actionManager.orbsChanneledThisTurn.stream().anyMatch(o->(o instanceof ArtifactNote)))
                    addToTop(new ChannelNoteAction(new ArtifactNote()));
                if(AbstractDungeon.actionManager.orbsChanneledThisTurn.stream().anyMatch(o->(o instanceof DebuffNote)))
                    addToTop(new ChannelNoteAction(new DebuffNote()));
                if(AbstractDungeon.actionManager.orbsChanneledThisTurn.stream().anyMatch(o->(o instanceof DrawNote)))
                    addToTop(new ChannelNoteAction(new DrawNote()));
                if(AbstractDungeon.actionManager.orbsChanneledThisTurn.stream().anyMatch(o->(o instanceof DefendNote)))
                    addToTop(new ChannelNoteAction(new DefendNote()));
                if(AbstractDungeon.actionManager.orbsChanneledThisTurn.stream().anyMatch(o->(o instanceof AttackNote)))
                    addToTop(new ChannelNoteAction(new AttackNote()));
                this.isDone=true;
            }
        });
    }

    public AbstractCard makeCopy() { return new Masterful(); }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription=UPGRADE_DESCRIPTION;
            this.exhaust=false;
            initializeDescription();
        }
    }
}
