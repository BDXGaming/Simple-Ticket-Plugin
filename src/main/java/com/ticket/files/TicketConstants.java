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

}
