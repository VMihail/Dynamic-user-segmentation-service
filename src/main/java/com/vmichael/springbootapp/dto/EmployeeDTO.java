package com.vmichael.springbootapp.dto;

import com.vmichael.springbootapp.entity.Employee;

public record EmployeeDTO(long id, String name, String email) {
  public static EmployeeDTO EmployeeDTOOf(final Employee employee) {
    return new EmployeeDTO(employee.getId(), employee.getName(), employee.getEmail());
  }
}
