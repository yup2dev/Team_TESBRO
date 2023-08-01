package com.team.tesbro.user;

import lombok.Getter;


@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    ACADEMY("ROLE_ACADEMY"),
    USER("ROLE_USER");

    UserRole(String value) {
        this.value = value;
    }

    private String value;
}