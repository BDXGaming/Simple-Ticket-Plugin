package com.ticket.bungee.config;

import com.ticket.SimpleTicketBungee;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class SimpleTicketBungeeConfig {

    public static Configuration configuration;

    public static void setupBungee(){
        if (!SimpleTicketBungee.simpleTicketBungee.getDataFolder().exists())
            SimpleTicketBungee.simpleTicketBungee.getDataFolder().mkdir();

        File file = new File(SimpleTicketBungee.simpleTicketBungee.getDataFolder(), "config.yml");


        if (!file.exists()) {
            try (InputStream in = SimpleTicketBungee.simpleTicketBungee.getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                ProxyServer.getInstance().getLogger().warning(e.toString());
            }
        }

        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(SimpleTicketBungee.simpleTicketBungee.getDataFolder(), "config.yml"));
        } catch (IOException e) {
            ProxyServer.getInstance().getLogger().warning(e.toString());
        }
    }

    public static void reload(){
        setupBungee();
    }

    public static Configuration get(){
        return configuration;
    }
}
