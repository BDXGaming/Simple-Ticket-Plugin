package com.ticket.files;

import java.util.List;

public class StatusController {

    public String SUGGESTED_REASON;
    public List<String> SUGGESTED_DURATIONS;

    public StatusController() {
        this.SUGGESTED_REASON = SimpleTicketConfig.get().getString("SuggestedReason");
        this.SUGGESTED_DURATIONS = SimpleTicketConfig.get().getStringList("SuggestedDurations");
    }

    public void reload(){
        this.SUGGESTED_REASON = SimpleTicketConfig.get().getString("SuggestedReason");
        this.SUGGESTED_DURATIONS = SimpleTicketConfig.get().getStringList("SuggestedDurations");
    }

}
