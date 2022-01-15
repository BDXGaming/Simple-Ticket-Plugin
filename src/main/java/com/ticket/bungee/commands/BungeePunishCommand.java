package com.ticket.bungee.commands;

import com.ticket.SimpleTicketBungee;
import com.ticket.bungee.config.SimpleTicketBungeeConfig;
import com.ticket.bungee.files.TicketPlayer;
import com.ticket.bungee.punishment.BungeePunishment;
import com.ticket.files.TicketConstants;
import com.ticket.utils.BungeeHelper;
import com.ticket.utils.MojangPlayerHelper;
import com.ticket.utils.TimeConverters;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class BungeePunishCommand extends Command {

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
}


