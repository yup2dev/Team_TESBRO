package com.team.tesbro.User;

import lombok.Getter;


@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    ACADEMY("ROLE_ACDEMY"),
    USER("ROLE_USER");

    UserRole(String value) {
        this.value = value;
    }

    private String value;
}