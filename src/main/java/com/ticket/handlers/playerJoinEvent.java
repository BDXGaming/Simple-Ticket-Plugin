package com.ticket.handlers;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class playerJoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent joinEvent) {

        //Checks if the player that joined is the only player
        if(Bukkit.getServer().getOnlinePlayers().toArray().length <=1){
            //pass
        }

    }

}
