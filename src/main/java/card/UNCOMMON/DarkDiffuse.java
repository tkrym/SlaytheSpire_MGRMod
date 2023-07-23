package card.UNCOMMON;

import card.AbstractMGRCard;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import path.AbstractCardEnum;
import power.GazePower;

public class DarkDiffuse extends AbstractMGRCard {
    public static final String ID = "MGR:DarkDiffuse";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 1;
    private static final int DMG = 6;
    private static final int PLUS_DMG = 3;
    public DarkDiffuse() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.MGR_COLOR, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.baseDamage = DMG;
        this.isMultiDamage = true;
        ExhaustiveField.ExhaustiveFields.baseExhaustive.set(this, 2);
        ExhaustiveField.ExhaustiveFields.exhaustive.set(this, 2);
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Color.DARK_GRAY.cpy(), ShockWaveEffect.ShockWaveType.NORMAL), Settings.FAST_MODE?0.3F:0.75F));
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo.hasPower(GazePower.POWER_ID)) {
                int amt = mo.getPower(GazePower.POWER_ID).amount>>1;
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new StrengthPower(mo, -amt), -amt));
                if (!mo.hasPower("Artifact"))
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new GainStrengthPower(mo, amt), amt));
            }
        }
        UpdateExhaustiveDescription();
    }

    public AbstractCard makeCopy() { return new DarkDiffuse(); }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(PLUS_DMG);
        }
    }
}
