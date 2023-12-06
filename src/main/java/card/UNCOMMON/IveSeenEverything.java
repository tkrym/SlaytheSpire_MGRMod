package card.UNCOMMON;

import action.ChannelNoteAction;
import action.ChristmasGiftAction;
import card.AbstractMGRCard;
import character.MGR_character;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import effect.AwakenedStanceParticleEffect;
import effect.IveSeenEverythingEffect;
import note.*;
import path.AbstractCardEnum;
import stance.AwakenedStance;

public class IveSeenEverything extends AbstractMGRCard {
    public static final String ID = "MGR:IveSeenEverything";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 1;
    public IveSeenEverything() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.UNCOMMON, CardTarget.SELF);
        this.exhaust=true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new VFXAction(AbstractDungeon.player,new IveSeenEverythingEffect(),1.0f,true));
        if(!MGR_character.AwakenedStanceCheck()) addToBot(new ChangeStanceAction(new AwakenedStance()));
        else
        {
            addToBot(new ChannelNoteAction(new AttackNote()));
            addToBot(new ChannelNoteAction(new DefendNote()));
            addToBot(new ChannelNoteAction(new DrawNote()));
            addToBot(new ChannelNoteAction(new DebuffNote()));
            addToBot(new ChannelNoteAction(new ArtifactNote()));
            addToBot(new ChannelNoteAction(new StarryNote()));
        }
    }

    public AbstractCard makeCopy() { return new IveSeenEverything(); }

    @Override
    public void triggerOnGlowCheck(){triggerOnGlowCheck_Awakened();}

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }
}
