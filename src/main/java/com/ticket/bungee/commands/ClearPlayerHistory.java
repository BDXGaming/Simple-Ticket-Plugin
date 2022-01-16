package com.ticket.bungee.commands;

import com.ticket.bungee.punishment.BungeePunishmentDatabase;
import com.ticket.files.TicketConstants;
import com.ticket.utils.MojangPlayerHelper;
import com.ticket.utils.OnlinePlayersHelper;
import com.ticket.utils.TabCompleteHelper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.UUID;


public class ClearPlayerHistory extends Command implements TabExecutor {

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

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {

        ArrayList<String> completions = new ArrayList<>();

        if(sender.hasPermission(TicketConstants.TICKET_STAFF_PERM)){
            return TabCompleteHelper.copyPartialMatches(args[0], OnlinePlayersHelper.getOnlinePlayerNames(), completions);
        }

        return completions;
    }
}

