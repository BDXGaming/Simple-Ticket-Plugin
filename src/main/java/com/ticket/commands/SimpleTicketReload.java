package com.ticket.commands;

import com.ticket.files.SimpleTicketConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class SimpleTicketReload implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(player.hasPermission("ticket.reload")){
                SimpleTicketConfig.reload();
                sender.sendMessage(ChatColor.GREEN+ "[Simple Ticket] Config files have been reloaded!");
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+ "[Simple Ticket] Config files have been reloaded!");
                return true;
            }
        }
        else if(sender instanceof ConsoleCommandSender){
            SimpleTicketConfig.reload();
            sender.sendMessage(ChatColor.GREEN+ "[Simple Ticket] Config files have been reloaded!");
            return true;
        }
        return false;
    }
}
