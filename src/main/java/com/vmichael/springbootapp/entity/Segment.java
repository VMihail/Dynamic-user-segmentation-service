package com.vmichael.springbootapp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Objects;

@Getter
@NoArgsConstructor
public class Segment {
  private long id;
  private String name;

  public Segment(@NonNull String name) {
    this.name = name;
  }

  public Segment(@NonNull long id, String name) {
    this(name);
    this.id = id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Segment segment = (Segment) o;
    return Objects.equals(id, segment.id) && Objects.equals(name, segment.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }
}
