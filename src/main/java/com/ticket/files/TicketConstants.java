package com.ticket.files;

public class TicketConstants {

    /** Permission required to use ticket commands for general users (open ticket, respond to ticket, etc) */
    public static final String TICKET_PERM = "ticket.ticket";

    /** Permission required to perform staff ticket actions*/
    public static final String TICKET_STAFF_PERM = "ticket.ticket.staff";

    /** Permission required to reload the plugin*/
    public static final String TICKET_RELOAD_PERM = "ticket.reload";

    /** Permission required to clear a player's ticket moderation history*/
    public static final String TICKET_HIST_CLEAR_PERM = "ticket.ticket.staff.clearhist";

    /** The BungeeCord plugin message Channel*/
    public static final String BUNGEE_CHANNEL = "BungeeCord";

    /** The Plugin message Channel */
    public static final String PLUGIN_CHANNEL = "SimpleTicket";

    /** The channel where a sync request is sent to update the punishments of all servers with online members*/
    public static final String PLUGIN_PUNISHMENT_UPDATE_CHANNEL = "SimpleTicket:Punishment";

}
