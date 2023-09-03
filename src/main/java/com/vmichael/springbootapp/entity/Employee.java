package com.vmichael.springbootapp.entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.Objects;

@Getter
@NoArgsConstructor
public class Employee {
  private long id;
  private String name;
  private String email;

  public Employee(@NonNull String name, @NonNull String email) {
    this.name = name;
    this.email = email;
  }

  public Employee(@NonNull long id, String name, String email) {
    this(name, email);
    this.id = id;
  }

  public void setName(@NonNull String name) {
    this.name = name;
  }

  public void setEmail(@NonNull String email) {
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
