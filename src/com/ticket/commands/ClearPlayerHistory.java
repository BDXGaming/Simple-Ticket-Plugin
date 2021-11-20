package com.ticket.commands;

import com.ticket.punishment.PunishmentDatabase;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ClearPlayerHistory implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(sender.hasPermission("ticket.ticket.staff.clearhist")){

            if(args.length >0){
                OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
                PunishmentDatabase.clearPlayerHistory(p.getUniqueId());
                sender.sendMessage(ChatColor.GREEN + "Ticket Hist Cleared for: " + ChatColor.WHITE + p.getName());
                return true;
            }
            else{
                sender.sendMessage(ChatColor.YELLOW + "Please specify a player");
                return true;
            }
        }else{
            sender.sendMessage(ChatColor.RED + "You don't have the permissions to use this command!");

        }
        return true;
    }
}
