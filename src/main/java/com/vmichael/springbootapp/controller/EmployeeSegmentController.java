package com.vmichael.springbootapp.controller;

import com.vmichael.springbootapp.dto.SegmentDTO;
import com.vmichael.springbootapp.repository.EmployeeSegmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class EmployeeSegmentController {
  private final EmployeeSegmentRepository employeeSegmentRepository;

  @Autowired
  public EmployeeSegmentController(final EmployeeSegmentRepository employeeSegmentRepository) {
    this.employeeSegmentRepository = Objects.requireNonNull(employeeSegmentRepository);
  }

  @GetMapping("/employeeSegment/{employeeId}")
  public ResponseEntity<List<SegmentDTO>> getSegmentsByEmployee(@PathVariable final Long employeeId) {
    final var result = employeeSegmentRepository
     .getEmployeeSegments(employeeId)
     .stream().map(SegmentDTO::SegmentDTOOf).toList();
    return ResponseEntity
     .status(HttpStatus.OK)
     .body(result);
  }

  @PostMapping("/employeeSegment/{employeeId}")
  public ResponseEntity<?> addEmployeeToSegment(
   @PathVariable final Long employeeId,
   @RequestParam final String segmentName
  ) {
    employeeSegmentRepository.addEmployeeToSegment(employeeId, segmentName);
    return ResponseEntity
     .status(HttpStatus.OK)
     .body("added");
  }

  @PutMapping("/employeeSegment/{employeeId}")
  public ResponseEntity<?> removeEmployeeFromSegment(
   @PathVariable final Long employeeId,
   @RequestParam final String segmentName
  ) {
    employeeSegmentRepository.removeEmployeeFromSegment(employeeId, segmentName);
    return ResponseEntity
     .status(HttpStatus.OK)
     .body("removed");
  }
}
