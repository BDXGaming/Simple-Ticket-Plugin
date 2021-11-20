package com.ticket.commands;

import com.ticket.files.SimpleTicketConfig;
import com.ticket.punishment.Punishment;
import com.ticket.utils.timeConverters;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
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
                    OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
                    assert p != null;
                    if (!(Punishment.getPunishedPlayers().contains(p.getUniqueId()))) {
                        if (args.length < 1) {
                            StringBuilder reason = new StringBuilder();
                            for(int i=2; i <args.length; i++){
                                reason.append(args[i]);
                            }

                            new Punishment(p, timeConverters.getDuration(args[1]), player, reason.toString());
                            Bukkit.broadcast(ChatColor.GRAY+"["+ChatColor.GREEN+"Simple-Ticket"+ChatColor.GRAY + "] " +ChatColor.RESET+ sender.getName() + ChatColor.GREEN+" ticket-blocked " +ChatColor.RESET+p.getName() + ChatColor.GREEN + " for " + args[1], "ticket.ticket.staff");
                        } else {
                            new Punishment(p, SimpleTicketConfig.get().getInt("Default Duration"), player);
                            Bukkit.broadcast(ChatColor.GRAY+"["+ChatColor.GREEN+"Simple-Ticket"+ChatColor.GRAY + "] " +ChatColor.RESET + sender.getName() + ChatColor.GREEN+" ticket-blocked "+ChatColor.RESET+p.getName() + ChatColor.GREEN + " for "+ timeConverters.getStringDuration(SimpleTicketConfig.get().getInt("Default Duration")), "ticket.ticket.staff");
                        }
                    }else{
                        player.sendMessage(ChatColor.YELLOW+p.getName()+ChatColor.YELLOW+" already cannot open tickets!");
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
