package com.ticket.commands;

import com.ticket.events.ticketClaimEvent;
import com.ticket.events.ticketCloseEvent;
import com.ticket.files.Ticket;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Objects;

public class SimpleTicketsTicket implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)){
            sender.sendMessage("Only Players can use this command");
            return true;
        }

        Player player = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("tickets")){
            if(player.hasPermission("ticket.ticket.staff")){
                String message = Ticket.getAllTickets();
                player.sendMessage(message);
                return true;
            }
            else{
                player.sendMessage(ChatColor.RED + "You do not have the permissions to use this command!");
                return true;
            }

        }

        if(cmd.getName().equalsIgnoreCase("ticket")){
            if(player.hasPermission("ticket.ticket")){

                if(player.hasPermission("ticket.ticket.staff")) {

                    if (args.length > 0) {

                        if (args[0].equalsIgnoreCase("claim")) {
                            ArrayList<Player> staff_tickets = Ticket.getStaffWithTickets();
                            if (!staff_tickets.contains(player)) {
                                Ticket t = Ticket.getTicket(args[1]);
                                assert t != null;
                                if (!(t.isClaimed())) {

                                    ticketClaimEvent event = new ticketClaimEvent(t,player);
                                    Bukkit.getPluginManager().callEvent(event);

                                    if(!event.isCancelled()){
                                        t.claimTicket(player);
                                        Bukkit.broadcast(ChatColor.GRAY+"["+ChatColor.GREEN+"Simple-Ticket"+ChatColor.GRAY + "] " +ChatColor.WHITE + player.getName() + ChatColor.GREEN+" claimed Ticket-"+ t.getNum(), "ticket.ticket.staff");
                                    }

                                    return true;
                                } else {
                                    player.sendMessage("§cThis ticket is already claimed by: " + t.getStaffClaimer().getDisplayName());
                                    return true;
                                }
                            } else {
                                player.sendMessage(ChatColor.YELLOW + "You cannot claim more than one ticket at a time!");
                                return true;
                            }
                        }

                        //Closes and deletes the ticket
                        else if (args[0].equalsIgnoreCase("close")) {
                            Ticket t = Ticket.getTicket(args[1]);

                            ticketCloseEvent event = new ticketCloseEvent(t, player);
                            Bukkit.getPluginManager().callEvent(event);

                            if(!event.isCancelled()){
                                player.sendMessage("§c[Ticket-" + args[1] + "]§c§l Deleted");
                                if (t != null) {
                                    t.deleteTicket();
                                }
                            }

                            return true;
                        }

                        //To view all the previous messages sent in the ticket both by staff and the player
                        else if ((args[0].equalsIgnoreCase("history")) || (args[0].equalsIgnoreCase("hist"))) {
                            try {
                                Ticket t = Ticket.getTicket(args[1]);
                                player.sendMessage(t.getMsgLog());
                                return true;
                            } catch (NullPointerException e) {
                                player.sendMessage(ChatColor.RED + "There are no open tickets at this time!");
                                return true;
                            }
                        }
                        else if (Ticket.hasClaim(player)) {
                            try {
                                Ticket t = Ticket.getClaim(player);
                                StringBuilder msg = new StringBuilder("§c[Ticket-" + t.getNum() + "] §a(" + player.getDisplayName() + "§a):§d ");
                                for (int i = 0; i < args.length; i++) {
                                    msg.append(args[i]).append(" ");
                                }
                                Objects.requireNonNull(Bukkit.getPlayer(t.getOwner().getUniqueId())).sendMessage(msg.toString());
                                player.sendMessage(msg.toString());
                                Objects.requireNonNull(Bukkit.getPlayer(t.getOwner().getUniqueId())).spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(msg.toString()));
                                t.addmsg(msg.toString());
                            } catch (ArrayIndexOutOfBoundsException e) {
                                return true;
                            }
                            return true;
                        }
                        else if(Ticket.hasTicket(player)){
                            Ticket t = Ticket.getTicket(player);
                            StringBuilder msg = new StringBuilder("§c[Ticket-" + t.getNum() + "]§a (" + player.getDisplayName() + "§a):§d ");
                            for (int i = 0; i < args.length; i++) {
                                msg.append(args[i]).append(" ");
                            }
                            if (!(t.isClaimed())) {
                                Bukkit.broadcast(msg.toString(), "ticket.ticket.staff");
                                t.addmsg(msg.toString());
                                return true;
                            } else {
                                Player staff = t.getStaffClaimer();
                                staff.sendMessage(msg.toString());
                                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(msg.toString()));
                                t.addmsg(msg.toString());
                                return true;
                            }
                        }
                    }

                    else{
                        return false;
                    }
                }
                else {
                    try {
                        if (Ticket.hasTicket(player)) {
                            Ticket t = Ticket.getTicket(player);
                            StringBuilder msg = new StringBuilder("§c[Ticket-" + t.getNum() + "] §a(" + player.getDisplayName() + "§a):§d ");
                            for (int i = 0; i < args.length; i++) {
                                msg.append(args[i]).append(" ");
                            }
                            if (!(t.isClaimed())) {
                                player.sendMessage(msg.toString());
                                Bukkit.broadcast(msg.toString(), "ticket.ticket.staff");
                                t.addmsg(msg.toString());
                                return true;
                            } else {
                                Player staff = t.getStaffClaimer();
                                staff.sendMessage(msg.toString());
                                player.sendMessage(msg.toString());
                                t.addmsg(msg.toString());
                                return true;
                            }
                        } else {
                            player.sendMessage("§cYou do not have an open ticket!");
                            return true;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED+"[Simple-Ticket]: Error in command processing!");
                        return false;
                    }
                }
            }
        }
        return false;
    }

}
