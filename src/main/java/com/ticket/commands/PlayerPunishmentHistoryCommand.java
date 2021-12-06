package com.ticket.commands;

import com.ticket.files.TicketConstants;
import com.ticket.punishment.PunishmentDatabase;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;

public class PlayerPunishmentHistoryCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender.hasPermission(TicketConstants.TICKET_STAFF_PERM)){

            if(args.length >=0){
                try {
                    OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);

                    p.getUniqueId();
                    ArrayList<String> hist = PunishmentDatabase.getPlayerHist(p.getUniqueId());
                    assert hist != null;
                    Collections.reverse(hist);
                    StringBuilder strHist = new StringBuilder();
                    for (String h : hist) {
                        strHist.append(h).append("\n \n");
                    }
                    sender.sendMessage(ChatColor.YELLOW +""+
                            ChatColor.BOLD+ "\nSimpleTicket Punishment History for: " + ChatColor.RESET +ChatColor.WHITE+
                            p.getName()+"\n \n" +ChatColor.RESET+ ChatColor.GREEN + strHist + "\n \n");
                    return true;
                }catch (ArrayIndexOutOfBoundsException e){
                    sender.sendMessage(ChatColor.YELLOW + "Please use the following format /"+label+" <player>");
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
