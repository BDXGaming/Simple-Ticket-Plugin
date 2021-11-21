package com.ticket.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import com.ticket.files.Ticket;

public class ticketClaimEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled = false;
    private Ticket ticket;
    private Player claimer;

    public ticketClaimEvent(Ticket ticket, Player claimer){
        this.ticket = ticket;
        this.claimer = claimer;
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

    public Player getClaimer() {
        return claimer;
    }

    public Ticket getTicket() {
        return ticket;
    }
}
