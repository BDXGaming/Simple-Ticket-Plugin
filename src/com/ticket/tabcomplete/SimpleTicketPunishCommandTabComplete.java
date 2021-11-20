package com.ticket.tabcomplete;

import com.ticket.SimpleTicket;
import com.ticket.files.SimpleTicketConfig;
import com.ticket.utils.playerList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SimpleTicketPunishCommandTabComplete implements TabCompleter {

    private List<String> SUGGESTED_DURATIONS = SimpleTicketConfig.get().getStringList("SuggestedDurations");

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
        ArrayList<String> tab = new ArrayList<>();

        if(args.length ==1){
            tab.addAll(playerList.getOnlinePlayers());
            return tab;
        }

        if(args.length == 2){
            if (!args[1].equals("")) {
                for (String dur : SUGGESTED_DURATIONS) {
                    if (dur.startsWith(args[1].toLowerCase())) {
                        tab.add(dur);
                    }
                }
            }
            else{
                tab.addAll(SUGGESTED_DURATIONS);
            }
            return tab;
        }

        else if(args.length >=3){
            tab.add("");
            return tab;
        }

        return null;
    }
}
