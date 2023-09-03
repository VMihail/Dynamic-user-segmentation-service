package com.vmichael.userSegmentationService.controller;

import com.vmichael.userSegmentationService.entity.Employee;
import com.vmichael.userSegmentationService.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class EmployeeController {
  private final EmployeeRepository employeeRepository;

  @Autowired
  public EmployeeController(final EmployeeRepository employeeService) {
    this.employeeRepository = Objects.requireNonNull(employeeService);
  }

  @GetMapping("/employees")
  public ResponseEntity<List<Employee>> getAll() {
    return ResponseEntity
     .status(HttpStatus.OK)
     .body(employeeRepository.findAll());
  }

  @PostMapping("/employees")
  public ResponseEntity<?> createEmployee(@RequestBody final Employee employee) {
    try {
      employeeRepository.save(employee);
      return ResponseEntity
       .status(HttpStatus.OK)
       .body(employee);
    } catch (final DataAccessException e) {
      return ResponseEntity
       .badRequest()
       .body(e.getMessage());
    }
  }
}
