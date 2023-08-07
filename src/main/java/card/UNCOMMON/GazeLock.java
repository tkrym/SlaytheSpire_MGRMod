package card.UNCOMMON;

import action.ApplyGazeAction;
import action.ChannelNoteAction;
import action.GazeLockAction;
import basemod.abstracts.CustomCard;
import card.AbstractMGRCard;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.FlickCoinEffect;
import note.DebuffNote;
import path.AbstractCardEnum;

public class GazeLock extends AbstractMGRCard {
    public static final String ID = "MGR:GazeLock";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 1;
    private static final int MAGIC = 2;
    private static final int PLUS_MAGIC = 2;
    public GazeLock() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseMagicNumber=MAGIC;
        this.magicNumber=this.baseMagicNumber;
        //ExhaustiveField.ExhaustiveFields.baseExhaustive.set(this, 2);
        //ExhaustiveField.ExhaustiveFields.exhaustive.set(this, 2);
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new AbstractGameAction() {
            @Override
            public void update()
            {
                for(AbstractPower power:m.powers)
                    if(power.type.equals(AbstractPower.PowerType.DEBUFF))
                    {
                        addToTop(new DrawCardAction(1));
                        addToTop(new ApplyGazeAction(m,GazeLock.this.magicNumber));
                    }
                this.isDone=true;
            }
        });
        //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        //AbstractDungeon.actionManager.addToBottom(new GazeLockAction(m));
        //this.UpdateExhaustiveDescription();
    }

    public AbstractCard makeCopy() { return new GazeLock(); }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(PLUS_MAGIC);
        }
    }
}
