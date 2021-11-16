package com.ticket.punishment;

import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.UUID;

public class Punishment {

    private static ArrayList<UUID> playersPunished;
    private Player punishedPlayer;
    private Player staff;
    private int duration;

    /**
     * Create a new punishment instance, adding a new punishment
     * @param player Player
     * @param duration int
     * @param staff Player
     */
    public Punishment(Player player, int duration, Player staff){

        this.punishedPlayer = player;
        this.staff = staff;
        this.duration = duration;

        if(playersPunished == null){
            playersPunished = new ArrayList<>();
        }

        playersPunished.add(player.getUniqueId());
        PunishmentDatabase.punishPlayer(this.punishedPlayer, this.duration, this.staff);
    }

    /**
     * Removes the given player for the list of active punishments
     * @param p Player
     */
    public static void removePunishedPlayer(Player p){
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
     * Updates the active punishments to match those stored in the database
     */
    public static void checkPunishedPlayers() {
        playersPunished =PunishmentDatabase.getActivePunishments();
    }

}
