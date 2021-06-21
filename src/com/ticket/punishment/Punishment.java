package com.ticket.punishment;

import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.UUID;

public class Punishment {

    private static ArrayList<UUID> playersPunished;
    private Player punishedPlayer;
    private Player staff;
    private int duration;


    public Punishment(Player player, int duration, Player staff){

        this.punishedPlayer = player;
        this.staff = staff;
        this.duration = duration;

        System.out.println(duration);

        playersPunished = new ArrayList<UUID>();
        playersPunished.add(player.getUniqueId());


        PunishmentDatabase.punishPlayer(this.punishedPlayer, this.duration, this.staff);

//        Timer timer = new Timer();
//        TimerTask tt = new
//        timer.schedule(tt, duration);

    }

    public static void removePunishedPlayer(Player p){
        if(playersPunished != null){
            if(playersPunished.contains(p.getUniqueId())){
                playersPunished.remove(p.getUniqueId());
                PunishmentDatabase.setInactive(p.getUniqueId().toString());
            }
        }
    }

    public static ArrayList<UUID> getPunishedPlayers(){

        if(playersPunished == null){
            playersPunished = new ArrayList<UUID>();
        }
        return playersPunished;
    }

    public static void checkPunishedPlayers() {
        playersPunished =PunishmentDatabase.getActivePunishments();
    }

}
