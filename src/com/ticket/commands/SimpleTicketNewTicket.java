package com.ticket.commands;

import com.ticket.files.SimpleTicketConfig;
import com.ticket.files.Ticket;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.Objects;

public class SimpleTicketNewTicket implements CommandExecutor {

    private ArrayList<Player> staff = getStaff();

    public ArrayList<Player> getStaff(){
        ArrayList<Player> rt = new ArrayList<>();
        for (Player p: Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("Reactor.ticket.staff")) {
                rt.add(p);
            }
        }
        return rt;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)){
            sender.sendMessage("Only Players can use this command");
            return true;
        }

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("newticket")) {
            if (player.hasPermission("ticket.ticket")) {
                if (!(Ticket.hasTicket(player))) {
                    Ticket t = new Ticket(player);

                    player.sendMessage(Objects.requireNonNull(SimpleTicketConfig.get().getString("FirstMessage")));
                    getStaff();
                    for (Player p : staff) {
                        p.sendMessage(player.getDisplayName() + " §c Has Opened Ticket-"+t.getNum());
                    }
                    return true;
                }
                else{
                    player.sendMessage("§cYou already have an open ticket!");
                    return true;
                }
            }
            else{
                player.sendMessage(ChatColor.RED+"You do not have the permissions to use this command!");
            }
        }

        return false;
    }
}
