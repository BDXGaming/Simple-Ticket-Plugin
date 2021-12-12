package com.ticket.punishment;

import com.ticket.SimpleTicket;
import com.ticket.files.SimpleTicketConfig;
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
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class PunishmentDatabase {

    private static Connection conn;

    /**
     * Creates the SQLite database file if none exists, adds table with given rows
     */
    public static void createDatabaseConnection() throws SQLException {
        File dir = Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("Simple-Ticket")).getDataFolder();
        String location ="";
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            Bukkit.getLogger().warning(e.toString());
        }

        if(SimpleTicket.statusController.sqlDataBaseType.equalsIgnoreCase("sqlite")){
            location = "jdbc:sqlite:" + dir.toString() + "\\SimpleTicket.db";
            conn = DriverManager.getConnection(location);

        }else if(SimpleTicket.statusController.sqlDataBaseType.equalsIgnoreCase("postgresql")){
            location = "jdbc:postgresql://"+SimpleTicket.statusController.address +"/"+SimpleTicket.statusController.databaseName;
            conn = DriverManager.getConnection(location, SimpleTicket.statusController.username, SimpleTicket.statusController.password);

            Bukkit.getServer().getConsoleSender().sendMessage("Database Connected!");
        }


        if (conn != null) {

            String sql = "CREATE TABLE IF NOT EXISTS simpleticket_punishments (\n"
                    + "uuid text, \n"
                    + "username text, \n"
                    + "duration int, \n"
                    + "staffName text, \n"
                    + "time text, \n"
                    + "reason text \n"
                    + ");";

            String sql2 = "CREATE TABLE IF NOT EXISTS simpleticket_active_punishments (\n"
                    + "uuid text, \n"
                    + "username text, \n"
                    + "time text \n"
                    + ");";

            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();

            Statement stmt2 = conn.createStatement();
            stmt2.execute(sql2);
            stmt2.close();

        }
    }

    /**
     * Adds the given player's punishment to the database
     * The player is the player being punished, staff is the player whom executed the punishment
     * @param player Player
     * @param duration int
     * @param staff Player
     */
    public static void punishPlayer(OfflinePlayer player, int duration, Player staff, String reason){

        if (conn != null) {

            try{String sql = "CREATE TABLE IF NOT EXISTS simpleticket_punishments (\n"
                    + "uuid text, \n"
                    + "username text, \n"
                    + "duration int, \n"
                    + "staffName text, \n"
                    + "time text, \n"
                    + "reason text \n"
                    + ");";

                Statement stmt = conn.createStatement();
                stmt.execute(sql);
                stmt.close();

                LocalDateTime myDateObj = LocalDateTime.now();

                LocalDateTime timeExpired;

                if(duration >=86400){
                    timeExpired = myDateObj.plusDays(duration/(24*3600));
                }
                else {
                    timeExpired = myDateObj.plusSeconds(duration);
                }

                String addsql = "INSERT INTO simpleticket_punishments(uuid, username, duration, staffName, time, reason) VALUES(?,?,?,?,?,?)";

                PreparedStatement ps = conn.prepareStatement(addsql);
                ps.setString(1, player.getUniqueId().toString());
                ps.setString(2, player.getName());
                ps.setInt(3, duration);
                ps.setString(4, staff.getName());
                ps.setString(5, timeExpired.toString());
                ps.setString(6, reason);

                if(!SimpleTicket.statusController.REMOTE_DB){
                    File users = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("Simple-Ticket")).getDataFolder()+"/users");

                    if(!users.exists()){
                        users.mkdir();
                    }

                    File file = new File(users, "activePunishments.yml");

                    if(!(file.exists())){
                        try{
                            file.createNewFile();
                        }catch (IOException e){
                            Bukkit.getLogger().warning(e.toString());
                        }
                    }

                    FileConfiguration user = YamlConfiguration.loadConfiguration(file);
                    user.addDefault(player.getUniqueId().toString(),timeExpired.toString());
                    user.options().copyDefaults(true);
                    user.save(file);
                }else{
                    String activeSQL = "INSERT INTO simpleticket_active_punishments(uuid, username, time) VALUES(?,?,?)";
                    PreparedStatement ps2 = conn.prepareStatement(activeSQL);
                    ps2.setString(1, player.getUniqueId().toString());
                    ps2.setString(2, player.getName());
                    ps2.setString(3, timeExpired.toString());

                    ps2.executeUpdate();
                    ps2.close();
                }

                ps.executeUpdate();
                ps.close();

            }catch (SQLException | IOException e){
                Bukkit.getLogger().warning(e.toString());
            }


        }
    }

    /**
     * Sets a punishment inactive for the given user UUID
     * @param uuid String
     */
    public static void setInactive(String uuid){

        if(!SimpleTicket.statusController.REMOTE_DB){
            File users = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("Simple-Ticket")).getDataFolder()+"/users");
            File file = new File(users, "activePunishments.yml");

            FileConfiguration f = YamlConfiguration.loadConfiguration(file);

            f.set(uuid, null);
            try {
                f.save(file);
            } catch (IOException e) {
                Bukkit.getLogger().warning(e.toString());
            }
        }else{
            String SQLStament = "DELETE FROM simpleticket_active_punishments WHERE uuid = ?";
            try {
                PreparedStatement ps = conn.prepareStatement(SQLStament);
                ps.setString(1, uuid);
                ps.executeUpdate();

                ps.close();

            } catch (SQLException throwables) {
                Bukkit.getLogger().warning(Arrays.toString(throwables.getStackTrace()));
            }
        }
    }

    /**
     * Gets all active punishments for Simple-Ticket, returns ArrayList of players who need to be removed
     * @return ArrayList<UUID>
     */
    public static ArrayList<UUID> getActivePunishments() {


        if(!SimpleTicket.statusController.REMOTE_DB){
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

                if (diff > 0) {
                    playersToRemove.add(UUID.fromString(key));
                }

                else if (diff <0){
                    setInactive(f.getString(key));
                }

            }


            return playersToRemove;
        }else{
            String sql = "SELECT * FROM simpleticket_active_punishments;";
            ArrayList<UUID> activePunishments = new ArrayList<>();
            try {
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();

                if(!rs.next()){
                    return activePunishments;
                }
                else{
                    do{
                        String t = rs.getString("time");
                        assert t != null;
                        LocalDateTime ldt = LocalDateTime.parse(t);

                        LocalDateTime currentTime = LocalDateTime.now();

                        long diff = currentTime.until(ldt, ChronoUnit.SECONDS);

                        if (diff > 0) {
                            activePunishments.add(UUID.fromString(rs.getString("uuid")));
                        }

                        else if (diff <0){
                            setInactive(rs.getString("uuid"));
                        }

                    } while(rs.next());
                }

                rs.close();
                stmt.close();

                return activePunishments;

            } catch (SQLException throwables) {
                Bukkit.getLogger().warning(Arrays.toString(throwables.getStackTrace()));
            }

        }
        return null;
    }

    /**
     * Get the string and color formatted history for the given user
     * @param uuid UUID
     * @return ArrayList<String>
     */
    public static ArrayList<String> getPlayerHist(UUID uuid){
        ArrayList<String> hist = new ArrayList<>();

            if(conn != null){
                try{
                    PreparedStatement stmt = conn.prepareStatement("SELECT * FROM simpleticket_punishments WHERE uuid = ?;");
                    //String SQL = "SELECT * FROM simpleticket_punishments WHERE uuid = '"+uuid.toString();
                    stmt.setString(1, uuid.toString());
                    ResultSet rs = stmt.executeQuery();

                    if(!rs.next()){
                        String res = "\n" + ChatColor.GREEN + "No Hist Found!";
                        hist.add(res);
                    }
                    else{
                        do{
                            String res;
                            res = ChatColor.GREEN + "Name: " + ChatColor.WHITE + rs.getString("username") + ChatColor.GREEN
                                    + "\nDuration: " + ChatColor.WHITE + timeConverters.getStringDuration(rs.getInt("duration")) + ChatColor.GREEN
                                    + "\nStaff: " + ChatColor.WHITE + rs.getString("staffName") + ChatColor.GREEN
                                    + "\nPunished: " + ChatColor.WHITE + LocalDateTime.parse(rs.getString("time")).minusSeconds(rs.getInt("duration")).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + ChatColor.GREEN
                                    + "\nReason: " + ChatColor.WHITE + rs.getString("reason");
                            hist.add(res);
                        } while(rs.next());
                    }
                    rs.close();
                    stmt.close();
                    } catch (SQLException e){
                        Bukkit.getLogger().warning(Arrays.toString(e.getStackTrace()));
                        hist.add("DB Error");
                }
            }
            else{
                hist.add("DB Error: Database connection Failed!");
            }
            return hist;
        }

    public static void clearPlayerHistory(UUID uuid){
        try{
            if(conn != null){
                String sql = "DELETE FROM simpleticket_punishments WHERE UUID = ?;";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, uuid.toString());
                stmt.executeUpdate();
                stmt.close();
            }
        }catch (SQLException e){
            Bukkit.getLogger().warning(e.toString());
        }
    }

    public static void closeConn(){
        try {
            conn.close();
        } catch (SQLException e) {
            Bukkit.getLogger().warning(e.toString());
        }
    }

}
