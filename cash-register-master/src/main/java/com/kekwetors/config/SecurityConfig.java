package com.kekwetors.config;

import com.kekwetors.dao.model.EmployeeRole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecurityConfig {

    private static final Map<EmployeeRole, List<String>> availableMappings = new HashMap<>();

    static {
        init();
    }

    private static void init() {
        availableMappings.put(EmployeeRole.CASHIER, List.of("/userInfo", "/api/v0/receipts", "/api/v0/products"));
        availableMappings.put(EmployeeRole.SENIOR_CASHIERS, List.of("/userInfo", "/api/v0/receipts", "/api/v0/products"));
        availableMappings.put(EmployeeRole.MERCHANDISE_EXPERT, List.of("/userInfo", "/api/v0/products"));
    }

    public static List<String> getUrlPatternsForRole(EmployeeRole role) {
        return availableMappings.get(role);
    }
}
