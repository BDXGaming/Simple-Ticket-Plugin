package com.ticket.files;
import org.bukkit.entity.Player;
import java.util.ArrayList;

public class Ticket{

    private Integer num;
    private Player owner;
    private Boolean claimed;
    private Player staffClaimer;
    private static ArrayList<Ticket> tickets = new ArrayList<>();
    private static ArrayList<Player> players_with_tickets = new ArrayList<>();
    private static ArrayList<Player> staff_with_tickets = new ArrayList<>();
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

    public String toString(){
        return num.toString();
    }

    public Integer getNum(){
        return this.num;
    }

    public Player getOwner(){
        return this.owner;
    }

    public Boolean isClaimed(){
        return claimed;
    }

    public void claimTicket(Player player){
        claimed = true;
        staffClaimer = player;
        staff_with_tickets.add(player);
    }

    public void addmsg(String msg){
        ticketLog += "\n "+msg;
    }

    public String getMsgLog(){
        return ticketLog+ "\n ";
    }

    public Player getStaffClaimer(){
        return staffClaimer;
    }

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

    public void deleteTicket(){
        staff_with_tickets.remove(staffClaimer);
        removeTicket(this);
    }

    public static Boolean hasTicket(Player p){
        for(Player pl: players_with_tickets){
            if(pl.getUniqueId().equals(p.getUniqueId())){
                return true;
            }
        }
        return false;
    }

    public static Ticket getTicket(Player player){
        for(Ticket t: tickets){
            if(t.getOwner().getUniqueId().equals(player.getUniqueId())){
                return t;
            }
        }
        return null;
    }

    public static Ticket getTicket(String num){
        for(Ticket t: tickets){
            if(String.valueOf(t.getNum()).equals(num)){
                return t;
            }
        }
        return null;
    }

    public static String getAllTickets(){
        String msg = "§eCurrent Tickets:";
        for (Ticket t: tickets){
            msg = msg + ("\n §d- Ticket-"+t.getNum() + " :Owner: "+ t.getOwner().getName());
        }
        return msg;
    }

    public static void removeTicket(Ticket t){
        tickets.remove(t);
        Player remove = null;
        Boolean test = false;

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

    public static ArrayList<Player> getStaffWithTickets(){
        return staff_with_tickets;
    }

    public static ArrayList<Ticket> getTickets(){
        return tickets;
    }


}
