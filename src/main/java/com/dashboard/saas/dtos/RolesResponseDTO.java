package com.dashboard.saas.dtos;

public class RolesResponseDTO {

    private String roleName;

    public RolesResponseDTO() {
    }

    public RolesResponseDTO(String roleName) {
        this.roleName = roleName;
    }

    // GETTERS

    public String getRoleName() {
        return roleName;
    }

    // SETTERS

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
