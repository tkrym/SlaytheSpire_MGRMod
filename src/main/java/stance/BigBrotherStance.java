package stance;

import character.MGR_subscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.DivinityParticleEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;
import effect.BigBrotherStanceAuraEffect;
import effect.BigBrotherStanceParticleEffect;
import effect.EnterBigBrotherStanceEffect;

/* loaded from: DivinityStance.class */
public class BigBrotherStance extends AbstractStance {
    public static final String STANCE_ID = "MGR:BigBrotherStance";
    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);
    private static long sfxId = -1;
    private float particleTimer3;
    private float particleTimerIndex1= MathUtils.random(0.8f, 2.0f);
    private float particleTimerIndex2= MathUtils.random(0.8f, 2.0f);
    private float particleTimerIndex3= MathUtils.random(0.8f, 2.0f);
    private float particleTimerIndex4= MathUtils.random(0.8f, 2.0f);

    public BigBrotherStance() {
        this.ID = STANCE_ID;
        this.name = stanceString.NAME;
        updateDescription();
    }

    @Override
    public void updateAnimation() {
        this.particleTimerIndex1 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimerIndex1 < 0.0f) {
            this.particleTimerIndex1 = MathUtils.random(0.8f, 2.0f);
            AbstractDungeon.effectsQueue.add(new BigBrotherStanceParticleEffect(1));
        }
        this.particleTimerIndex2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimerIndex2 < 0.0f) {
            this.particleTimerIndex2 = MathUtils.random(0.8f, 2.0f);
            AbstractDungeon.effectsQueue.add(new BigBrotherStanceParticleEffect(2));
        }
        this.particleTimerIndex3 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimerIndex3 < 0.0f) {
            this.particleTimerIndex3 = MathUtils.random(0.8f, 2.0f);
            AbstractDungeon.effectsQueue.add(new BigBrotherStanceParticleEffect(3));
        }
        this.particleTimerIndex4 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimerIndex4 < 0.0f) {
            this.particleTimerIndex4 = MathUtils.random(0.8f, 2.0f);
            AbstractDungeon.effectsQueue.add(new BigBrotherStanceParticleEffect(4));
        }
        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0f) {
            this.particleTimer2 = MathUtils.random(0.45f, 0.55f);
            AbstractDungeon.effectsQueue.add(new BigBrotherStanceAuraEffect());
        }
        this.particleTimer3 -=Gdx.graphics.getDeltaTime();
        if (this.particleTimer3 < 0.0f) {
            this.particleTimer3 = MathUtils.random(0.1f, 0.15f);
            AbstractMonster m=AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
            AbstractDungeon.effectsQueue.add(new BigBrotherStanceParticleEffect(m));
        }
    }

    @Override
    public void onEndOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(NeutralStance.STANCE_ID));
    }

    @Override
    public void updateDescription() {
        this.description = stanceString.DESCRIPTION[0];
    }

    @Override
    public void onEnterStance() {
        if (sfxId != -1) {
            stopIdleSfx();
        }
        CardCrawlGame.sound.play("STANCE_ENTER_DIVINITY");
        sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_DIVINITY");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(CardHelper.getColor(0,230,245), true));
        AbstractDungeon.effectsQueue.add(new EnterBigBrotherStanceEffect());
        if(!MGR_subscriber.BanBigBrotherStanceEffect)
        {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
        }
    }

    @Override
    public void onExitStance() {
        stopIdleSfx();
    }

    @Override
    public void stopIdleSfx() {
        if (sfxId != -1) {
            CardCrawlGame.sound.stop("STANCE_LOOP_DIVINITY", sfxId);
            sfxId = -1;
        }
    }
}

