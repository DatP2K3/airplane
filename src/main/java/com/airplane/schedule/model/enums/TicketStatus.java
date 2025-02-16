package com.airplane.schedule.model.enums;

public enum TicketStatus {
    PENDING("Pending"),
    SUCCESS("Success"),
    CANCELLED("Cancel");

    private final String displayName;

    TicketStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
