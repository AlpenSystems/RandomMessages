/**
 * Class SetChannelCMD
 * @author David-H-Dev
 *
 * last changes:
 * @date 15.08.2020
 */

package at.AlpenSystems.RandomMessages.commands;

import at.AlpenSystems.RandomMessages.utils.sql.DBStatement;
import java.util.Objects;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SetChannelCMD extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        MessageChannel channel = event.getChannel();
        if(msg.getChannel().getType() == ChannelType.TEXT) {
            long serverid = msg.getGuild().getIdLong();
            if (msg.getContentRaw().startsWith("?setchannel")) {
                DBStatement dbStatement = new DBStatement();
                if (!msg.getAuthor().isBot()) {
                    if (Objects.requireNonNull(msg.getMember()).hasPermission(Permission.ADMINISTRATOR)) {
                        if (msg.getContentRaw().length() >= 13) {
                            try {
                                long setchannelid = Long.parseLong(msg.getContentRaw().substring(12));
                                dbStatement.setActionChannel(serverid, setchannelid);
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