package com.kekwetors.web;

import com.kekwetors.converter.EmployeeConverter;
import com.kekwetors.converter.ProductConverter;
import com.kekwetors.dao.EmployeeDao;
import com.kekwetors.dao.model.Employee;
import com.kekwetors.util.UserUtils;
import com.kekwetors.web.dto.EmployeeDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@WebServlet("/registration")
public class RegistrationServlet extends AbstractBaseServlet{
    private static final EmployeeDao employeeDao = factory.getEmployeeDao();
    private static final EmployeeConverter employeeConverter = new EmployeeConverter();

    @Override
    @SneakyThrows
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/registrationView.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    @SneakyThrows
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        String JSONRequest = parseRequest(request);
        EmployeeDto employeeDto = objectMapper.readValue(JSONRequest, EmployeeDto.class);
        sendResponse(employeeConverter.toDto(employeeDao.insertEmployee(employeeConverter.toModel(employeeDto))), response);
    }
}
