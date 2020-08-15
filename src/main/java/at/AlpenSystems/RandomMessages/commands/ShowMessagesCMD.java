/**
 * Class ShowMessagesCMD
 * @author David-H-Dev
 *
 * last changes:
 * @date 15.08.2020
 */

package at.AlpenSystems.RandomMessages.commands;

import java.util.Objects;

import at.AlpenSystems.RandomMessages.utils.sql.DBStatement;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ShowMessagesCMD extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        MessageChannel channel = event.getChannel();
        if(msg.getChannel().getType() == ChannelType.TEXT) {
            long serverid = msg.getGuild().getIdLong();
            if (msg.getContentRaw().startsWith("?messages")) {
                DBStatement dbStatement = new DBStatement();
                if (!msg.getAuthor().isBot()) {
                    if (Objects.requireNonNull(msg.getMember()).hasPermission(Permission.ADMINISTRATOR)) {
                        dbStatement.getTable(serverid, channel);
                    } else {
                        channel.sendMessage(": x: Error: You have no permissions to do this!").queue();
                    }
                } else {
                    channel.sendMessage(":x: Error: Unfortunately, a bot cannot execute this command!").queue();
                }
            }
        }
    }
}