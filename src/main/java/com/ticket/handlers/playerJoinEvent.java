package com.ticket.handlers;

import com.ticket.SimpleTicket;
import com.ticket.punishment.Punishment;
import com.ticket.punishment.PunishmentDatabase;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class playerJoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent joinEvent) {

        //Checks if the player that joined is the only player
        if(Bukkit.getServer().getOnlinePlayers().toArray().length <=1){

            //Creates a new runnable which will get and check all the active punishments
            new BukkitRunnable(){
                @Override
                public void run() {
                    Punishment.checkPunishedPlayers();
                }
            }.runTaskAsynchronously(SimpleTicket.simpleTicket);
        }

    }

}
