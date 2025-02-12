package com.airplane.schedule.model.enums;

import lombok.Data;

public enum SeatType {
    ECONOMY("Economy"),
    BUSINESS("Business"),
    FIRST_CLASS("FirstClass");

    private final String displayName;

    SeatType(String displayName) {
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