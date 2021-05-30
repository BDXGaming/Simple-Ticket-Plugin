package com.ticket;
import com.ticket.commands.SimpleTicketNewTicket;
import com.ticket.commands.SimpleTicketReload;
import com.ticket.commands.SimpleTicketsTicket;
import com.ticket.files.SimpleTicketConfig;
import com.ticket.tabcomplete.SimpleTicketTabComplete;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;


public class SimpleTicket extends JavaPlugin{

    @Override
    public void onEnable(){

        SimpleTicketConfig.setup();
        SimpleTicketConfig.get().addDefault("FirstMessage"," \n§e Ticket Message History\n§e Welcome to your ticket, to reply type /tr then your message!");
        SimpleTicketConfig.get().options().copyDefaults(true);
        SimpleTicketConfig.save();

        SimpleTicketNewTicket nt = new SimpleTicketNewTicket();
        SimpleTicketsTicket tic = new SimpleTicketsTicket();
        getCommand("ticket").setExecutor(tic);
        getCommand("tickets").setExecutor(tic);
        getCommand("newticket").setExecutor(nt);
        getCommand("treload").setExecutor(new SimpleTicketReload());
        getCommand("ticket").setTabCompleter(new SimpleTicketTabComplete());
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN +"[Simple Ticket] Plugin is enabled");

    }//Closes the onEnable


    @Override
    public void onDisable(){
        getServer().getConsoleSender().sendMessage(ChatColor.RED +"[Simple Ticket] Plugin is disabled");
    }//Closes onDisable
}
