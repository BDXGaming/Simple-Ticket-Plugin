package com.ticket.bungee.commands;

import com.ticket.bungee.files.TicketPlayer;
import com.ticket.bungee.punishment.BungeePunishment;
import com.ticket.files.TicketConstants;
import com.ticket.utils.BungeeHelper;
import com.ticket.utils.MojangPlayerHelper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class BungeeRemovePunishment extends Command {


    public BungeeRemovePunishment() {
        super("tunpunish", "", "tunmute", "tunban", "tfree");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (sender instanceof ProxiedPlayer){
            ProxiedPlayer player = (ProxiedPlayer) sender;

            if(player.hasPermission(TicketConstants.TICKET_STAFF_PERM)){
                if(args.length >= 1){

                    TicketPlayer p = MojangPlayerHelper.getPlayer(MojangPlayerHelper.getUniqueId(args[0]));

                    if(BungeePunishment.checkForPlayerPunishment(p.getUniqueId())){

                        BungeePunishment.removePunishedPlayer(p);
                        player.sendMessage(new TextComponent(ChatColor.GREEN+p.getName()+ChatColor.GREEN+" is now able to open tickets!"));
                        BungeeHelper.broadcast(ChatColor.GRAY+"["+ChatColor.GREEN+"Simple-Ticket"+ChatColor.GRAY + "] " +ChatColor.RESET+ sender.getName() + ChatColor.GREEN+" un-ticket-blocked " +ChatColor.RESET+p.getName(), TicketConstants.TICKET_STAFF_PERM);

                    }
                    else{
                        sender.sendMessage(new TextComponent(ChatColor.YELLOW + "This player is not currently being punished!"));
                    }



                }else{
                    player.sendMessage(new TextComponent(ChatColor.YELLOW +"You must use the following format /tunban <player>"));

                }
            }else{
                player.sendMessage(new TextComponent(ChatColor.RED +"You do not have the permissions to use this command!"));

            }
        }

    }
}
