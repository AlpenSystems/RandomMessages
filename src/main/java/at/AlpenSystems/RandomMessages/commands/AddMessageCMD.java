/**
 * Class AddMessageCMD
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

public class AddMessageCMD extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        if(msg.getChannel().getType() == ChannelType.TEXT) {
            MessageChannel channel = event.getChannel();
            long serverid = msg.getGuild().getIdLong();
            if (msg.getContentRaw().startsWith("?add")) {
                DBStatement dbStatement = new DBStatement();
                if (!msg.getAuthor().isBot()) {
                    if (Objects.requireNonNull(msg.getMember()).hasPermission(Permission.ADMINISTRATOR)) {
                        if (msg.getContentRaw().length() >= 6) {
                            try {
                                String dbmessage = msg.getContentRaw().substring(5);
                                dbStatement.insertMessage(serverid, dbmessage);
                                channel.sendMessage(":white_check_mark: Entry successfully added to the database!").queue();
                            } catch (Exception e) {
                                channel.sendMessage(":x: Error: Entry couldn't be added to the database!").queue();
                                e.printStackTrace();
                            }
                        } else {
                            channel.sendMessage(":x: Error: Please enter the message you want to add!").queue();
                        }
                    } else {
                        channel.sendMessage(":x: Error: You have no permissions to do this!").queue();
                    }
                } else {
                    channel.sendMessage(":x: Error: Unfortunately, a bot cannot execute this command!").queue();
                }
            }
        }
    }
}