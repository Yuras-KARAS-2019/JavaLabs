package com.kekwetors.web.request;

import com.kekwetors.dao.model.EmployeeRole;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.security.Principal;

public class UserRoleRequestWrapper extends HttpServletRequestWrapper {

  private final String userLogin;
  private final EmployeeRole employeeRole;
  private final HttpServletRequest realRequest;

  public UserRoleRequestWrapper(String userLogin, EmployeeRole employeeRole, HttpServletRequest request) {
    super(request);
    this.userLogin = userLogin;
    this.employeeRole = employeeRole;
    this.realRequest = request;
  }

  @Override
  public boolean isUserInRole(String role) {
    if (employeeRole == null) {
      return this.realRequest.isUserInRole(role);
    }
    return employeeRole.toString().equals(role);
  }

  @Override
  public Principal getUserPrincipal() {
    if (this.userLogin == null) {
      return realRequest.getUserPrincipal();
    }
    return () -> userLogin;
  }
}
