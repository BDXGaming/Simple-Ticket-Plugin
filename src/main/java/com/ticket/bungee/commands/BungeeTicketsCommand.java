package com.ticket.bungee.commands;

import com.ticket.bungee.files.BungeeTicket;
import com.ticket.files.TicketConstants;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class BungeeTicketsCommand extends Command {

    public BungeeTicketsCommand(){
        super("tickets", "","listtickets");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender.hasPermission(TicketConstants.TICKET_STAFF_PERM)){
            String message = BungeeTicket.getAllTickets();
            sender.sendMessage(new TextComponent(message));
        }
        else{
            sender.sendMessage(new TextComponent(ChatColor.RED + "You do not have the permissions to use this command!"));
        }
    }
}
