package com.ticket.bungee.commands;

import com.ticket.bungee.config.BungeeStatusController;
import com.ticket.bungee.config.SimpleTicketBungeeConfig;
import com.ticket.bungee.files.BungeeTicket;
import com.ticket.bungee.files.TicketPlayer;
import com.ticket.bungee.punishment.BungeePunishment;
import com.ticket.bungee.punishment.BungeePunishmentDatabase;
import com.ticket.files.TicketConstants;
import com.ticket.utils.BungeeHelper;
import com.ticket.utils.MojangPlayerHelper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class BungeeNewTicket extends Command {

    public BungeeNewTicket(){
        super("newticket", "","ntick","nt");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        BungeePunishment.checkPunishedPlayers();

        if (!(sender instanceof ProxiedPlayer)){
            sender.sendMessage(new TextComponent("Only Players can use this command"));
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (player.hasPermission(TicketConstants.TICKET_PERM)) {
            ArrayList<UUID> playersPunished = BungeePunishment.getPunishedPlayers();
            if(!(playersPunished.contains(player.getUniqueId()))) {
                if (!(BungeeTicket.hasTicket(player))) {

                    BungeeTicket t = new BungeeTicket(player);

                    player.sendMessage(new TextComponent(Objects.requireNonNull(SimpleTicketBungeeConfig.get().getString("FirstMessage"))));
                    BungeeHelper.broadcast(org.bukkit.ChatColor.GRAY+"["+ org.bukkit.ChatColor.GREEN+"Simple-Ticket"+ org.bukkit.ChatColor.GRAY + "] " + org.bukkit.ChatColor.WHITE+player.getName() + org.bukkit.ChatColor.GREEN+ " Opened Ticket-" + t.getNum(), TicketConstants.TICKET_STAFF_PERM);

                } else {
                    player.sendMessage(new TextComponent(ChatColor.RED + "You already have an open ticket!"));
                }
            }
            else{
                player.sendMessage(new TextComponent(ChatColor.RED + "You are currently blocked from opening tickets!"));
            }
        }
        else{
            player.sendMessage(new TextComponent(ChatColor.RED+"You do not have the permissions to use this command!"));
        }

    }
}