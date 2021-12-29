package com.kekwetors.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {
  private Integer id;
  private String name;
  private EmployeeRole role;

  private String login;
  private String password;
}
