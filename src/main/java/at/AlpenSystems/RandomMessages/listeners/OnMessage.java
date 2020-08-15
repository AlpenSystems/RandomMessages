/**
 * Class OnMessage
 * @author David-H-Dev
 *
 * last changes:
 * @date 15.08.2020
 */

package at.AlpenSystems.RandomMessages.listeners;

import java.sql.SQLException;
import java.util.EventListener;

import at.AlpenSystems.RandomMessages.utils.sql.DBStatement;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class OnMessage extends ListenerAdapter implements EventListener {
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        if(msg.getChannel().getType() == ChannelType.TEXT) {
            long serverid = msg.getGuild().getIdLong();
            DBStatement dbStatement = new DBStatement();
            String dbMSG;
            if (!msg.getAuthor().isBot()) {
                try {
                    dbStatement.createTable(serverid);
                    dbStatement.createConfigTable(serverid);
                    if (msg.getChannel().getIdLong() == dbStatement.getActionChannel(serverid)) {
                        try {
                            dbMSG = dbStatement.getMessage(serverid);
                            TextChannel textChannel = event.getGuild().getTextChannelById(dbStatement.getActionChannel(serverid));
                            if (textChannel != null) {
                                textChannel.sendMessage(dbMSG).queue();
                            } else {
                                System.out.println("Error getting the channel!");
                            }
                        } catch (SQLException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}