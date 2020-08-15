/**
 * Class PingCMD
 * @author David-H-Dev
 *
 * last changes:
 * @date 15.08.2020
 */

package at.AlpenSystems.RandomMessages.commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PingCMD extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        if (msg.getContentRaw().equals("?ping")) {
            MessageChannel channel = event.getChannel();
            long time = System.currentTimeMillis();
            channel.sendMessage("Loading ping...").queue(response -> response.editMessageFormat("Ping: %d ms", new Object[] {
                    System.currentTimeMillis() - time
            }).queue());
        }
    }
}