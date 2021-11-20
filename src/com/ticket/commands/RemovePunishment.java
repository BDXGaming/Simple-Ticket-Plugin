package com.ticket.commands;

import com.ticket.punishment.Punishment;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.TimerTask;

public class RemovePunishment implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player player = (Player) sender;

            if(player.hasPermission("ticket.ticket.staff")){
                if(args.length >= 1){
                    OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
                    if(p == null){
                        sender.sendMessage(ChatColor.YELLOW + "This is not a valid player name!");
                        return true;
                    }
                    if(Punishment.checkForPlayerPunishment(p.getUniqueId())){
                        Punishment.removePunishedPlayer(p);
                        player.sendMessage(ChatColor.GREEN+p.getName()+ChatColor.GREEN+" is now able to open tickets!");
                        Bukkit.broadcast(ChatColor.GRAY+"["+ChatColor.GREEN+"Simple-Ticket"+ChatColor.GRAY + "] " +ChatColor.RESET+ sender.getName() + ChatColor.GREEN+" un-ticket-blocked " +ChatColor.RESET+p.getName(), "ticket.ticket.staff");
                    }
                    else{
                        sender.sendMessage(ChatColor.YELLOW + "This player is not currently being punished!");
                    }

                    return true;

                }else{
                    player.sendMessage(ChatColor.YELLOW +"You must use the following format /"+label+" <player>");
                    return true;
                }
            }else{
                player.sendMessage(ChatColor.RED +"You do not have the permissions to use this command!");
                return true;
            }
        }

        return false;
    }
}
