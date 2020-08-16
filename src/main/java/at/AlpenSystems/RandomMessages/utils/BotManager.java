/**
 * Class BotManager
 * @author David-H-Dev
 *
 * last changes:
 * @date 16.08.2020
 */

package at.AlpenSystems.RandomMessages.utils;

import at.AlpenSystems.RandomMessages.commands.*;
import at.AlpenSystems.RandomMessages.listeners.OnMessage;
import at.AlpenSystems.RandomMessages.utils.config.Config;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class BotManager {

        public static void startBot() {
            try {
                JDABuilder.createDefault(
                        Config.get("TOKEN")

                //Listeners
                ).addEventListeners(new OnMessage()

                //Commands
                ).addEventListeners(new AddMessageCMD()
                ).addEventListeners(new DeleteMessageCMD()
                ).addEventListeners(new SetChannelCMD()
                ).addEventListeners(new ShowMessagesCMD()
                ).addEventListeners(new PingCMD()

                ).setActivity(Activity.playing(Config.get("STATUS"))

                ).build();
            } catch (LoginException e) {
                e.printStackTrace();
            }
    }
}