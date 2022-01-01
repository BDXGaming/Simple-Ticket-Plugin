package com.ticket;

import com.ticket.commands.*;
import com.ticket.files.SimpleTicketConfig;
import com.ticket.files.StatusController;
import com.ticket.files.TicketConstants;
import com.ticket.handlers.bungeeChannelListener;
import com.ticket.punishment.Punishment;
import com.ticket.punishment.PunishmentDatabase;
import com.ticket.tabcomplete.SimpleTicketPunishCommandTabComplete;
import com.ticket.tabcomplete.SimpleTicketTabComplete;
import com.ticket.utils.LoggerControl;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import java.sql.SQLException;

public class SimpleTicket extends JavaPlugin {

    public static SimpleTicket simpleTicket;
    public static StatusController statusController;
    public static Chat chat;

    @Override
    public void onEnable(){

        //Sets the logging instance to the Spigot logger
        LoggerControl.setLogger(this.getLogger());

        //Sets the instance of the plugin to the running plugin
        simpleTicket = this;

        //This sets up the config file the first time that the plugin is run
        SimpleTicketConfig.setup();
        statusController = new StatusController();

        //Gets the Vault Chat dependency
        if(statusController.VAULT){
            try{
                RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
                if (rsp != null) {
                    chat = rsp.getProvider();
                    Bukkit.getConsoleSender().sendMessage("[Simple-Ticket]: Vault has been loaded!");
                }
            }catch (NoClassDefFoundError e){
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[SimpleTicket]: Vault not found!");
                statusController.VAULT = false;
            }

        }

        //Checks if the server is connected to a BungeeCord Proxy (Or is configured to)
        checkBungeeCordStatus();

        //Creates a database if it does not exist
        try { PunishmentDatabase.createDatabaseConnection(); } catch (SQLException throwables) { LoggerControl.warning(throwables.toString()); }

        //Checks for users who need to be removed from the active moderations list
        Punishment.checkPunishedPlayers();

        //The classes that are used in the commandExecuters
        SimpleTicketNewTicket nt = new SimpleTicketNewTicket();
        SimpleTicketsTicket tic = new SimpleTicketsTicket();

        //Register Plugin message channels
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, TicketConstants.BUNGEE_CHANNEL);
        this.getServer().getMessenger().registerIncomingPluginChannel(this, TicketConstants.BUNGEE_CHANNEL, new bungeeChannelListener());

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

        //Removes the BungeeMessageChannel listeners and senders
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);

        //Prints a message to console when the plugin is disabled
        getServer().getConsoleSender().sendMessage(ChatColor.RED +"[Simple Ticket] Plugin is disabled");

    }//Closes onDisable

    private void checkBungeeCordStatus()
    {
        if (!getServer().spigot().getConfig().getConfigurationSection("settings").getBoolean( "settings.bungeecord" ) )
        {
            statusController.BUNGEE = false;
        }
    }
}
