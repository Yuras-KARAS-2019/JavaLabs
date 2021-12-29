package com.kekwetors.web;

import com.kekwetors.dao.EmployeeDao;
import com.kekwetors.dao.model.Employee;
import lombok.SneakyThrows;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/v0/cashiers")
public class EmployeeServlet extends AbstractBaseServlet {

  private final EmployeeDao employeeDao = factory.getEmployeeDao();

  @Override
  @SneakyThrows
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    super.doGet(req, resp);
  }

  @Override
  @SneakyThrows
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    super.doPost(req, resp);
  }

  @Override
  @SneakyThrows
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
    super.doPut(req, resp);
  }

  @Override
  @SneakyThrows
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
    super.doDelete(req, resp);
  }

}
