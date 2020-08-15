/**
 * Class BotManager
 * @author David-H-Dev
 *
 * last changes:
 * @date 15.08.2020
 */

package at.AlpenSystems.RandomMessages.utils;

import at.AlpenSystems.RandomMessages.commands.*;
import at.AlpenSystems.RandomMessages.listeners.OnMessage;
import at.AlpenSystems.RandomMessages.utils.config.Config;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;

public class BotManager {

        public static void startBot() {
            try {
                JDABuilder.createDefault(
                        Config.get("TOKEN"),
                        GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.GUILD_VOICE_STATES,
                        GatewayIntent.DIRECT_MESSAGES,
                        GatewayIntent.GUILD_PRESENCES

                ).disableCache(EnumSet.of(CacheFlag.CLIENT_STATUS, CacheFlag.ACTIVITY, CacheFlag.EMOTE)
                ).enableCache(CacheFlag.VOICE_STATE

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