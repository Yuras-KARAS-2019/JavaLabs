package com.kekwetors.util;

import com.kekwetors.config.SecurityConfig;
import com.kekwetors.dao.model.EmployeeRole;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.Objects.nonNull;

public class SecurityUtils {

    public static boolean isSecurityPage(HttpServletRequest request) {
        String urlPattern = UrlPatternUtils.getUrlPattern(request);

        for (EmployeeRole role : EmployeeRole.values()) {
            List<String> urlPatterns = SecurityConfig.getUrlPatternsForRole(role);
            if (nonNull(urlPatterns) && urlPatterns.contains(urlPattern)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasPermission(HttpServletRequest request) {
        String urlPattern = UrlPatternUtils.getUrlPattern(request);

        for (EmployeeRole role : EmployeeRole.values()) {
            if (!request.isUserInRole(role.toString())) {
                continue;
            }

            List<String> urlPatterns = SecurityConfig.getUrlPatternsForRole(role);
            if (nonNull(urlPatterns) && urlPatterns.contains(urlPattern)) {
                return true;
            }
        }
        return false;
    }

}
