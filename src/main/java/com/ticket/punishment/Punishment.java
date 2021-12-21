package com.ticket.punishment;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.ticket.SimpleTicket;
import com.ticket.files.SimpleTicketConfig;
import com.ticket.files.TicketConstants;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class Punishment {

    private static ArrayList<UUID> playersPunished;
    private OfflinePlayer punishedPlayer;
    private Player staff;
    private int duration;

    /**
     * Create a new punishment instance, adding a new punishment
     * @param player Player
     * @param duration int
     * @param staff Player
     */
    public Punishment(OfflinePlayer player, int duration, Player staff){

        this.punishedPlayer = player;
        this.staff = staff;
        this.duration = duration;

        if(playersPunished == null){
            playersPunished = new ArrayList<>();
        }

        playersPunished.add(player.getUniqueId());
        PunishmentDatabase.punishPlayer(this.punishedPlayer, this.duration, this.staff, SimpleTicketConfig.get().getString("SuggestedReason"));
    }

    /**
     * Create a new punishment instance, adding a new punishment
     * @param player Player
     * @param duration int
     * @param staff Player
     * @param reason String
     */
    public Punishment(OfflinePlayer player, int duration, Player staff, String reason){

        this.punishedPlayer = player;
        this.staff = staff;
        this.duration = duration;

        if(playersPunished == null){
            playersPunished = new ArrayList<>();
        }

        playersPunished.add(player.getUniqueId());
        PunishmentDatabase.punishPlayer(this.punishedPlayer, this.duration, this.staff, reason);
    }

    /**
     * Removes the given player for the list of active punishments
     * @param p Player
     */
    public static void removePunishedPlayer(OfflinePlayer p){
        if(playersPunished != null){
            if(playersPunished.contains(p.getUniqueId())){
                playersPunished.remove(p.getUniqueId());
                PunishmentDatabase.setInactive(p.getUniqueId().toString());
            }
        }
    }

    /**
     * Gets the ArrayList of actively punished players
     * @return playersPunished ArrayList<UUID>
     */
    public static ArrayList<UUID> getPunishedPlayers(){

        if(playersPunished == null){
            playersPunished = new ArrayList<UUID>();
        }
        return playersPunished;
    }

    /**
     * Checks if the given UUID has an active punishment
     * @param p UUID
     * @return boolean
     */
    public static boolean checkForPlayerPunishment(UUID p){
        return playersPunished.contains(p);
    }

    /**
     * Updates the active punishments to match those stored in the database
     */
    public static void checkPunishedPlayers() {
        playersPunished =PunishmentDatabase.getActivePunishments();
    }

    public static void sendPunishmentSync(){
        if(SimpleTicket.statusController.BUNGEE){
            try {
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("Forward"); // So BungeeCord knows to forward it
                out.writeUTF("ALL");
                out.writeUTF(TicketConstants.PLUGIN_PUNISHMENT_UPDATE_CHANNEL); // The channel name to check if this your data

                ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
                DataOutputStream msgout = new DataOutputStream(msgbytes);
                try {
                    msgout.writeUTF("sync"); // You can do anything you want with msgout
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
