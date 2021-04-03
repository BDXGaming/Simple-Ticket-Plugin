package com.ticket;
import com.ticket.commands.SimpleTicketNewTicket;
import com.ticket.commands.SimpleTicketsTicket;
import com.ticket.commands.Ticket;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;


public class SimpleTicket extends JavaPlugin{

    @Override
    public void onEnable(){
        SimpleTicketNewTicket nt = new SimpleTicketNewTicket();
        SimpleTicketsTicket tic = new SimpleTicketsTicket();
        getCommand("ticket").setExecutor(tic);
        getCommand("tickets").setExecutor(tic);
        getCommand("newticket").setExecutor(nt);
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN +"[Simple Ticket] Plugin is enabled");

    }//Closes the onEnable


    @Override
    public void onDisable(){
        getServer().getConsoleSender().sendMessage(ChatColor.RED +"[Simple Ticket] Plugin is disabled");
    }//Closes onDisable
}
