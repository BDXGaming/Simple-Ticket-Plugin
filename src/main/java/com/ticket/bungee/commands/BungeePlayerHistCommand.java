package com.ticket.bungee.commands;

import com.ticket.bungee.files.TicketPlayer;
import com.ticket.bungee.punishment.BungeePunishmentDatabase;
import com.ticket.files.TicketConstants;
import com.ticket.utils.MojangPlayerHelper;
import com.ticket.utils.OnlinePlayersHelper;
import com.ticket.utils.TabCompleteHelper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.Collections;

public class BungeePlayerHistCommand extends Command implements TabExecutor {

    public BungeePlayerHistCommand(){
        super("thist", "","thistory");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender.hasPermission(TicketConstants.TICKET_STAFF_PERM)){

            if(args.length >=0){
                try {
                    TicketPlayer p = MojangPlayerHelper.getPlayer(MojangPlayerHelper.getUniqueId(args[0]));
                    ArrayList<String> hist = BungeePunishmentDatabase.getPlayerHist(p.getUniqueId());
                    assert hist != null;
                    Collections.reverse(hist);
                    StringBuilder strHist = new StringBuilder();
                    for (String h : hist) {
                        strHist.append(h).append("\n \n");
                    }
                    sender.sendMessage(new TextComponent( ChatColor.YELLOW +""+ ChatColor.BOLD+ "\nSimpleTicket Punishment History for: " + ChatColor.RESET +ChatColor.WHITE+ p.getName()+"\n \n" +ChatColor.RESET+ ChatColor.GREEN + strHist));


                }catch (ArrayIndexOutOfBoundsException e){
                    sender.sendMessage( new TextComponent(ChatColor.YELLOW + "Please use the following format <player>"));
                }

            }else{
                sender.sendMessage( new TextComponent(ChatColor.YELLOW + "Please use the following format /thist <player>"));
            }

        }else{
            sender.sendMessage(new TextComponent(ChatColor.RED+"You do not have the required permissions to use this command!"));
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
