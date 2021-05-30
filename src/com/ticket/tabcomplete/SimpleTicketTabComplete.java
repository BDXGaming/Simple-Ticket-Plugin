package com.ticket.tabcomplete;

import com.ticket.commands.SimpleTicketsTicket;
import com.ticket.commands.Ticket;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SimpleTicketTabComplete implements TabCompleter {

    private String[] subcommands = {"claim", "close", "history"};


    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args){
        if(cmd.getName().equalsIgnoreCase("ticket")){
            if (args.length == 1){
                ArrayList<String> tab = new ArrayList<>();
                if(!args[0].equals("")){
                    for(String scmd: subcommands){
                        if(scmd.startsWith(args[0].toLowerCase())){
                            tab.add(scmd);
                        }
                    }
                }
                else{
                    for(String scmd: subcommands){
                        tab.add(scmd);
                    }
                }
                return tab;
            }

            else if(args.length == 2){
                ArrayList<String> tab = new ArrayList<>();
                if(!args[1].equals("")){
                    for (Ticket ticket: Ticket.getTickets()){
                        tab.add(ticket.toString());
                    }
                }
                return tab;
            }

        }
        return null;
    }
}
