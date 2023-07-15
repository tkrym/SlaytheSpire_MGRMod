package card.UNCOMMON;

import card.AbstractMGRCard;
import character.MGR_character;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlickCoinEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;
import path.AbstractCardEnum;

public class MaguroBash extends AbstractMGRCard {
    public static final String ID = "MGR:MaguroBash";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 2;
    private static final int DMG = 10;
    private static final int PLUS_DMG = 4;
    private static final int MAGIC = 6;
    private static final int PLUS_MAGIC = 2;
    private int OriginDamage;
    public MaguroBash() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.MGR_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = DMG;
        this.baseMagicNumber=MAGIC;
        this.magicNumber=this.baseMagicNumber;
        this.OriginDamage=this.baseDamage;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (m != null) {
            addToBot(new VFXAction(new VerticalImpactEffect(m.hb.cX + (m.hb.width / 4.0f), m.hb.cY - (m.hb.height / 4.0f))));
        }
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public void applyPowers()
    {
        this.baseDamage=this.OriginDamage+this.magicNumber*MGR_character.GetChordCount();
        super.applyPowers();
    }

    @Override
    public void onMoveToDiscard()
    {
        this.baseDamage=this.OriginDamage;
        this.initializeDescription();
    }

    public AbstractCard makeCopy() { return new MaguroBash(); }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(PLUS_DMG);
            this.upgradeMagicNumber(PLUS_MAGIC);
            this.OriginDamage=this.baseDamage;
        }
    }
}
