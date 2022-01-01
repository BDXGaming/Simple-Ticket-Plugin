package com.ticket.bungee.punishment;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.ticket.SimpleTicketBungee;
import com.ticket.bungee.config.SimpleTicketBungeeConfig;
import com.ticket.bungee.files.TicketPlayer;
import com.ticket.files.TicketConstants;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class BungeePunishment {

    private static ArrayList<UUID> playersPunished;
    private TicketPlayer punishedPlayer;
    private ProxiedPlayer staff;
    private int duration;

    /**
     * Create a new punishment instance, adding a new punishment
     * @param player Player
     * @param duration int
     * @param staff Player
     */
    public BungeePunishment(TicketPlayer player, int duration, ProxiedPlayer staff){

        this.punishedPlayer = player;
        this.staff = staff;
        this.duration = duration;

        if(playersPunished == null){
            playersPunished = new ArrayList<>();
        }

        playersPunished.add(player.getUniqueId());
        BungeePunishmentDatabase.punishPlayer(this.punishedPlayer, this.duration, this.staff, SimpleTicketBungeeConfig.get().getString("SuggestedReason"));
    }

    /**
     * Create a new punishment instance, adding a new punishment
     * @param player Player
     * @param duration int
     * @param staff Player
     * @param reason String
     */
    public BungeePunishment(TicketPlayer player, int duration, ProxiedPlayer staff, String reason){

        this.punishedPlayer = player;
        this.staff = staff;
        this.duration = duration;

        if(playersPunished == null){
            playersPunished = new ArrayList<>();
        }

        playersPunished.add(player.getUniqueId());
        BungeePunishmentDatabase.punishPlayer(this.punishedPlayer, this.duration, this.staff, reason);
    }

    /**
     * Removes the given player for the list of active punishments
     * @param p Player
     */
    public static void removePunishedPlayer(TicketPlayer p){
        if(playersPunished != null){
            if(playersPunished.contains(p.getUniqueId())){
                playersPunished.remove(p.getUniqueId());
                BungeePunishmentDatabase.setInactive(p.getUniqueId().toString());
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
        playersPunished =BungeePunishmentDatabase.getActivePunishments();
    }


}
