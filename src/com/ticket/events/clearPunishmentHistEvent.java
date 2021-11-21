package com.ticket.events;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class clearPunishmentHistEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled = false;
    private String playername;
    private OfflinePlayer offlinePlayer;
    private Player player; //The player that cleared the hist

    /**
     * Constructs a new clearPunishmentHistEvent
     * Is called when a player clears a player's history
     * @param p OfflinePlayer
     * @param player Player
     */
    public clearPunishmentHistEvent(OfflinePlayer p, Player player){
        this.playername = p.getName();
        this.offlinePlayer = p;
        this.player = player;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public String getPlayername() {
        return playername;
    }

    public OfflinePlayer getOfflinePlayer() {
        return offlinePlayer;
    }

    public Player getPlayer() {
        return player;
    }
}