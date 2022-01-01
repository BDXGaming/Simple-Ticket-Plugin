package com.ticket.utils;

import com.ticket.bungee.files.TicketPlayer;
import org.shanerx.mojang.Mojang;
import org.shanerx.mojang.PlayerProfile;

import java.util.UUID;
import java.util.regex.Pattern;

public class MojangPlayerHelper {

    private static final Mojang m = new Mojang();

    private static final Pattern UUID_FIX = Pattern.compile("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})");

    public static UUID formatFromInput(String uuid) {
        return UUID.fromString(UUID_FIX.matcher(uuid.replace("-", "")).replaceAll("$1-$2-$3-$4-$5"));
    }

    public static UUID getUniqueId(String username){
//        final String[] uuid = new String[1];
//        SimpleTicketBungee.simpleTicketBungee.getProxy().getScheduler().schedule(SimpleTicketBungee.simpleTicketBungee, new Runnable() {
//            @Override
//            public void run() {
//                Mojang m = new Mojang();
//                uuid[0] = m.getUUIDOfUsername(username);
//            }
//        }, 1, 1440, TimeUnit.MINUTES);
//        return uuid[0];

        String uuid = m.getUUIDOfUsername(username);
        return formatFromInput(uuid);
    }


    public static TicketPlayer getPlayer(UUID uuid){
        PlayerProfile p = m.getPlayerProfile(String.valueOf(uuid));
        return new TicketPlayer(uuid, p.getUsername());
    }
}
