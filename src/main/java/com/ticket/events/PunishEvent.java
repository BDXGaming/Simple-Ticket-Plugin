package com.ticket.events;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PunishEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled = false;
    private OfflinePlayer offlinePlayer;
    private Player executor;
    private String reason;
    private int duration;
    private boolean isModified = false;

    public PunishEvent(OfflinePlayer p, Player executor, String reason, int duration){
        this.offlinePlayer = p;
        this.executor = executor;
        this.reason = reason;
        this.duration = duration;
    }

    public PunishEvent(OfflinePlayer p, Player executor, int duration){
        this.offlinePlayer = p;
        this.executor = executor;
        this.reason = "";
        this.duration = duration;
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

    public OfflinePlayer getOfflinePlayer() {
        return offlinePlayer;
    }

    public void setOfflinePlayer(OfflinePlayer offlinePlayer) {
        this.offlinePlayer = offlinePlayer;
        this.isModified = true;
    }

    public Player getExecutor() {
        return executor;
    }

    public void setExecutor(Player executor) {
        this.executor = executor;
        this.isModified = true;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
        this.isModified = true;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
        this.isModified = true;
    }
    public boolean isModified(){
        return this.isModified;
    }
}