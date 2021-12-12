package com.ticket.files;

import java.util.ArrayList;
import java.util.List;

public class StatusController {

    public String SUGGESTED_REASON;
    public List<String> SUGGESTED_DURATIONS;
    public String sqlDataBaseType;
    public String databaseName;
    public String username;
    public String address;
    public String password;
    public boolean REMOTE_DB;

    public StatusController() {
        this.SUGGESTED_REASON = SimpleTicketConfig.get().getString("SuggestedReason");
        this.SUGGESTED_DURATIONS = SimpleTicketConfig.get().getStringList("SuggestedDurations");
        this.sqlDataBaseType= SimpleTicketConfig.get().getString("database");
        this.address = (SimpleTicketConfig.get().getString("address"));
        this.databaseName = (SimpleTicketConfig.get().getString("databaseName"));
        this.username = (SimpleTicketConfig.get().getString("username"));
        this.password = (SimpleTicketConfig.get().getString("password"));
        if(!this.sqlDataBaseType.equalsIgnoreCase("sqlite")){
            this.REMOTE_DB = true;
        }else{
            this.REMOTE_DB = false;
        }
    }

    public void reload(){
        this.SUGGESTED_REASON = SimpleTicketConfig.get().getString("SuggestedReason");
        this.SUGGESTED_DURATIONS = SimpleTicketConfig.get().getStringList("SuggestedDurations");
        this.sqlDataBaseType= SimpleTicketConfig.get().getString("database");
        this.address = (SimpleTicketConfig.get().getString("address"));
        this.databaseName = (SimpleTicketConfig.get().getString("databaseName"));
        this.username = (SimpleTicketConfig.get().getString("username"));
        this.password = (SimpleTicketConfig.get().getString("password"));
        if(!this.sqlDataBaseType.equalsIgnoreCase("sqlite")){
            this.REMOTE_DB = true;
        }
        this.REMOTE_DB = false;
    }

}
