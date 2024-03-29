package com.ticket.commands;

import com.ticket.files.TicketConstants;
import com.ticket.punishment.PunishmentDatabase;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class ViewActivePunishmentCommands implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender.hasPermission(TicketConstants.TICKET_STAFF_PERM)){

            ArrayList<UUID> playerspunished = PunishmentDatabase.getActivePunishments();

            StringBuilder res = new StringBuilder(ChatColor.YELLOW + "\nActive SimpleTicket Punishments \n \n" + ChatColor.GREEN);

            if(playerspunished != null){
                if(playerspunished.size() < 1){
                    res.append("No active Punishments Found!");
                }else{
                    for(UUID id: playerspunished){
                        OfflinePlayer p = Bukkit.getOfflinePlayer(id);
                        res.append(ChatColor.GREEN).append("  Name: ").append(ChatColor.WHITE).append(p.getName()).append(ChatColor.GREEN).append("\n  UUID: ").append(ChatColor.WHITE).append(id.toString()).append("\n \n");
                    }
                }
            }
            sender.sendMessage(res.toString());
            return true;
        }
        return false;
    }
}
