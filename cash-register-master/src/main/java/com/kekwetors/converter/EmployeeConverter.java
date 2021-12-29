package com.kekwetors.converter;

import com.kekwetors.dao.model.Employee;
import com.kekwetors.web.dto.EmployeeDto;

public class EmployeeConverter implements Converter<EmployeeDto, Employee> {

  @Override
  public Employee toModel(EmployeeDto dto) {
    return new Employee(dto.getId(), dto.getName(), dto.getRole(), dto.getLogin(), dto.getPassword());
  }

  @Override
  public EmployeeDto toDto(Employee model) {
    return new EmployeeDto(model.getId(), model.getName(), model.getRole(), model.getLogin(), model.getPassword());
  }

}
