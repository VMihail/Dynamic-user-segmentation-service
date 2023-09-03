package com.vmichael.userSegmentationService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.springframework.lang.NonNull;

import java.util.Objects;

@Entity
public class Employee {
  @Id
  @GeneratedValue
  private long id;
  private String name;
  private String email;

  public Employee() {
  }

  public Employee(@NonNull String name, @NonNull String email) {
    this.name = name;
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Employee employee = (Employee) o;
    return id == employee.id && Objects.equals(name, employee.name) && Objects.equals(email, employee.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, email);
  }
}
