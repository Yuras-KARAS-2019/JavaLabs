package com.kekwetors.web.filter;

import com.kekwetors.dao.model.Employee;
import com.kekwetors.dao.model.EmployeeRole;
import com.kekwetors.util.SecurityUtils;
import com.kekwetors.util.UserUtils;
import com.kekwetors.web.request.UserRoleRequestWrapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class SecurityFilter implements Filter {

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;

    String servletPath = request.getServletPath();
    Employee loggedInUser = UserUtils.getLoggedInUser(request.getSession());

    if (servletPath.equals("/login")) {
      chain.doFilter(request, response);
      return;
    }
    HttpServletRequest wrappedRequest = request;

    if (loggedInUser != null) {
      String userLogin = loggedInUser.getLogin();
      EmployeeRole employeeRole = loggedInUser.getRole();

      wrappedRequest = new UserRoleRequestWrapper(userLogin, employeeRole, request);
    }

    if (SecurityUtils.isSecurityPage(request)) {
      if (loggedInUser == null) {
        String requestUri = request.getRequestURI();
        int redirectId = UserUtils.storeRedirectAfterLoginUrl(request.getSession(), requestUri);

        response.sendRedirect(wrappedRequest.getContextPath() + "/login?redirectId=" + redirectId);
        return;
      }

      boolean hasPermission = SecurityUtils.hasPermission(wrappedRequest);
      if (!hasPermission) {
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/accessDeniedView.jsp");
        dispatcher.forward(request, response);
        return;
      }
    }

    chain.doFilter(wrappedRequest, response);
  }

}
