package com.kekwetors.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kekwetors.dao.model.EmployeeRole;
import lombok.Value;

@Value
public class EmployeeDto {
  Integer id;
  String name;
  EmployeeRole role;
  String login;
  String password;

  @JsonCreator
  public EmployeeDto(@JsonProperty("id") Integer id,
                    @JsonProperty("name") String name,
                    @JsonProperty("availableAmount") EmployeeRole role,
                    @JsonProperty("login") String login,
                    @JsonProperty("password") String password) {
    this.id = id;
    this.name = name;
    this.role = role;
    this.login = login;
    this.password = password;
  }
}
