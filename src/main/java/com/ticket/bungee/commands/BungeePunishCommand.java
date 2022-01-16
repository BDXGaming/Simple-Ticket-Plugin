package com.ticket.bungee.commands;

import com.ticket.SimpleTicketBungee;
import com.ticket.bungee.config.SimpleTicketBungeeConfig;
import com.ticket.bungee.files.TicketPlayer;
import com.ticket.bungee.punishment.BungeePunishment;
import com.ticket.files.TicketConstants;
import com.ticket.utils.*;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;

public class BungeePunishCommand extends Command implements TabExecutor {

    public BungeePunishCommand() {
        super("tpunish", "", "tban", "tblock");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {


        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;

            if (player.hasPermission(TicketConstants.TICKET_STAFF_PERM)) {
                if (args.length >= 1) {
                    TicketPlayer p = MojangPlayerHelper.getPlayer(MojangPlayerHelper.getUniqueId(args[0]));

                    if (!(BungeePunishment.getPunishedPlayers().contains(p.getUniqueId()))) {
                        if (args.length > 1) {
                            StringBuilder reason = new StringBuilder();
                            for (int i = 2; i < args.length; i++) {
                                reason.append(args[i]).append(" ");
                            }
                            String modReason = reason.toString();
                            int time = TimeConverters.getDuration(args[1]);

                            new BungeePunishment(p, time, player, modReason);
                            BungeeHelper.broadcast(ChatColor.GRAY + "[" + ChatColor.GREEN + "Simple-Ticket" + ChatColor.GRAY + "] " + ChatColor.RESET + sender.getName() + ChatColor.GREEN + " ticket-blocked " + ChatColor.RESET + p.getName() + ChatColor.GREEN + " for " + args[1], TicketConstants.TICKET_STAFF_PERM);


                        } else {

                            int time = SimpleTicketBungee.bungeeStatusController.DEFAULT_DURATION;

                            new BungeePunishment(p, time, player);
                            BungeeHelper.broadcast(ChatColor.GRAY + "[" + ChatColor.GREEN + "Simple-Ticket" + ChatColor.GRAY + "] " + ChatColor.RESET + player.getName() + ChatColor.GREEN + " ticket-blocked " + ChatColor.RESET + p.getName() + ChatColor.GREEN + " for " + TimeConverters.getStringDuration(SimpleTicketBungeeConfig.get().getInt("Default Duration")), TicketConstants.TICKET_STAFF_PERM);
                        }

                    } else {
                        player.sendMessage(new TextComponent(ChatColor.YELLOW + p.getName() + ChatColor.YELLOW + " already cannot open tickets!"));
                    }
                } else {
                    player.sendMessage(new TextComponent(ChatColor.YELLOW + "Please use the following format /tblock <player> <duration>"));
                }
            } else {
                sender.sendMessage(new TextComponent(ChatColor.RED + "You do not have the perms to use this command!"));
            }
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {

        ArrayList<String> tab = new ArrayList<>();

        if(args.length ==1){
            tab.addAll(OnlinePlayersHelper.getOnlinePlayerNames());
            return tab;
        }

        if(args.length == 2){
            if (!args[1].equals("")) {
                for (String dur : SimpleTicketBungee.bungeeStatusController.SUGGESTED_DURATIONS) {
                    if (dur.startsWith(args[1].toLowerCase())) {
                        tab.add(dur);
                    }
                }
            }
            else{
                tab.addAll(SimpleTicketBungee.bungeeStatusController.SUGGESTED_DURATIONS);
            }
            return tab;
        }

        else if(args.length ==3){
            tab.add(SimpleTicketBungee.bungeeStatusController.SUGGESTED_REASON);
            return tab;
        }

        return tab;

    }

}


