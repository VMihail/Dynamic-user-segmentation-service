package com.vmichael.springbootapp.dto;

import com.vmichael.springbootapp.entity.Segment;

public record SegmentDTO(long id, String name) {
  public static SegmentDTO SegmentDTOOf(final Segment segment) {
    return new SegmentDTO(segment.getId(), segment.getName());
  }
}
