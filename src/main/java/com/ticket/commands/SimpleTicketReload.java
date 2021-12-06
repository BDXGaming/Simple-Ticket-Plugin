package com.ticket.commands;

import com.ticket.SimpleTicket;
import com.ticket.files.SimpleTicketConfig;
import com.ticket.files.TicketConstants;
import com.ticket.punishment.PunishmentDatabase;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.Arrays;

public class SimpleTicketReload implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;
            if(player.hasPermission(TicketConstants.TICKET_RELOAD_PERM)){
                sender.sendMessage(ChatColor.GRAY+"["+ChatColor.GREEN+"Simple-Ticket"+ChatColor.GRAY + "] " +ChatColor.RESET+ChatColor.YELLOW+"Starting Reload");
                SimpleTicketConfig.reload();
                PunishmentDatabase.closeConn();
                SimpleTicket.statusController.reload();
                try {
                    PunishmentDatabase.createDatabaseConnection();
                } catch (SQLException throwables) {
                    Bukkit.getLogger().warning(Arrays.toString(throwables.getStackTrace()));
                }
                sender.sendMessage(ChatColor.GRAY+"["+ChatColor.GREEN+"Simple-Ticket"+ChatColor.GRAY + "] " +ChatColor.RESET+ChatColor.GREEN+"Reload Complete");
                Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY+"["+ChatColor.GREEN+"Simple-Ticket"+ChatColor.GRAY + "] " +ChatColor.RESET+ChatColor.GREEN+"Reload Complete");
                return true;
            }
        }
        else if(sender instanceof ConsoleCommandSender){
            sender.sendMessage(ChatColor.GRAY+"["+ChatColor.GREEN+"Simple-Ticket"+ChatColor.GRAY + "] " +ChatColor.RESET+ChatColor.YELLOW+"Starting Reload");
            SimpleTicketConfig.reload();
            PunishmentDatabase.closeConn();
            SimpleTicket.statusController.reload();
            try {
                PunishmentDatabase.createDatabaseConnection();
            } catch (SQLException throwables) {
                Bukkit.getLogger().warning(Arrays.toString(throwables.getStackTrace()));
            }
            sender.sendMessage(ChatColor.GRAY+"["+ChatColor.GREEN+"Simple-Ticket"+ChatColor.GRAY + "] " +ChatColor.RESET+ChatColor.GREEN+"Reload Complete");
            return true;
        }
        return false;
    }
}
