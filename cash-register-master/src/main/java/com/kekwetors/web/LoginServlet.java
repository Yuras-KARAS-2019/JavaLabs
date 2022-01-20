package com.kekwetors.web;

import com.kekwetors.dao.EmployeeDao;
import com.kekwetors.dao.model.Employee;
import com.kekwetors.util.UserUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@WebServlet("/login")
public class LoginServlet extends AbstractBaseServlet {

    private final EmployeeDao employeeDao = factory.getEmployeeDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String login = request.getParameter("login");
        String hashedPassword = UserUtils.getShaHash(request.getParameter("password"));
        log.info("{}", employeeDao.getAllEmployees());
        log.info("{}", employeeDao.getEmployeeByLogin(login));
        Employee userAccount = employeeDao.getEmployeeByLogin(login);

        if (isNull(userAccount) || !hashedPassword.equals(userAccount.getPassword())) {
            String errorMessage = "Invalid login or password";

            request.setAttribute("errorMessage", errorMessage);

            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");

            dispatcher.forward(request, response);
            return;
        }

        UserUtils.storeLoggedInUser(request.getSession(), userAccount);

        int redirectId = -1;

        try {
            redirectId = Integer.parseInt(request.getParameter("redirectId"));
        } catch (Exception e) {
        }

        String requestUri = UserUtils.getRedirectAfterLoginUrl(request.getSession(), redirectId);
        if (nonNull(requestUri)) {
            response.sendRedirect(requestUri);
        } else {
            response.sendRedirect(request.getContextPath() + "/userInfo");
        }

    }

}
