package com.ticket.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class SimpleTicketConfig {

    private static File file;
    private static FileConfiguration customfile;

    //Finds or generates config file thing
    public static void setup(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Simple-Ticket").getDataFolder(), "config.yml");

        if(!(file.exists())){
            try{
                file.createNewFile();
            }catch (IOException e){
                //naa
            }
        }
        customfile = YamlConfiguration.loadConfiguration(file);
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
        customfile = YamlConfiguration.loadConfiguration(file);
    }

}
