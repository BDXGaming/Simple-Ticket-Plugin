package com.ticket.commands;

import com.ticket.files.SimpleTicketConfig;
import com.ticket.files.Ticket;
import com.ticket.punishment.Punishment;
import com.ticket.punishment.PunishmentDatabase;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class SimpleTicketNewTicket implements CommandExecutor {

    private ArrayList<Player> staff = getStaff();

    public ArrayList<Player> getStaff(){
        ArrayList<Player> rt = new ArrayList<>();
        for (Player p: Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("ticket.ticket.staff")) {
                rt.add(p);
            }
        }
        return rt;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Punishment.checkPunishedPlayers();

        if (!(sender instanceof Player)){
            sender.sendMessage("Only Players can use this command");
            return true;
        }

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("newticket")) {
            if (player.hasPermission("ticket.ticket")) {
                ArrayList<UUID> playersPunished = Punishment.getPunishedPlayers();
                if(!(playersPunished.contains(player.getUniqueId()))) {
                    if (!(Ticket.hasTicket(player))) {
                        Ticket t = new Ticket(player);

                        player.sendMessage(Objects.requireNonNull(SimpleTicketConfig.get().getString("FirstMessage")));
                        getStaff();
                        Bukkit.broadcast(player.getDisplayName() + " §c Has Opened Ticket-" + t.getNum(), "ticket.ticket.staff");
                        return true;
                    } else {
                        player.sendMessage("§cYou already have an open ticket!");
                        return true;
                    }
                }
                else{
                    player.sendMessage(ChatColor.RED + "You are currently blocked from opening tickets!");
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
