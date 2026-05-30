package com.dashboard.saas.enums;

public enum Roles {

    USER("User"),
    ADMIN("Admin"),
    MANAGER("Manager");



    private final String label;

    Roles(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
