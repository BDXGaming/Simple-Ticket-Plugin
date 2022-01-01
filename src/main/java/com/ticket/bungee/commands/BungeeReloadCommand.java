package com.ticket.bungee.commands;

import com.ticket.SimpleTicketBungee;
import com.ticket.bungee.config.SimpleTicketBungeeConfig;
import com.ticket.bungee.punishment.BungeePunishmentDatabase;
import com.ticket.files.TicketConstants;
import com.ticket.utils.LoggerControl;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import java.sql.SQLException;
import java.util.Arrays;

public class BungeeReloadCommand extends Command {

    public BungeeReloadCommand(){
        super("trelaod", "", "ticketreload");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if(sender.hasPermission(TicketConstants.TICKET_RELOAD_PERM)){
            sender.sendMessage(new TextComponent(ChatColor.GRAY+"["+ChatColor.GREEN+"Simple-Ticket"+ChatColor.GRAY + "] " +ChatColor.RESET+ChatColor.YELLOW+"Starting Reload"));
            SimpleTicketBungeeConfig.reload();
            BungeePunishmentDatabase.closeConn();
            SimpleTicketBungee.bungeeStatusController.reload();
            try {
                BungeePunishmentDatabase.createDatabaseConnection();
            } catch (SQLException throwables) {
                LoggerControl.warning(Arrays.toString(throwables.getStackTrace()));
            }
            sender.sendMessage(new TextComponent(ChatColor.GRAY+"["+ChatColor.GREEN+"Simple-Ticket"+ChatColor.GRAY + "] " +ChatColor.RESET+ChatColor.GREEN+"Reload Complete"));
            ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(ChatColor.GRAY+"["+ChatColor.GREEN+"Simple-Ticket"+ChatColor.GRAY + "] " +ChatColor.RESET+ChatColor.GREEN+"Reload Complete"));

        }
    }
}
