package com.ticket.tabcomplete;

import com.ticket.commands.Ticket;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleTicketTabComplete implements TabCompleter {

    private String[] subcommands = {"claim", "close", "history"};

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args){
        if(cmd.getName().equalsIgnoreCase("ticket")){
            ArrayList<String> tab = new ArrayList<>();
            if(sender instanceof Player){
                if(sender.hasPermission("ticket.ticket.staff")) {
                    if (args.length == 1) {
                        if (!args[0].equals("")) {
                            for (String scmd : subcommands) {
                                if (scmd.startsWith(args[0].toLowerCase())) {
                                    tab.add(scmd);
                                }
                            }
                        } else {
                            tab.addAll(Arrays.asList(subcommands));
                        }
                        return tab;
                    }

                    if (args.length == 2) {

                        if ((args[0].equalsIgnoreCase("claim"))|| args[0].equalsIgnoreCase("close") || args[0].equalsIgnoreCase("history") || args[0].equalsIgnoreCase("hist")){
                            for (Ticket ticket : Ticket.getTickets()) {
                                tab.add(ticket.getNum().toString());
                            }
                        }
                        return tab;
                    }
                }
            }

            return tab;
        }
        return null;
    }
}
