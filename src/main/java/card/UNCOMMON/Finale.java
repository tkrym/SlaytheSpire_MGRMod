package card.UNCOMMON;

import action.ChannelNoteAction;
import action.GazeLockAction;
import card.AbstractMGRCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;
import note.AttackNote;
import path.AbstractCardEnum;

import java.util.Iterator;

public class Finale extends AbstractMGRCard
{
    public static final String ID = "MGR:Finale";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "img/card/" + ID.substring(4) + ".png";
    private static final int COST = 1;
    private static final int MAGIC = 2;
    private static final int PLUS_MAGIC = 1;

    public Finale()
    {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.MGR_COLOR, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        this.isEthereal=true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if(p.hand.group.stream().filter(c->!c.type.equals(CardType.ATTACK)).count()>=5)
            addToBot(new VFXAction(new GrandFinalEffect(), Settings.FAST_MODE?0.7f:1.0f));
        addToBot(new AbstractGameAction()
        {
            @Override
            public void update()
            {
                int amt=0;
                for (AbstractCard c : AbstractDungeon.player.hand.group)
                    if (c.type != CardType.ATTACK)
                    {
                        amt++;
                        this.addToTop(new DiscardSpecificCardAction(c));
                    }
                for(int i=1;i<=Finale.this.magicNumber*amt;i++) addToBot(new ChannelNoteAction(new AttackNote()));
                this.isDone = true;
            }
        });
    }

    public AbstractCard makeCopy() {return new Finale();}

    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeMagicNumber(PLUS_MAGIC);
        }
    }
}
