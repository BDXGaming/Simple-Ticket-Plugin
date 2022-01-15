package com.ticket;

import com.ticket.bungee.commands.*;
import com.ticket.bungee.config.BungeeStatusController;
import com.ticket.bungee.config.SimpleTicketBungeeConfig;
import com.ticket.bungee.handlers.BungeeTabCompleteEvent;
import com.ticket.bungee.punishment.BungeePunishment;
import com.ticket.bungee.punishment.BungeePunishmentDatabase;
import com.ticket.utils.LoggerControl;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;

import java.sql.SQLException;

public class SimpleTicketBungee extends Plugin {

    public static SimpleTicketBungee simpleTicketBungee;
    public static BungeeStatusController bungeeStatusController;

    @Override
    public void onEnable(){

        //Sets the logging instance to the BungeeCord logger
        LoggerControl.setLogger(this.getLogger());

        //Sets the instance
        simpleTicketBungee = this;

        //Creates and loads the config file
        SimpleTicketBungeeConfig.setupBungee();

        //Creates the instance of the status controller
        bungeeStatusController = new BungeeStatusController();

        //Creates the connection to the database
        try {
            BungeePunishmentDatabase.createDatabaseConnection();
        } catch (SQLException throwables) {
            LoggerControl.warning(throwables.toString());
        }

        //Checks the active ticket punishments
        BungeePunishment.checkPunishedPlayers();

        //Register Bungee Commands
        getProxy().getPluginManager().registerCommand(this, new ClearPlayerHistory());
        getProxy().getPluginManager().registerCommand(this, new ViewActivePunishmentBungeeCommand());
        getProxy().getPluginManager().registerCommand(this, new BungeePlayerHistCommand());
        getProxy().getPluginManager().registerCommand(this, new BungeeRemovePunishment());
        getProxy().getPluginManager().registerCommand(this, new BungeeNewTicket());
        getProxy().getPluginManager().registerCommand(this, new BungeePunishCommand());
        getProxy().getPluginManager().registerCommand(this, new BungeeTicketsCommand());
        getProxy().getPluginManager().registerCommand(this, new BungeeTicketCommand());
        getProxy().getPluginManager().registerCommand(this, new BungeeReloadCommand());

        getLogger().info("Plugin has loaded");
        getProxy().getConsole().sendMessage(new TextComponent("SimpleTicket For Bungee"));
    }

    @Override
    public void onDisable(){

        //Close the Database connection whenever the plugin is disabled
        BungeePunishmentDatabase.closeConn();

        //Sends messages to console alerting to plugin disabled
        getProxy().getConsole().sendMessage(new TextComponent("[Simple-Ticket]: Database connection closed!"));
        getProxy().getConsole().sendMessage(new TextComponent("[Simple-Ticket]: Plugin is disabled!"));
    }

}
