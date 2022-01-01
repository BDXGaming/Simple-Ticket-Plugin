package com.ticket.utils;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeHelper {

    /**
     * When invoked this method sends a message to all online players with the given permission
     * @param message String
     * @param permission String
     */
    public static void broadcast(String message, String permission){

        for(ProxiedPlayer p: ProxyServer.getInstance().getPlayers()){
            if(p.hasPermission(permission)){
                p.sendMessage(new TextComponent(message));
            }
        }

    }
}
