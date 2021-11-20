package com.ticket;
import com.ticket.commands.SimpleTicketNewTicket;
import com.ticket.commands.SimpleTicketReload;
import com.ticket.commands.SimpleTicketsTicket;
import com.ticket.files.SimpleTicketConfig;
import com.ticket.commands.PlayerPunishmentHistoryCommand;
import com.ticket.punishment.Punishment;
import com.ticket.punishment.PunishmentDatabase;
import com.ticket.commands.RemovePunishment;
import com.ticket.commands.SimpleTicketPunishCommand;
import com.ticket.commands.ViewActivePunishmentCommands;
import com.ticket.tabcomplete.SimpleTicketPunishCommandTabComplete;
import com.ticket.tabcomplete.SimpleTicketTabComplete;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;


public class SimpleTicket extends JavaPlugin{

    public static SimpleTicket simpleTicket;

    @Override
    public void onEnable(){

        //Sets the instance of the plugin to the running plugin
        simpleTicket = this;

        //Creates a database if it does not exist
        PunishmentDatabase.createDatabase();

        //Checks for users who need to be removed from the active moderations list
        Punishment.checkPunishedPlayers();

        //The classes that are used in the commandExecuters
        SimpleTicketNewTicket nt = new SimpleTicketNewTicket();
        SimpleTicketsTicket tic = new SimpleTicketsTicket();

        //This sets up the config file the first time that the plugin is run
        SimpleTicketConfig.setup();

        //All the commands are assigned executors here
        getCommand("ticket").setExecutor(tic);
        getCommand("tickets").setExecutor(tic);
        getCommand("newticket").setExecutor(nt);
        getCommand("treload").setExecutor(new SimpleTicketReload());
        getCommand("tpunish").setExecutor(new SimpleTicketPunishCommand());
        getCommand("tunpunish").setExecutor(new RemovePunishment());
        getCommand("thist").setExecutor(new PlayerPunishmentHistoryCommand());
        getCommand("viewpunishments").setExecutor(new ViewActivePunishmentCommands());

        //The tab complete for the ticket command
        getCommand("ticket").setTabCompleter(new SimpleTicketTabComplete());
        getCommand("tpunish").setTabCompleter(new SimpleTicketPunishCommandTabComplete());

        //Showing that the plugin is enabled
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN +"[Simple Ticket] Plugin is enabled");

    }//Closes the onEnable


    @Override
    public void onDisable(){

        //Prints a message to console when the plugin is disabled
        getServer().getConsoleSender().sendMessage(ChatColor.RED +"[Simple Ticket] Plugin is disabled");

    }//Closes onDisable

}
