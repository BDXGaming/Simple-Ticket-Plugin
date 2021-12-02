package com.ticket.files;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import java.util.ArrayList;

public class Ticket{

    private final Integer num;
    private final Player owner;
    private Boolean claimed;
    private Player staffClaimer = null;
    private static final ArrayList<Ticket> tickets = new ArrayList<>();
    private static final ArrayList<Player> players_with_tickets = new ArrayList<>();
    private static final ArrayList<Player> staff_with_tickets = new ArrayList<>();
    private static int ticket_count = 0;
    private String ticketLog = SimpleTicketConfig.get().getString("FirstMessage");

    public Ticket(Player player){
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
    public Player getOwner(){
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
    public void claimTicket(Player player){
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
    public Player getStaffClaimer(){
        return staffClaimer;
    }

    /**
     * Checks if the given player has a claimed ticket
     * @param p Player
     * @return boolean
     */
    public static Boolean hasClaim(Player p){
        for (Ticket t: tickets){
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
    public static Ticket getClaim(Player p){
        for (Ticket t: tickets){
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
    public static Boolean hasTicket(Player p){
        for(Player pl: players_with_tickets){
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
    public static Ticket getTicket(Player player){
        for(Ticket t: tickets){
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
    public static Ticket getTicket(String num){
        for(Ticket t: tickets){
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
    public static Ticket getTicket(int num){
        for(Ticket t: tickets){
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
        StringBuilder msg = new StringBuilder(ChatColor.BOLD +""+ ChatColor.YELLOW + "\nCurrent Tickets \n \n" + ChatColor.RESET + "");
        for (Ticket t: tickets){

            if(t.getStaffClaimer() != null){
                msg.append("\n   §d- Ticket-").append(t.getNum()).append(" :Owner: ").append(t.getOwner().getName()).append(" :Claimed by: ").append(t.getStaffClaimer().getDisplayName());

            } else{
                msg.append("\n   §d- Ticket-").append(t.getNum()).append(" :Owner: ").append(t.getOwner().getName());
            }

        }

        msg.append("\n \n ");

        return msg.toString();
    }

    /**
     * Removes the given ticket
     * @param t Ticket
     */
    public static void removeTicket(Ticket t){
        tickets.remove(t);
        Player remove = null;
        boolean test = false;

        for(Player pl: players_with_tickets){
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
    public static ArrayList<Player> getStaffWithTickets(){
        return staff_with_tickets;
    }

    /**
     * Gets the arraylist of all currently open tickets
     * @return ArrayList<Ticket>
     */
    public static ArrayList<Ticket> getTickets(){
        return tickets;
    }


}
