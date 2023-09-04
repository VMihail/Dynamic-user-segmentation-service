package com.vmichael.springbootapp.controller;

import com.vmichael.springbootapp.dto.SegmentDTO;
import com.vmichael.springbootapp.entity.Segment;
import com.vmichael.springbootapp.repository.SegmentRepository;
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
public class SegmentController {
  private final SegmentRepository segmentRepository;

  @Autowired
  public SegmentController(final SegmentRepository segmentRepository) {
    this.segmentRepository = Objects.requireNonNull(segmentRepository);
  }

  @GetMapping("/segments")
  public ResponseEntity<List<SegmentDTO>> getAllSegments() {
    return ResponseEntity
     .status(HttpStatus.OK)
     .body(segmentRepository.findAll().stream().map(SegmentDTO::SegmentDTOOf).toList());
  }

  @PostMapping("/segments")
  public ResponseEntity<Segment> createSegment(@RequestBody final Segment segment) {
    segmentRepository.save(segment);
    return ResponseEntity
     .status(HttpStatus.CREATED)
     .body(segment);
  }

  @DeleteMapping("/segments/{id}")
  public ResponseEntity<?> deleteSegment(@PathVariable final Long id) {
    segmentRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  /*@PutMapping("/segments/{id}")
  public ResponseEntity<?> updateSegment(
   @PathVariable final Long id,
   @RequestBody final Segment newSegment) {
    final var result = segmentRepository.findById(id);
    if (result.isEmpty()) {
      throw new IllegalArgumentException("Segment not found");
    }
    final var updatedSegment = result.map(segment -> {
      segment.setName(newSegment.getName());
      return segmentRepository.save(segment);
    });
    return ResponseEntity
     .status(HttpStatus.OK)
     .body(updatedSegment);
  }*/
}
