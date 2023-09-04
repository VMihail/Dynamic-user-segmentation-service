package com.vmichael.springbootapp.repository;

import com.vmichael.springbootapp.entity.Employee;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository extends AbstractRepository {
  public EmployeeRepository() throws SQLException, IOException, ParseException {
    super();
    tableName = super.dataBaseConfig.employeeTableName();
  }

  public List<Employee> findAll() {
    final List<Employee> result = new ArrayList<>();
    try {
      jdbcConnection.executeQuery(
       String.format("select * from %s;", tableName),
       (resultSet) -> {
         while (resultSet.next()) {
           result.add(new Employee(
            resultSet.getInt(1),
            resultSet.getString(2),
            resultSet.getString(3)));
         }
       }
      );
    } catch (final SQLException e) {
      System.out.println();
    }
    return result;
  }

  public Employee save(final Employee employee) {
    try {
      jdbcConnection.executePreparedQuery(
       String.format("insert into %s (employee_name, employee_email) values (?, ?);", tableName),
       (statement) -> {
         statement.setString(1, employee.getName());
         statement.setString(2, employee.getEmail());
         statement.execute();
       });
    } catch (final SQLException e) {
      System.out.println("Failed to save user " + e.getMessage());
    }
    return employee;
  }

  public void deleteById(final long id) {
    try {
      jdbcConnection.executePreparedQuery(
       String.format("delete from %s where employee_id = ?;", tableName),
       (statement) -> {
         statement.setLong(1, id);
         statement.execute();
       }
      );
    } catch (final SQLException e) {
      System.out.println(e.getMessage());
    }
  }
}
