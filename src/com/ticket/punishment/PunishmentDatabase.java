package com.ticket.punishment;

import com.ticket.utils.timeConverters;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class PunishmentDatabase {

    /**
     * Creates the SQLite database file if none exists, adds table with given rows
     */
    public static void createDatabase() {
        File dir = Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("Simple-Ticket")).getDataFolder();
        String location = "jdbc:sqlite:" + dir.toString() + "\\SimpleTicket.db";

        try (Connection conn = DriverManager.getConnection(location)) {
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

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Adds the given player's punishment to the database
     * The player is the player being punished, staff is the player whom executed the punishment
     * @param player Player
     * @param duration int
     * @param staff Player
     */
    public static void punishPlayer(OfflinePlayer player, int duration, Player staff){
        File dir = Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("Simple-Ticket")).getDataFolder();
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

                File users = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("Simple-Ticket")).getDataFolder()+"/users");

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

                LocalDateTime timeExpired;

                if(duration >=86400){
                     timeExpired = myDateObj.plusDays(duration/(24*3600));
                }
                else {
                     timeExpired = myDateObj.plusSeconds(duration);
                }

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

    /**
     * Sets a punishment inactive for the given user UUID
     * @param uuid String
     */
    public static void setInactive(String uuid){
        File users = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("Simple-Ticket")).getDataFolder()+"/users");
        File file = new File(users, "activePunishments.yml");

        FileConfiguration f = YamlConfiguration.loadConfiguration(file);

        f.set(uuid, null);
        try {
            f.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets all active punishments for Simple-Ticket, returns ArrayList of players who need to be removed
     * @return ArrayList<UUID>
     */
    public static ArrayList<UUID> getActivePunishments() {

        File users = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("Simple-Ticket")).getDataFolder()+"/users");
        File file = new File(users, "activePunishments.yml");

        ArrayList<UUID> playersToRemove = new ArrayList<>();

        FileConfiguration f = YamlConfiguration.loadConfiguration(file);

        for(String key: f.getKeys(true)){
            String t = f.getString(key);
            assert t != null;
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

    /**
     * Get the string formatted history for the given user
     * @param uuid UUID
     * @return ArrayList<String>
     */
    public static ArrayList<String> getPlayerHist(UUID uuid){
        File dir = Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("Simple-Ticket")).getDataFolder();
        String location = "jdbc:sqlite:"+dir.toString()+"\\SimpleTicket.db";
        ArrayList<String> hist = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(location)){



            if(conn != null){
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM punishments WHERE UUID = ?");
                stmt.setString(1, uuid.toString());

                ResultSet rs = stmt.executeQuery();

                while(rs.next()){
                    String res;
                    res = "Name: " + rs.getString("name") + "\nDuration: " + timeConverters.getStringDuration(rs.getInt("duration")) + "\n" + "Staff: " + rs.getString("staffName");
                    hist.add(res);
                }

                rs.close();
                stmt.close();
                conn.close();

                return hist;
            }
        }catch (SQLException e){
            System.out.println("[Simple-Ticket] SQL ERROR "+ e);
            hist.add("DB Error");
            return hist;
        }
        return null;
    }

}
