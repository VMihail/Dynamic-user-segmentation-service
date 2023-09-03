package com.vmichael.springbootapp.repository;

import com.vmichael.springbootapp.entity.Segment;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeSegmentRepository extends AbstractRepository {
  public EmployeeSegmentRepository() throws SQLException, IOException, ParseException {
    super();
    tableName = super.dataBaseConfig.getEmployeeSegmentTableName();
  }

  public void addEmployeeToSegment(final long employeeId, final String segmentName) {
    try {
      jdbcConnection.executePreparedQuery(
       String.format("insert into %s (employee_id, segment_id, date_added)" +
        " values (?, (select segment_id from segment where segment_name = ?), now());", tableName),
       (statement) -> {
         statement.setLong(1, employeeId);
         statement.setString(2, segmentName);
         statement.execute();
       });
    } catch (final SQLException e) {
      System.out.println("Failed to save user " + e.getMessage());
    }
  }

  public void removeEmployeeFromSegment(final long employeeId, final String segmentName) {
    try {
      jdbcConnection.executePreparedQuery(
       String.format("update %s set date_removed = now()" +
        " where employee_id = ? and segment_id = (select segment_id from segment where segment_name = ?)", tableName),
       (statement) -> {
         statement.setLong(1, employeeId);
         statement.setString(2, segmentName);
         statement.execute();
       });
    } catch (final SQLException e) {
      System.out.println("Failed to save user " + e.getMessage());
    }
  }

  public List<Segment> getEmployeeSegments(final long employeeId) {
    final List<Segment> result = new ArrayList<>();
    try {
      jdbcConnection.executePreparedQuery(
       String.format("select segment_id, segment_name from %s" +
        " inner join segment using(segment_id) where employee_id = ?;", tableName),
       (statement) -> statement.setLong(1, employeeId),
       (resultSet) -> {
         while (resultSet.next()) {
           result.add(new Segment(resultSet.getLong(1), resultSet.getString(2)));
         }
       }
      );
    } catch (final SQLException e) {
      System.out.println(e.getMessage());
    }
    return result;
  }
}
