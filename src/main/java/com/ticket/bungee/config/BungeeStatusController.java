package com.ticket.bungee.config;

import com.ticket.bungee.config.SimpleTicketBungeeConfig;
import java.util.List;

public class BungeeStatusController {

    public String SUGGESTED_REASON;
    public List<String> SUGGESTED_DURATIONS;
    public int DEFAULT_DURATION;
    public String sqlDataBaseType;
    public String databaseName;
    public String username;
    public String address;
    public String password;
    public boolean REMOTE_DB;
    public boolean PUNISHMENT_SYNC;
    public boolean VAULT;
    public boolean BUNGEE;

    public BungeeStatusController() {
        this.SUGGESTED_REASON = SimpleTicketBungeeConfig.get().getString("SuggestedReason");
        this.SUGGESTED_DURATIONS = SimpleTicketBungeeConfig.get().getStringList("SuggestedDurations");
        this.DEFAULT_DURATION = SimpleTicketBungeeConfig.get().getInt("Default Duration");
        this.sqlDataBaseType= SimpleTicketBungeeConfig.get().getString("database");
        this.address = (SimpleTicketBungeeConfig.get().getString("address"));
        this.databaseName = (SimpleTicketBungeeConfig.get().getString("databaseName"));
        this.username = (SimpleTicketBungeeConfig.get().getString("username"));
        this.password = (SimpleTicketBungeeConfig.get().getString("password"));
        if(!this.sqlDataBaseType.equalsIgnoreCase("sqlite")){
            this.REMOTE_DB = true;
        }else{
            this.REMOTE_DB = false;
        }
        this.PUNISHMENT_SYNC = SimpleTicketBungeeConfig.get().getBoolean("syncPunishmentOnJoin");
        this.VAULT = SimpleTicketBungeeConfig.get().getBoolean("useVault");
        this.BUNGEE = SimpleTicketBungeeConfig.get().getBoolean("useBungee");
    }

    public void reload(){
        this.SUGGESTED_REASON = SimpleTicketBungeeConfig.get().getString("SuggestedReason");
        this.SUGGESTED_DURATIONS = SimpleTicketBungeeConfig.get().getStringList("SuggestedDurations");
        this.DEFAULT_DURATION = SimpleTicketBungeeConfig.get().getInt("Default Duration");
        this.sqlDataBaseType= SimpleTicketBungeeConfig.get().getString("database");
        this.address = (SimpleTicketBungeeConfig.get().getString("address"));
        this.databaseName = (SimpleTicketBungeeConfig.get().getString("databaseName"));
        this.username = (SimpleTicketBungeeConfig.get().getString("username"));
        this.password = (SimpleTicketBungeeConfig.get().getString("password"));
        if(!this.sqlDataBaseType.equalsIgnoreCase("sqlite")){
            this.REMOTE_DB = true;
        }else{
            this.REMOTE_DB = false;
        }
        this.PUNISHMENT_SYNC = SimpleTicketBungeeConfig.get().getBoolean("syncPunishmentOnJoin");
        this.VAULT = SimpleTicketBungeeConfig.get().getBoolean("useVault");
        this.BUNGEE = SimpleTicketBungeeConfig.get().getBoolean("useBungee");
    }
}
