package card.RARE;

import card.AbstractMGRCard;
import character.MGR_character;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import path.AbstractCardEnum;
import power.HarmonyFormPower;

public class HarmonyForm extends AbstractMGRCard {
    public static final String ID = "MGR:HarmonyForm";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 3;
    public HarmonyForm() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.POWER,
                AbstractCardEnum.MGR_COLOR, CardRarity.RARE, CardTarget.SELF);
        this.isEthereal=true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        /*addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if(AbstractDungeon.player instanceof MGR_character)
                {
                    MGR_character p=(MGR_character)AbstractDungeon.player;
                    if(p.maxOrbs>5) p.decreaseMaxOrbSlots(p.maxOrbs-5);
                    else if(p.maxOrbs<5) p.increaseMaxOrbSlots(5-p.maxOrbs,true);
                }
                this.isDone=true;
            }
        });*/
        if(!p.hasPower(HarmonyFormPower.POWER_ID))
            this.addToBot(new ApplyPowerAction(p,p,new HarmonyFormPower(p),1));
    }

    public AbstractCard makeCopy() { return new HarmonyForm(); }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.isEthereal=false;
            this.rawDescription=UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
