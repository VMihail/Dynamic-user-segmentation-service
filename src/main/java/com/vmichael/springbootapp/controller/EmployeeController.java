package com.vmichael.springbootapp.controller;

import com.vmichael.springbootapp.dto.EmployeeDTO;
import com.vmichael.springbootapp.entity.Employee;
import com.vmichael.springbootapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class EmployeeController {
  private final EmployeeRepository employeeRepository;

  @Autowired
  public EmployeeController(final EmployeeRepository employeeRepository) {
    this.employeeRepository = Objects.requireNonNull(employeeRepository);
  }

  @GetMapping("/employees")
  public ResponseEntity<List<EmployeeDTO>> getAllEmployee() {
    return ResponseEntity
     .status(HttpStatus.OK)
     .body(employeeRepository.findAll().stream().map(EmployeeDTO::EmployeeDTOOf).toList());
  }

  @PostMapping("/employees")
  public ResponseEntity<Employee> createEmployee(@RequestBody final Employee employee) {
    employeeRepository.save(employee);
    return ResponseEntity
     .status(HttpStatus.CREATED)
     .body(employee);
  }

  @DeleteMapping("/employees/{id}")
  public ResponseEntity<?> deleteEmployee(@PathVariable final Long id) {
    employeeRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  /*@PutMapping("/employees/{id}")
  public ResponseEntity<?> updateEmployee(
   @PathVariable final Long id,
   @RequestBody final Employee newEmployee) {
    final var result = employeeRepository.findById(id);
    if (result.isEmpty()) {
      throw new IllegalArgumentException(String.format("Employee not found: <%s>", result));
    }
    final var updatedEmployee = result
     .map(employee -> {
       employee.setName(newEmployee.getName());
       employee.setEmail(newEmployee.getEmail());
       return employeeRepository.save(employee);
     });
    return ResponseEntity
     .status(HttpStatus.OK)
     .body(updatedEmployee);
  }*/
}
