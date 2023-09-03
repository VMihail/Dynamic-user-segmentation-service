package com.vmichael.userSegmentationService.repository;

import com.vmichael.userSegmentationService.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
