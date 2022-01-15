package com.ticket.tabcomplete;

import com.ticket.SimpleTicket;
import com.ticket.utils.PlayerList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class SimpleTicketPunishCommandTabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
        ArrayList<String> tab = new ArrayList<>();

        if(args.length ==1){
            tab.addAll(PlayerList.getOnlinePlayers());
            return tab;
        }

        if(args.length == 2){
            if (!args[1].equals("")) {
                for (String dur : SimpleTicket.statusController.SUGGESTED_DURATIONS) {
                    if (dur.startsWith(args[1].toLowerCase())) {
                        tab.add(dur);
                    }
                }
            }
            else{
                tab.addAll(SimpleTicket.statusController.SUGGESTED_DURATIONS);
            }
            return tab;
        }

        else if(args.length ==3){
            tab.add(SimpleTicket.statusController.SUGGESTED_REASON);
            return tab;
        }

        return tab;
    }
}
