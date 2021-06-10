package com.ticket.punishment;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
                    Player p = Bukkit.getPlayer(args[0]);
                    Punishment.removePunishedPlayer(p);
                    player.sendMessage(ChatColor.GREEN+p.getDisplayName()+ChatColor.GREEN+" is now able to open tickets!");
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
