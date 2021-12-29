package com.kekwetors.dao;

import com.kekwetors.dao.model.Employee;

import java.util.List;

public interface EmployeeDao {
  List<Employee> getAllEmployees();
  Employee getEmployeeById(int id);
  Employee getEmployeeByLogin(String login);
  Employee insertEmployee(Employee employee);
  void updateEmployee(Employee employee);
  void deleteEmployee(int id);
}
