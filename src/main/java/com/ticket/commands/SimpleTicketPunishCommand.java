package com.ticket.commands;

import com.ticket.events.PunishEvent;
import com.ticket.files.SimpleTicketConfig;
import com.ticket.files.TicketConstants;
import com.ticket.punishment.Punishment;
import com.ticket.utils.ChatHelper;
import com.ticket.utils.TimeConverters;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SimpleTicketPunishCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;

            if (player.hasPermission(TicketConstants.TICKET_STAFF_PERM)){
                if(args.length >=1) {
                    OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
                    assert p != null;
                    if (!(Punishment.getPunishedPlayers().contains(p.getUniqueId()))) {
                        if (args.length > 1) {
                            StringBuilder reason = new StringBuilder();
                            for(int i=2; i <args.length; i++){
                                reason.append(args[i]).append(" ");
                            }
                            String modReason = reason.toString();
                            int time = TimeConverters.getDuration(args[1]);
                            PunishEvent event = new PunishEvent(p, player, reason.toString(), TimeConverters.getDuration(args[1]));
                            Bukkit.getPluginManager().callEvent(event);

                            if(!event.isCancelled()){

                                if(event.isModified()){
                                    p = event.getOfflinePlayer();
                                    player = event.getExecutor();
                                    time = event.getDuration();
                                    modReason = event.getReason();
                                }

                                new Punishment(p, time, player, modReason);
                                Punishment.sendPunishmentSync();
                                ChatHelper.broadcast(ChatColor.GRAY+"["+ChatColor.GREEN+"Simple-Ticket"+ChatColor.GRAY + "] " +ChatColor.RESET+ sender.getName() + ChatColor.GREEN+" ticket-blocked " +ChatColor.RESET+p.getName() + ChatColor.GREEN + " for " + args[1]);
                            }


                        } else {

                            int time = SimpleTicketConfig.get().getInt("Default Duration");
                            PunishEvent event = new PunishEvent(p, player, time);
                            Bukkit.getPluginManager().callEvent(event);

                            if(!event.isCancelled()){

                                if(event.isModified()){
                                    p = event.getOfflinePlayer();
                                    player = event.getExecutor();
                                    time = event.getDuration();
                                }

                                new Punishment(p, time, player);
                                Punishment.sendPunishmentSync();
                                ChatHelper.broadcast(ChatColor.GRAY+"["+ChatColor.GREEN+"Simple-Ticket"+ChatColor.GRAY + "] " +ChatColor.RESET + player.getName() + ChatColor.GREEN+" ticket-blocked "+ChatColor.RESET+p.getName() + ChatColor.GREEN + " for "+ TimeConverters.getStringDuration(SimpleTicketConfig.get().getInt("Default Duration")));
                            }


                        }
                    }else{
                        player.sendMessage(ChatColor.YELLOW+p.getName()+ChatColor.YELLOW+" already cannot open tickets!");
                    }
                }
                else{
                    player.sendMessage(ChatColor.YELLOW + "Please use the following format /"+label+" <player> <duration>");
                }
                return true;
            }
            else{
                player.sendMessage(ChatColor.RED +"You do not have the perms to use this command!");
            }
        }
        else{
            sender.sendMessage(ChatColor.GREEN +"This command is not yet complete for console senders!");
        }


        return false;
    }
}
