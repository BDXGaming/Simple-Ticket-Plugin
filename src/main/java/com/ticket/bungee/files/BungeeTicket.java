package com.ticket.bungee.files;

import com.ticket.bungee.config.SimpleTicketBungeeConfig;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import java.util.ArrayList;

public class BungeeTicket {

    private final Integer num;
    private final ProxiedPlayer owner;
    private Boolean claimed;
    private ProxiedPlayer staffClaimer = null;
    private static final ArrayList<BungeeTicket> tickets = new ArrayList<>();
    private static final ArrayList<ProxiedPlayer> players_with_tickets = new ArrayList<>();
    private static final ArrayList<ProxiedPlayer> staff_with_tickets = new ArrayList<>();
    private static int ticket_count = 0;
    private String ticketLog = SimpleTicketBungeeConfig.get().getString("FirstMessage");

    public BungeeTicket(ProxiedPlayer player){
        ticket_count += 1;
        this.num = ticket_count;
        this.owner = player;
        players_with_tickets.add(player);
        this.claimed = false;
        tickets.add(this);
    }

    /**
     * When .toString() is called instance returns the ticket number as a String
     * @return String
     */
    public String toString(){
        return num.toString();
    }

    /**
     * Gets the number of the ticket
     * @return Integer
     */
    public Integer getNum(){
        return this.num;
    }

    /**
     * Gets the owner of the ticket, based on the ticket instance
     * @return Player
     */
    public ProxiedPlayer getOwner(){
        return this.owner;
    }

    /**
     * Checks if the ticket instance is claimed
     * @return boolean
     */
    public Boolean isClaimed(){
        return claimed;
    }

    /**
     * The given player claims the ticket
     * @param player Player
     */
    public void claimTicket(ProxiedPlayer player){
        claimed = true;
        staffClaimer = player;
        staff_with_tickets.add(player);
    }

    /**
     * Adds a message to the ticket message history
     * @param msg String
     */
    public void addmsg(String msg){
        ticketLog += "\n "+msg;
    }

    /**
     * Gets the ticket message history
     * @return String
     */
    public String getMsgLog(){ return "\n"+ ticketLog+ "\n "; }

    /**
     * Gets the user with the staff permission who claimed the given ticket
     * @return Player
     */
    public ProxiedPlayer getStaffClaimer(){
        return staffClaimer;
    }

    /**
     * Checks if the given player has a claimed ticket
     * @param p Player
     * @return boolean
     */
    public static Boolean hasClaim(ProxiedPlayer p){
        for (BungeeTicket t: tickets){
            if(t.isClaimed()){
                if(t.getStaffClaimer().getUniqueId().equals(p.getUniqueId())){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets the ticket that the player has claimed, if exists
     * @param p Player
     * @return Ticket || null
     */
    public static BungeeTicket getClaim(ProxiedPlayer p){
        for (BungeeTicket t: tickets){
            if(t.isClaimed()){
                if(t.getStaffClaimer().getUniqueId().equals(p.getUniqueId())){
                    return t;
                }
            }
        }
        return null;
    }

    /**
     * Removes a ticket
     */
    public void deleteTicket(){
        staff_with_tickets.remove(staffClaimer);
        removeTicket(this);
    }

    /**
     * Checks if the given player has an open ticket
     * @param p Player
     * @return boolean
     */
    public static Boolean hasTicket(ProxiedPlayer p){
        for(ProxiedPlayer pl: players_with_tickets){
            if(pl.getUniqueId().equals(p.getUniqueId())){
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the ticket instance with the given ticket owner
     * @param player Player
     * @return Ticket
     */
    public static BungeeTicket getTicket(ProxiedPlayer player){
        for(BungeeTicket t: tickets){
            if(t.getOwner().getUniqueId().equals(player.getUniqueId())){
                return t;
            }
        }
        return null;
    }

    /**
     * Gets the ticket instance with the given number
     * @param num String
     * @return Ticket
     */
    public static BungeeTicket getTicket(String num){
        for(BungeeTicket t: tickets){
            if(String.valueOf(t.getNum()).equals(num)){
                return t;
            }
        }
        return null;
    }

    /**
     * Gets the ticket instance with the given number
     * @param num int
     * @return Ticket
     */
    public static BungeeTicket getTicket(int num){
        for(BungeeTicket t: tickets){
            if(String.valueOf(t.getNum()).equals(String.valueOf(num))){
                return t;
            }
        }
        return null;
    }

    /**
     * Gets the formatted message of all open tickets
     * @return String
     */
    public static String getAllTickets(){
        StringBuilder msg = new StringBuilder(ChatColor.translateAlternateColorCodes('&',"\n&e&lCurrent Tickets \n \n"));

        if(tickets.isEmpty()){
            msg.append(ChatColor.GREEN).append("No Open Tickets!");
        }
        for (BungeeTicket t: tickets){

            if(t.getStaffClaimer() != null){
                msg.append(ChatColor.WHITE).append(" - ").append(ChatColor.LIGHT_PURPLE).append(t.getOwner().getName()).append("'s ").append(ChatColor.WHITE).append("Ticket-").append(t.getNum()).append(ChatColor.LIGHT_PURPLE).append(" Claimed by ").append(ChatColor.YELLOW).append(t.getStaffClaimer().getDisplayName());

            } else{
                msg.append(ChatColor.WHITE).append(" - ").append(ChatColor.LIGHT_PURPLE).append(t.getOwner().getName()).append("'s ").append(ChatColor.WHITE).append("Ticket-").append(t.getNum());
            }

        }

        msg.append("\n \n ");

        return msg.toString();
    }

    /**
     * Removes the given ticket
     * @param t Ticket
     */
    public static void removeTicket(BungeeTicket t){
        tickets.remove(t);
        ProxiedPlayer remove = null;
        boolean test = false;

        for(ProxiedPlayer pl: players_with_tickets){
            if(pl.getUniqueId().equals(t.getOwner().getUniqueId())){
                remove = pl;
                test = true;
            }
        }
        if(test){
            players_with_tickets.remove(remove);
        }
    }

    /**
     * Returns an arraylist of all staff users currently with open tickets
     * @return Arraylist
     */
    public static ArrayList<ProxiedPlayer> getStaffWithTickets(){
        return staff_with_tickets;
    }

    /**
     * Gets the arraylist of all currently open tickets
     * @return ArrayList<Ticket>
     */
    public static ArrayList<BungeeTicket> getTickets(){
        return tickets;
    }


}
