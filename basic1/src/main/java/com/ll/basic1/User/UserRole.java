package com.ll.basic1.User;

import lombok.Getter;


@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"), // 관리자
    ACADEMY("ROLE_ACDEMY"), // 학원
    USER("ROLE_USER"); // 사용자

    UserRole(String value) {
        this.value = value;
    }

    private String value;
}