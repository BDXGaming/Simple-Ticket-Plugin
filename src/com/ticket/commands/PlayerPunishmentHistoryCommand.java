package com.ticket.commands;

import com.ticket.punishment.PunishmentDatabase;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PlayerPunishmentHistoryCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender.hasPermission("ticket.ticket.staff")){

            if(args.length >=0){
                OfflinePlayer p =  Bukkit.getOfflinePlayer(args[0]);

                if(p.getUniqueId() != null){
                    ArrayList<String> hist = PunishmentDatabase.getPlayerHist(p.getUniqueId());
                    assert hist != null;
                    String strHist = "";
                    for(String h: hist){
                        strHist += h+"\n \n";
                    }
                    sender.sendMessage(ChatColor.YELLOW + "\nSimpleTicket Punishment History\n \n"+ChatColor.GREEN +strHist+"\n \n");
                    return true;
                }

            }else{
                sender.sendMessage(ChatColor.YELLOW + "Please use the following format /"+label+" <player>");
                return true;
            }

        }else{
            sender.sendMessage(ChatColor.RED+"You do not have the required permissions to use this command!");
        }

        return false;
    }
}
