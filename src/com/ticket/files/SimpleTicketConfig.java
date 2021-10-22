package com.ticket.files;

import com.ticket.SimpleTicket;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class SimpleTicketConfig {

    private static File file;
    private static FileConfiguration customfile;

    //Finds or generates config file thing
    public static void setup(){

        SimpleTicket.simpleTicket.saveDefaultConfig();

        customfile = SimpleTicket.simpleTicket.getConfig();
    }

    public static FileConfiguration get(){
        return customfile;
    }

    public static void save(){
        try {
            customfile.save(file);
        }catch (IOException e){
            System.out.println("Could not save file");
        }
    }

    public static void reload(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Simple-Ticket").getDataFolder(), "config.yml");
        customfile = YamlConfiguration.loadConfiguration(file);
    }

}
