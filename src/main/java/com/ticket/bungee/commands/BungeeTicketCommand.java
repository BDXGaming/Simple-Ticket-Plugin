package com.ticket.bungee.commands;

import com.ticket.bungee.files.BungeeTicket;
import com.ticket.files.Ticket;
import com.ticket.files.TicketConstants;
import com.ticket.utils.BungeeHelper;
import com.ticket.utils.LoggerControl;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.Arrays;

public class BungeeTicketCommand extends Command implements TabExecutor {

    public BungeeTicketCommand() {
        super("ticket", "", "tr");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if(!(sender instanceof ProxiedPlayer)){
            sender.sendMessage(new TextComponent(ChatColor.RED + "This command can only be used by players!"));
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if(player.hasPermission(TicketConstants.TICKET_PERM)){

            if(player.hasPermission(TicketConstants.TICKET_STAFF_PERM)) {

                if (args.length > 0) {

                    if (args[0].equalsIgnoreCase("claim")) {
                        ArrayList<ProxiedPlayer> staff_tickets = BungeeTicket.getStaffWithTickets();
                        if (!staff_tickets.contains(player)) {
                            BungeeTicket t = BungeeTicket.getTicket(args[1]);
                            assert t != null;
                            if (!(t.isClaimed())) {

                                t.claimTicket(player);
                                BungeeHelper.broadcast(ChatColor.GRAY+"["+ChatColor.GREEN+"Simple-Ticket"+ChatColor.GRAY + "] " +ChatColor.WHITE + player.getName() + ChatColor.GREEN+" claimed Ticket-"+ t.getNum(), TicketConstants.TICKET_STAFF_PERM);

                            } else {
                                player.sendMessage(new TextComponent(ChatColor.RED + "This ticket is already claimed by: " + t.getStaffClaimer().getName()));
                            }
                        } else {
                            player.sendMessage(new TextComponent(ChatColor.YELLOW + "You cannot claim more than one ticket at a time!"));
                        }
                    }

                    //Closes and deletes the ticket
                    else if (args[0].equalsIgnoreCase("close")) {
                        BungeeTicket t = BungeeTicket.getTicket(args[1]);

                        player.sendMessage(new TextComponent(ChatColor.RED + "[Ticket-" + args[1] + "]§c§l Deleted"));
                        if (t != null) {
                            t.deleteTicket();
                        }
                    }

                    //To view all the previous messages sent in the ticket both by staff and the player
                    else if ((args[0].equalsIgnoreCase("history")) || (args[0].equalsIgnoreCase("hist"))) {
                        try {
                            BungeeTicket t = BungeeTicket.getTicket(args[1]);
                            player.sendMessage((new TextComponent(t.getMsgLog())));

                        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
                            player.sendMessage((new TextComponent(ChatColor.RED + "There are no open tickets at this time!")));

                        }
                    }
                    else if (BungeeTicket.hasClaim(player)) {
                        try {
                            BungeeTicket t = BungeeTicket.getClaim(player);
                            StringBuilder msg = new StringBuilder("§c[Ticket-" + t.getNum() + "] §a(" + player.getName() + "§a):§d ");
                            for (int i = 0; i < args.length; i++) {
                                msg.append(args[i]).append(" ");
                            }
                            t.getOwner().sendMessage(new TextComponent(msg.toString()));
                            player.sendMessage(new TextComponent(msg.toString()));
                            t.addmsg(msg.toString());
                        } catch (ArrayIndexOutOfBoundsException e) {
                            LoggerControl.warning(e.toString());
                        }
                    }
                    else if(BungeeTicket.hasTicket(player)){
                        BungeeTicket t = BungeeTicket.getTicket(player);
                        StringBuilder msg = new StringBuilder("§c[Ticket-" + t.getNum() + "]§a (" + player.getName() + "§a):§d ");
                        for (int i = 0; i < args.length; i++) {
                            msg.append(args[i]).append(" ");
                        }
                        if (!(t.isClaimed())) {
                            BungeeHelper.broadcast(msg.toString(), TicketConstants.TICKET_STAFF_PERM);
                            t.addmsg(msg.toString());

                        } else {
                            ProxiedPlayer staff = t.getStaffClaimer();
                            staff.sendMessage(new TextComponent(msg.toString()));
                            t.addmsg(msg.toString());
                        }
                    }
                    else{
                        sender.sendMessage(new TextComponent(ChatColor.RED + "Invalid Command use! /<command> <message/subcommand>"));
                    }
                }

                else{
                    sender.sendMessage(new TextComponent(ChatColor.RED + "Invalid Command use! /<command> <message/subcommand>"));
                }
            }
            else {
                try {
                    if (BungeeTicket.hasTicket(player)) {
                        BungeeTicket t = BungeeTicket.getTicket(player);
                        StringBuilder msg = new StringBuilder("§c[Ticket-" + t.getNum() + "] §a(" + player.getName() + "§a):§d ");
                        for (int i = 0; i < args.length; i++) {
                            msg.append(args[i]).append(" ");
                        }
                        if (!(t.isClaimed())) {
                            player.sendMessage(new TextComponent(msg.toString()));
                            BungeeHelper.broadcast(msg.toString(), TicketConstants.TICKET_STAFF_PERM);
                            t.addmsg(msg.toString());

                        } else {
                            ProxiedPlayer staff = t.getStaffClaimer();
                            staff.sendMessage(new TextComponent(msg.toString()));
                            player.sendMessage(new TextComponent(msg.toString()));
                            t.addmsg(msg.toString());

                        }
                    } else {
                        player.sendMessage(new TextComponent(ChatColor.RED +"You do not have an open ticket!"));

                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                   ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(ChatColor.RED + "[Simple-Ticket]: Error in command processing!"));

                }
            }
        }

    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {

        final String[] subcommands = {"claim", "close", "history"};
        ArrayList<String> tab = new ArrayList<>();
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
                    for (BungeeTicket ticket : BungeeTicket.getTickets()) {
                        tab.add(ticket.getNum().toString());
                    }
                }
                return tab;
            }
        }

        return tab;
    }
}
