package com.ticket.bungee.commands;

import com.ticket.bungee.punishment.BungeePunishmentDatabase;
import com.ticket.files.TicketConstants;
import com.ticket.utils.MojangPlayerHelper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.scheduler.GroupedThreadFactory;

import java.util.UUID;


public class ClearPlayerHistory extends Command {

    public ClearPlayerHistory() {
        super("cleartickethist","", "thistclear", "clearhist");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer){

            if(sender.hasPermission(TicketConstants.TICKET_HIST_CLEAR_PERM)){
                if(args.length >0){
                    UUID uuid = MojangPlayerHelper.getUniqueId(args[0]);
                    BungeePunishmentDatabase.clearPlayerHistory(uuid);
                    sender.sendMessage(new TextComponent(ChatColor.GREEN + "Ticket Hist Cleared for: " + ChatColor.WHITE + args[0]));
                }
                else{
                    sender.sendMessage(new TextComponent(ChatColor.YELLOW + "Please specify a player"));
                }
            }else{
                sender.sendMessage(new TextComponent(ChatColor.RED + "You don't have the permissions to use this command!"));

            }
        }
    }
}

