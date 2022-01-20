package com.kekwetors.dao.model;

import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum EmployeeRole {
    CASHIER("cashier"),
    SENIOR_CASHIERS("senior cashier"),
    MERCHANDISE_EXPERT("merchandise expert");

    private final String name;

    public static EmployeeRole fromString(String role) {
        return Arrays.stream(EmployeeRole.values())
                .filter(x -> x.name.equals(role))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Can't get role from string " + role));
    }

    @Override
    public String toString() {
        return name;
    }
}
