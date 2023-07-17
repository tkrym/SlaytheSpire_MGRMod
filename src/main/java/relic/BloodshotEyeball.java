package relic;
import action.ApplyGazeAction;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class BloodshotEyeball extends CustomRelic{
    public static final String ID = "MGR:BloodshotEyeball";
    private static final String IMG = "img/relic/"+ID.substring(4)+".png";
    private static final String OUTLINE = "img/relic/outline/"+ID.substring(4)+".png";
    private static final int MAGIC = 3;
    private static final int TIMES = 3;

    public BloodshotEyeball() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(OUTLINE), RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    public String getUpdatedDescription() { return this.DESCRIPTIONS[0]+MAGIC+this.DESCRIPTIONS[1]+TIMES+this.DESCRIPTIONS[2]; }

    @Override
    public void atBattleStartPreDraw() {
        flash();
        for(int i=1;i<=TIMES;++i) {
            AbstractMonster monster = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(monster, this));
            this.addToBot(new ApplyGazeAction(monster,MAGIC));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BloodshotEyeball();
    }

}
