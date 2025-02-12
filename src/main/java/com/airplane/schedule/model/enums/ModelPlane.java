package com.airplane.schedule.model.enums;

public enum ModelPlane {
    AP_A220("AP_A220"),
    AP_A290("AP_A290"),
    AP_A330("AP_A330");

    private final String displayName;

    ModelPlane(String displayName) {
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