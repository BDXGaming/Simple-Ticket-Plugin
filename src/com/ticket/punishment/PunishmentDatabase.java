package com.ticket.punishment;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import javax.swing.plaf.DimensionUIResource;
import javax.xml.transform.Result;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.UUID;

public class PunishmentDatabase {

    public static void punishPlayer(Player player, int duration, Player staff){
        File dir = Bukkit.getServer().getPluginManager().getPlugin("Simple-Ticket").getDataFolder();
        String location = "jdbc:sqlite:"+dir.toString()+"\\SimpleTicket.db";

        try(Connection conn = DriverManager.getConnection(location)){
            if (conn != null) {

                String sql = "CREATE TABLE IF NOT EXISTS punishments (\n"
                        + "UUID text, \n"
                        + "name text, \n"
                        + "duration int, \n"
                        + "staffName text, \n"
                        + "time text \n"
                        + ");";

                Statement stmt = conn.createStatement();
                stmt.execute(sql);
                stmt.close();

                File users = new File(Bukkit.getServer().getPluginManager().getPlugin("Simple-Ticket").getDataFolder()+"/users");

                if(!users.exists()){
                    users.mkdir();
                }

                File file = new File(users, "activePunishments.yml");

                if(!(file.exists())){
                    try{
                        file.createNewFile();
                    }catch (IOException e){
                        System.out.println(ChatColor.YELLOW + "[Simple-Ticket]: "+ e.toString());
                    }
                }

                FileConfiguration user = YamlConfiguration.loadConfiguration(file);

                LocalDateTime myDateObj = LocalDateTime.now();
                LocalDateTime timeExpired = myDateObj.plusSeconds(duration);

                String addsql = "INSERT INTO punishments(UUID, name, duration, staffName, time) VALUES(?,?,?,?,?)";

                PreparedStatement ps = conn.prepareStatement(addsql);
                ps.setString(1, player.getUniqueId().toString());
                ps.setString(2, player.getName());
                ps.setInt(3, duration);
                ps.setString(4, staff.getName());
                ps.setString(5, timeExpired.toString());

                ps.executeUpdate();

                user.addDefault(player.getUniqueId().toString(),timeExpired.toString());
                user.options().copyDefaults(true);
                user.save(file);

                ps.close();
                conn.close();

            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPlayerPunishmentHistory(Player player){
        File dir = Bukkit.getServer().getPluginManager().getPlugin("Simple-Ticket").getDataFolder();
        String location = "jdbc:sqlite:"+dir.toString()+"\\SimpleTicket.db";

        try(Connection conn = DriverManager.getConnection(location)){

            if(conn != null){
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM punishments WHERE UUID = ?");
                stmt.setString(1, player.getUniqueId().toString());

                ResultSet rs = stmt.executeQuery();

                System.out.println(rs);

                rs.close();
                stmt.close();
                conn.close();

                return(rs.toString());
            }
        }catch (SQLException e){
            System.out.println("[Simple-Ticket] SQL ERROR "+ e);

        }
        return "none";
    }

    public static void setInactive(String uuid){
        File users = new File(Bukkit.getServer().getPluginManager().getPlugin("Simple-Ticket").getDataFolder()+"/users");
        File file = new File(users, "activePunishments.yml");

        FileConfiguration f = YamlConfiguration.loadConfiguration(file);

        f.set(uuid, null);
        try {
            f.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<UUID> getActivePunishments() {

        File users = new File(Bukkit.getServer().getPluginManager().getPlugin("Simple-Ticket").getDataFolder()+"/users");
        File file = new File(users, "activePunishments.yml");

        ArrayList<UUID> playersToRemove = new ArrayList<>();

        FileConfiguration f = YamlConfiguration.loadConfiguration(file);

        for(String key: f.getKeys(true)){
            String t = f.getString(key);
            LocalDateTime ldt = LocalDateTime.parse(t);

            LocalDateTime currentTime = LocalDateTime.now();

            long diff = currentTime.until(ldt, ChronoUnit.SECONDS);

            System.out.println(diff + " " +key);

            if (diff > 0) {
                playersToRemove.add(UUID.fromString(key));
            }

            else if (diff <0){
                setInactive(f.getString(key));
            }

        }


        return playersToRemove;
    }

}