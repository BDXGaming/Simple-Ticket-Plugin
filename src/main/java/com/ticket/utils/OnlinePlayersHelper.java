package com.ticket.utils;

import com.ticket.SimpleTicketBungee;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.Collection;

public class OnlinePlayersHelper {

    /**
     * Gets an arraylist of all currently online players
     * @return ArrayList<ProxiedPlayer>
     */
    public static Collection<ProxiedPlayer> getOnlinePlayers(){
        return SimpleTicketBungee.simpleTicketBungee.getProxy().getPlayers();
    }

    /**
     * Gets a list of the online players
     * @return Object[]
     */
    public static Object[] getOnlinePlayersList(){
        return ProxyServer.getInstance().getPlayers().toArray();
    }

    /**
     * Gets the names of all online players
     * @return ArrayList<String>
     */
    public static ArrayList<String> getOnlinePlayerNames(){
        Collection<ProxiedPlayer> players = getOnlinePlayers();
        ArrayList<String> onlinePlayerNames = new ArrayList<>();
        for(ProxiedPlayer p: players){
            onlinePlayerNames.add(p.getName());
        }
        return onlinePlayerNames;
    }

}
