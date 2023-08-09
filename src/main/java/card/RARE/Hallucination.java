package card.RARE;

import card.AbstractMGRCard;
import character.MGR_character;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import effect.IveSeenEverythingEffect;
import path.AbstractCardEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;

public class Hallucination extends AbstractMGRCard {
    public static final String ID = "MGR:Hallucination";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG = "img/card/"+ID.substring(4)+".png";
    private static final int COST = 0;
    public Hallucination() {
        super(ID, cardStrings.NAME, IMG, COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.MGR_COLOR, CardRarity.RARE, CardTarget.ENEMY);
        this.exhaust=true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new VFXAction(AbstractDungeon.player,new BorderFlashEffect(CardHelper.getColor(250,90,150)),1.0f,true));
        if(MGR_character.AwakenedStanceCheck())
        {
            for(AbstractPower power:AbstractDungeon.player.powers)
                if(power.type== AbstractPower.PowerType.DEBUFF)
                {
                    power.owner=m;
                    addToBot(new RemoveSpecificPowerAction(p,p,power));
                    addToBot(new ApplyPowerAction(m,p,power));
                }
        } else
        {
            ArrayList<AbstractPower> powers=new ArrayList<>(AbstractDungeon.player.powers);
            Collections.shuffle(powers, new Random(AbstractDungeon.miscRng.randomLong()));
            Optional<AbstractPower> power=powers.stream().filter(mp->mp.type== AbstractPower.PowerType.DEBUFF).findFirst();
            if(power.isPresent())
            {
                power.get().owner=m;
                addToBot(new RemoveSpecificPowerAction(p,p,power.get()));
                addToBot(new ApplyPowerAction(m,p,power.get()));
            }
        }
    }

    public AbstractCard makeCopy() { return new Hallucination(); }

    @Override
    public void triggerOnGlowCheck(){triggerOnGlowCheck_Awakened();}

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription=UPGRADE_DESCRIPTION;
            this.selfRetain=true;
            initializeDescription();
        }
    }
}
