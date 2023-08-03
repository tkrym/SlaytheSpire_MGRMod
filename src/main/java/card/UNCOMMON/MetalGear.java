package card.UNCOMMON;

import card.AbstractMGRCard;
import character.MGR_character;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import path.AbstractCardEnum;
import power.SongOfSubmersionPower;

public class MetalGear extends AbstractMGRCard {
    public static final String ID = "MGR:MetalGear";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 2;
    private static final int MAGIC = 4;
    private static final int PLUS_MAGIC = 1;
    public MetalGear() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber=MAGIC;
        this.magicNumber=this.baseMagicNumber;
        this.exhaust=true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int DecDexterity=this.upgraded?-3:-3;
        int PlusNumber=this.upgraded?3:2;
        addToBot(new ApplyPowerAction(p,p,new DexterityPower(p,DecDexterity),DecDexterity));
        addToBot(new ApplyPowerAction(p,p,new MetallicizePower(p,this.magicNumber),this.magicNumber));
        addToBot(new ApplyPowerAction(p,p,new PlatedArmorPower(p,this.magicNumber),this.magicNumber));
        if(MGR_character.BigBrotherStanceCheck())
        {
            addToBot(new ApplyPowerAction(p,p,new MetallicizePower(p,PlusNumber),PlusNumber));
            addToBot(new ApplyPowerAction(p,p,new PlatedArmorPower(p,PlusNumber),PlusNumber));
        }
    }

    public AbstractCard makeCopy() { return new MetalGear(); }

    @Override
    public void triggerOnGlowCheck(){triggerOnGlowCheck_BigBrother();}

    public void upgrade() {
        if(!this.upgraded)
        {
            this.upgradeName();
            this.upgradeMagicNumber(PLUS_MAGIC);
            this.rawDescription=UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
