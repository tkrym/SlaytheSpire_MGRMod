package action;

import character.MGR_character;
import character.MGR_subscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import ui.MGRTutorial;

public class MGRTutorialAction extends AbstractGameAction
{
    public MGRTutorialAction() {}

    public void update()
    {
        if (MGR_subscriber.EnableTutorial)
        {
            AbstractDungeon.ftue = new MGRTutorial();
            MGR_subscriber.EnableTutorial = false;
            try
            {
                SpireConfig config = new SpireConfig("MGRMod", "CustomSettings", MGR_subscriber.CustomSettings);
                config.setBool(MGR_subscriber.EnableTutorialString, MGR_subscriber.EnableTutorial);
                config.save();
            } catch (Exception ignored) {}
        }
        this.isDone = true;
    }
}
