package com.ticket.commands;

import com.ticket.files.SimpleTicketConfig;
import com.ticket.punishment.Punishment;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SimpleTicketPunishCommand implements CommandExecutor {

    public static int getDuration(String dur){
        if(dur.contains("m")){
            int index = dur.indexOf("m");
            return 60*Integer.parseInt(dur.substring(0,index));
        }
        else if(dur.contains("h") || dur.contains("hr")){
            int index = dur.indexOf("h");
            return 3600*Integer.parseInt(dur.substring(0,index));
        }
        else if(dur.contains("d") || dur.contains("day")){
            int index = dur.indexOf("d");
            return (24*3600)*Integer.parseInt(dur.substring(0,index));
        }
        else{
            return (int)Integer.parseInt(dur);
        }
    }

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
                            Punishment punishment = new Punishment(p, getDuration(args[1]), player);
                            player.sendMessage(p.getDisplayName() + ChatColor.GREEN + " has been blocked from opening tickets for " + args[1]);
                        } else {
                            Punishment punishment = new Punishment(p, SimpleTicketConfig.get().getInt("Default Duration"), player);
                            player.sendMessage(p.getDisplayName() + ChatColor.GREEN + " has been blocked from opening tickets for "+SimpleTicketConfig.get().getInt("Default Duration"));
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
