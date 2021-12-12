package com.ticket;
import com.ticket.commands.*;
import com.ticket.files.SimpleTicketConfig;
import com.ticket.files.StatusController;
import com.ticket.punishment.Punishment;
import com.ticket.punishment.PunishmentDatabase;
import com.ticket.tabcomplete.SimpleTicketPunishCommandTabComplete;
import com.ticket.tabcomplete.SimpleTicketTabComplete;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Arrays;


public class SimpleTicket extends JavaPlugin{

    public static SimpleTicket simpleTicket;
    public static StatusController statusController;

    @Override
    public void onEnable(){

        //Sets the instance of the plugin to the running plugin
        simpleTicket = this;

        //This sets up the config file the first time that the plugin is run
        SimpleTicketConfig.setup();
        statusController = new StatusController();

        //Creates a database if it does not exist
        try { PunishmentDatabase.createDatabaseConnection(); } catch (SQLException throwables) { Bukkit.getLogger().warning(throwables.toString()); }

        //Checks for users who need to be removed from the active moderations list
        Punishment.checkPunishedPlayers();

        //The classes that are used in the commandExecuters
        SimpleTicketNewTicket nt = new SimpleTicketNewTicket();
        SimpleTicketsTicket tic = new SimpleTicketsTicket();

        //All the commands are assigned executors here
        getCommand("ticket").setExecutor(tic);
        getCommand("tickets").setExecutor(tic);
        getCommand("newticket").setExecutor(nt);
        getCommand("treload").setExecutor(new SimpleTicketReload());
        getCommand("tpunish").setExecutor(new SimpleTicketPunishCommand());
        getCommand("tunpunish").setExecutor(new RemovePunishment());
        getCommand("thist").setExecutor(new PlayerPunishmentHistoryCommand());
        getCommand("viewpunishments").setExecutor(new ViewActivePunishmentCommands());
        getCommand("cleartickethist").setExecutor(new ClearPlayerHistory());

        //The tab complete for the ticket command
        getCommand("ticket").setTabCompleter(new SimpleTicketTabComplete());
        getCommand("tpunish").setTabCompleter(new SimpleTicketPunishCommandTabComplete());

        //Showing that the plugin is enabled
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN +"[Simple Ticket] Plugin is enabled");

    }//Closes the onEnable


    @Override
    public void onDisable(){

        //Closes the Database Connection
        PunishmentDatabase.closeConn();

        //Prints a message to console when the plugin is disabled
        getServer().getConsoleSender().sendMessage(ChatColor.RED +"[Simple Ticket] Plugin is disabled");

    }//Closes onDisable

}
