package com.ticket.events;

import com.ticket.files.Ticket;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TicketCloseEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled = false;
    private Ticket ticket;
    private Player closer;

    public TicketCloseEvent(Ticket ticket, Player closer){
        this.ticket = ticket;
        this.closer = closer;
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

    public Player getCloser() {
        return closer;
    }

    public Ticket getTicket() {
        return ticket;
    }
}
