package com.ticket.utils;

import com.ticket.SimpleTicket;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerNameHelper {

    public static String getPlayerName(Player p){

        if(SimpleTicket.statusController.VAULT){
            String prefix = SimpleTicket.chat.getPlayerPrefix(p);
            String username = p.getName();
            String suffix = SimpleTicket.chat.getPlayerSuffix(p);
            return ChatColor.translateAlternateColorCodes('&', (prefix + username + suffix));
        }

        return p.getDisplayName();
    }
}
