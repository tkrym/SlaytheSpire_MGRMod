package note;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import note.AbstractNote;
import java.util.Objects;

public class EmptyNoteSlot extends AbstractNote{
    public static final String ORB_ID = "MGR:Empty";
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESC = orbString.DESCRIPTION;
    public EmptyNoteSlot(float x, float y) {
        this.angle = MathUtils.random(360.0f);
        this.ID = ORB_ID;
        this.name = orbString.NAME;
        this.evokeAmount = 0;
        this.cX = x;
        this.cY = y;
        updateDescription();
        this.channelAnimTimer = 0.5f;
    }

    @Override
    public void applyForte(){}

    public EmptyNoteSlot() {
        this.angle = MathUtils.random(360.0f);
        this.name = orbString.NAME;
        this.evokeAmount = 0;
        this.cX = AbstractDungeon.player.drawX + AbstractDungeon.player.hb_x;
        this.cY = AbstractDungeon.player.drawY + AbstractDungeon.player.hb_y + (AbstractDungeon.player.hb_h / 2.0f);
        updateDescription();
    }

    @Override
    public void updateDescription()
    {
        this.description = DESC[0];
    }

    @Override
    public void myEvoke() {
    }

    @Override
    public void updateAnimation() {
        super.updateAnimation();
        this.angle += Gdx.graphics.getDeltaTime() * 10.0f;
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.c);
        sb.draw(ImageMaster.ORB_SLOT_2, (this.cX - 48.0f) - (this.bobEffect.y / 8.0f), (this.cY - 48.0f) + (this.bobEffect.y / 8.0f), 48.0f, 48.0f, 96.0f, 96.0f, this.scale, this.scale, 0.0f, 0, 0, 96, 96, false, false);
        sb.draw(ImageMaster.ORB_SLOT_1, (this.cX - 48.0f) + (this.bobEffect.y / 8.0f), (this.cY - 48.0f) - (this.bobEffect.y / 8.0f), 48.0f, 48.0f, 96.0f, 96.0f, this.scale, this.scale, this.angle, 0, 0, 96, 96, false, false);
        renderText(sb);
        this.hb.render(sb);
    }

    public AbstractNote makeCopy() {
        return new EmptyNoteSlot();
    }

    public void playChannelSFX() {
    }
}
