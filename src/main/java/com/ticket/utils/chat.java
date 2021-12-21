package com.ticket.utils;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.ticket.SimpleTicket;
import com.ticket.files.SimpleTicketConfig;
import com.ticket.files.StatusController;
import com.ticket.files.TicketConstants;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class chat {

    public static void broadcast(String message) {
        Bukkit.broadcast(message, TicketConstants.TICKET_STAFF_PERM);

        if(SimpleTicket.statusController.BUNGEE){
            try {
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("Forward"); // So BungeeCord knows to forward it
                out.writeUTF("ALL");
                out.writeUTF(TicketConstants.PLUGIN_CHANNEL); // The channel name to check if this your data

                ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
                DataOutputStream msgout = new DataOutputStream(msgbytes);
                try {
                    msgout.writeUTF(message); // You can do anything you want with msgout
                    msgout.writeShort(123);
                } catch (IOException exception){
                    exception.printStackTrace();
                }

                out.writeShort(msgbytes.toByteArray().length);
                out.write(msgbytes.toByteArray());

                Player p = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
                assert p != null;
                p.sendPluginMessage(SimpleTicket.simpleTicket, TicketConstants.BUNGEE_CHANNEL, out.toByteArray());

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
