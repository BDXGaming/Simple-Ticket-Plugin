package com.ticket.handlers;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.ticket.SimpleTicket;
import com.ticket.files.TicketConstants;
import com.ticket.punishment.Punishment;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class BungeeChannelListener implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {

        if (!channel.equals(TicketConstants.BUNGEE_CHANNEL)) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();

        if (subchannel.equals(TicketConstants.PLUGIN_CHANNEL)) {
            short len = in.readShort();
            byte[] msgbytes = new byte[len];
            in.readFully(msgbytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
            try {
                String somedata = msgin.readUTF();

                Bukkit.broadcast(somedata, TicketConstants.TICKET_STAFF_PERM);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(subchannel.equals(TicketConstants.PLUGIN_PUNISHMENT_UPDATE_CHANNEL)){

            new BukkitRunnable(){
                @Override
                public void run() {
                    Punishment.checkPunishedPlayers();
                }
            }.runTaskAsynchronously(SimpleTicket.simpleTicket);
        }
    }
}
