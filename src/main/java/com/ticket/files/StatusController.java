package com.ticket.files;

public class StatusController {

    private String SuggestedReason;

    public StatusController() {
        this.SuggestedReason = SimpleTicketConfig.get().getString("SuggestedReason");
    }

    public void reload(){
        this.SuggestedReason = SimpleTicketConfig.get().getString("SuggestedReason");
    }

    public String getSuggestedReason() {
        return SuggestedReason;
    }
}
