package relic;
import action.ApplyForteAction;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Fumo extends CustomRelic{
    public static final String ID = "MGR:Fumo";
    private static final String IMG = "img/relic/"+ID.substring(4)+".png";
    private static final String OUTLINE = "img/relic/outline/"+ID.substring(4)+".png";
    private static final int MAGIC = 1;

    public Fumo() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(OUTLINE), RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        flash();
        addToTop(new ApplyForteAction(1));
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    public String getUpdatedDescription() { return this.DESCRIPTIONS[0]+MAGIC+this.DESCRIPTIONS[1]; }

    @Override
    public AbstractRelic makeCopy() {
        return new Fumo();
    }
}
