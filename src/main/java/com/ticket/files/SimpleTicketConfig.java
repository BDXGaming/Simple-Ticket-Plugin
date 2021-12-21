package com.ticket.files;

import com.ticket.SimpleTicket;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.Objects;

public class SimpleTicketConfig {

    private static File file;
    private static FileConfiguration customfile;

    //Finds or generates config file thing
    public static void setup(){

        //Ensures that the config exists
        SimpleTicket.simpleTicket.saveDefaultConfig();

        InputStream inputStream = SimpleTicket.simpleTicket.getResource("config.yml");
        File target = new File(String.valueOf(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("Simple-Ticket")).getDataFolder()));

        File file = new File(target, "template.yml");
        try {
            assert inputStream != null;
            FileUtils.copyInputStreamToFile(inputStream, file);

            FileConfiguration template = YamlConfiguration.loadConfiguration(file);
            customfile = SimpleTicket.simpleTicket.getConfig();

            for (String key : template.getKeys(true)) {
                if (customfile.getKeys(true).contains(key)) {
                    template.set(key, customfile.get(key));
                }
            }

            file.delete();
            customfile = template;

        } catch (IOException e) {
           Bukkit.getLogger().warning(e.toString());
           Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Config Error");
       }

    }

    public static FileConfiguration get(){
        return customfile;
    }

    public static void reload(){
        setup();
    }

}
