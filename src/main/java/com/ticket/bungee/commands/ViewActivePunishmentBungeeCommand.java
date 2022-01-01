package com.ticket.bungee.commands;

import com.ticket.bungee.files.TicketPlayer;
import com.ticket.bungee.punishment.BungeePunishmentDatabase;
import com.ticket.files.TicketConstants;
import com.ticket.punishment.PunishmentDatabase;
import com.ticket.utils.MojangPlayerHelper;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.UUID;

public class ViewActivePunishmentBungeeCommand extends Command {

    public ViewActivePunishmentBungeeCommand() {
        super("viewpunishments", "", "tactivepunishments","tlive");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender.hasPermission(TicketConstants.TICKET_STAFF_PERM)){

            ArrayList<UUID> playerspunished = BungeePunishmentDatabase.getActivePunishments();

            StringBuilder res = new StringBuilder(ChatColor.YELLOW + "\nActive SimpleTicket Punishments \n \n" + ChatColor.GREEN);

            if(playerspunished != null){
                if(playerspunished.size() < 1){
                    res.append("No active Punishments Found!");
                }else{
                    for(UUID id: playerspunished){
                        TicketPlayer p = MojangPlayerHelper.getPlayer(id);
                        res.append(ChatColor.GREEN).append("  Name: ").append(ChatColor.WHITE).append(p.getName()).append(ChatColor.GREEN).append("\n  UUID: ").append(ChatColor.WHITE).append(id.toString()).append("\n \n");
                    }
                }
            }
            sender.sendMessage(new TextComponent(res.toString()));
        }
    }

}
