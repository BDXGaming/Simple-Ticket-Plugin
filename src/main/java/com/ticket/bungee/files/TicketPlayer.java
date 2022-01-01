package com.ticket.bungee.files;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import java.util.UUID;

public class TicketPlayer {
    /**
     * This class serves as an intermediary between the Spigot and the Bungeecord version, allowing the Ticket,
     * punishment, and other classes to be shared between the versions.
     */
    private final UUID uuid;
    private final String name;
    private String displayName;

    //Requires one of the three to be present, set after construction
    private ProxiedPlayer proxiedPlayer;

    /**
     * Create a new TicketPlayer instance
     * @param uuid UUID
     * @param name String
     */
    public TicketPlayer (UUID uuid, String name){
        this.uuid = uuid;
        this.name = name;
        this.displayName = name;
    }

    /**
     * Create a new TicketPlayer
     * @param uuid UUID
     * @param name String
     * @param displayName String
     */
    public TicketPlayer (UUID uuid, String name, String displayName){
        this.uuid = uuid;
        this.name = name;
        this.displayName = displayName;
    }

    /**
     * Get the Unique id of the TicketPlayer
     * @return uuid UUID
     */
    public UUID getUniqueId() {
        return uuid;
    }

    /**
     * Get the name of the TicketPlayer
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Get the displayName of the TicketPlayer
     * @return String
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the displayName of the TicketPlayer
     * @param displayName String
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the ProxiedPlayer associated with the given TicketPlayer
     * @return ProxiedPlayer
     */
    public ProxiedPlayer getProxiedPlayer() {
        return proxiedPlayer;
    }

    /**
     * Sets the TicketPlayer ProxiedPlayer
     * @param proxiedPlayer ProxiedPlayer
     */
    public void setProxiedPlayer(ProxiedPlayer proxiedPlayer) {
        this.proxiedPlayer = proxiedPlayer;
    }

}
