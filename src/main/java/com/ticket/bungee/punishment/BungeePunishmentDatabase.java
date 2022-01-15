package com.ticket.bungee.punishment;

import com.ticket.SimpleTicketBungee;
import com.ticket.bungee.files.TicketPlayer;
import com.ticket.utils.LoggerControl;
import com.ticket.utils.TimeConverters;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class BungeePunishmentDatabase {

    private static Connection conn;

    /**
     * Creates the SQLite database file if none exists, adds table with given rows
     */
    public static void createDatabaseConnection() throws SQLException {
        String location ="";
        try {
            Class.forName("org.postgresql.Driver");
            Class.forName ("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            LoggerControl.warning(e.toString());
        }

        if(SimpleTicketBungee.bungeeStatusController.sqlDataBaseType.equalsIgnoreCase("sqlite") || SimpleTicketBungee.bungeeStatusController.sqlDataBaseType.equalsIgnoreCase("local")){
            File dir = SimpleTicketBungee.simpleTicketBungee.getDataFolder().getAbsoluteFile();

            location = "jdbc:h2:" + dir.toString() + "\\SimpleTicket";
            conn = DriverManager.getConnection(location, "st", "test");

        }else if(SimpleTicketBungee.bungeeStatusController.sqlDataBaseType.equalsIgnoreCase("postgresql")){
            location = "jdbc:postgresql://"+SimpleTicketBungee.bungeeStatusController.address +"/"+SimpleTicketBungee.bungeeStatusController.databaseName;
            conn = DriverManager.getConnection(location, SimpleTicketBungee.bungeeStatusController.username, SimpleTicketBungee.bungeeStatusController.password);

            ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("[Simple-Ticket]: PostGreSQL Database Connected!"));
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
    public static void punishPlayer(TicketPlayer player, int duration, ProxiedPlayer staff, String reason){

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

                if(!SimpleTicketBungee.bungeeStatusController.REMOTE_DB){
                    File users = new File(SimpleTicketBungee.simpleTicketBungee.getDataFolder()+"/users");

                    if(!users.exists()){
                        users.mkdir();
                    }

                    File file = new File(users, "activePunishments.yml");

                    if(!(file.exists())){
                        try{
                            file.createNewFile();
                        }catch (IOException e){
                            LoggerControl.warning(e.toString());
                        }
                    }

                    Configuration user = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
                    user.set(player.getUniqueId().toString(),timeExpired.toString());
                    ConfigurationProvider.getProvider(YamlConfiguration.class).save(user, file);

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
                LoggerControl.warning(e.toString());
            }


        }
    }

    /**
     * Sets a punishment inactive for the given user UUID
     * @param uuid String
     */
    public static void setInactive(String uuid){

        if(!SimpleTicketBungee.bungeeStatusController.REMOTE_DB){
            File users = new File(SimpleTicketBungee.simpleTicketBungee.getDataFolder() +"/users");
            File file = new File(users, "activePunishments.yml");

            Configuration f = null;
            try {
                f = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            } catch (IOException e) {
                LoggerControl.warning(e.toString());
            }

            f.set(uuid, null);
            try {
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(f, file);
            } catch (IOException e) {
                LoggerControl.warning(e.toString());
            }
        }else{
            String SQLStament = "DELETE FROM simpleticket_active_punishments WHERE uuid = ?";
            try {
                PreparedStatement ps = conn.prepareStatement(SQLStament);
                ps.setString(1, uuid);
                ps.executeUpdate();

                ps.close();

            } catch (SQLException throwables) {
                LoggerControl.warning(Arrays.toString(throwables.getStackTrace()));
            }
        }
    }

    /**
     * Gets all active punishments for Simple-Ticket, returns ArrayList of players who need to be removed
     * @return ArrayList<UUID>
     */
    public static ArrayList<UUID> getActivePunishments() {


        if(!SimpleTicketBungee.bungeeStatusController.REMOTE_DB){
            File users = new File(SimpleTicketBungee.simpleTicketBungee.getDataFolder()+"/users");
            if(!users.exists()){
                users.mkdirs();
            }
            File file = new File(users, "activePunishments.yml");
            if(!file.exists()){
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    LoggerControl.warning(e.toString());
                }
            }


            ArrayList<UUID> playersToRemove = new ArrayList<>();

            Configuration f = null;
            try {
                f = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            } catch (IOException e) {
                LoggerControl.warning(e.toString());
            }

            assert f != null;
            for(String key: f.getKeys()){
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
                LoggerControl.warning(Arrays.toString(throwables.getStackTrace()));
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
                                + "\nDuration: " + ChatColor.WHITE + TimeConverters.getStringDuration(rs.getInt("duration")) + ChatColor.GREEN
                                + "\nStaff: " + ChatColor.WHITE + rs.getString("staffName") + ChatColor.GREEN
                                + "\nPunished: " + ChatColor.WHITE + LocalDateTime.parse(rs.getString("time")).minusSeconds(rs.getInt("duration")).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + ChatColor.GREEN
                                + "\nReason: " + ChatColor.WHITE + rs.getString("reason");
                        hist.add(res);
                    } while(rs.next());
                }
                rs.close();
                stmt.close();
            } catch (SQLException e){
                LoggerControl.warning(Arrays.toString(e.getStackTrace()));
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
            LoggerControl.warning(e.toString());
        }
    }

    public static void closeConn(){
        try {
            conn.close();
        } catch (SQLException e) {
            LoggerControl.warning(e.toString());
        }
    }


}
