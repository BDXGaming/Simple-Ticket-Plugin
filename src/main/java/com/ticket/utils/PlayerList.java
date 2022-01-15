package com.ticket.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.ArrayList;

public class PlayerList {

    public static ArrayList<String> getOnlinePlayers(){
        ArrayList<String> onlinePlayers = new ArrayList<>();

        for(Player p: Bukkit.getOnlinePlayers()){
            onlinePlayers.add(p.getName());
        }
        return onlinePlayers;
    }
}
