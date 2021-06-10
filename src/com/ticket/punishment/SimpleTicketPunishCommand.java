package com.ticket.punishment;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SimpleTicketPunishCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;

            if (player.hasPermission("ticket.ticket.staff")){
                if(args.length >=1) {
                    Player p = Bukkit.getPlayer(args[0]);
                    assert p != null;
                    if (!(Punishment.getPunishedPlayers().contains(p.getUniqueId()))) {
                        if (args.length > 1) {
                            Punishment punishment = new Punishment(p, Integer.parseInt(args[1]), player);
                            player.sendMessage(p.getDisplayName() + ChatColor.GREEN + " has been blocked from opening tickets for " + args[1]);
                        } else {
                            Punishment punishment = new Punishment(p, 3600, player);
                            player.sendMessage(p.getDisplayName() + ChatColor.GREEN + " has been blocked from opening tickets for 3600");
                        }
                    }else{
                        player.sendMessage(ChatColor.YELLOW+p.getDisplayName()+ChatColor.YELLOW+" already cannot open tickets!");
                    }
                }
                else{
                    player.sendMessage(ChatColor.YELLOW + "Please use the following format /"+label+" <player> <duration>");
                }
                return true;
            }
            else{
                player.sendMessage(ChatColor.RED +"You do not have the perms to use this command!");
            }
        }
        else{
            sender.sendMessage(ChatColor.GREEN +"This command is not yet complete for console senders!");
        }


        return false;
    }
}
