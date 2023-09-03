package com.vmichael.springbootapp.repository;

import com.vmichael.springbootapp.entity.Segment;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SegmentRepository extends AbstractRepository {
  public SegmentRepository() throws SQLException, IOException, ParseException {
    super();
    tableName = super.dataBaseConfig.getSegmentTableName();
  }

  public List<Segment> findAll(){
    final List<Segment> result = new ArrayList<>();
    try {
      jdbcConnection.executeQuery(
       String.format("select * from %s;", tableName),
       (resultSet) -> {
         while (resultSet.next()) {
           result.add(new Segment(
            resultSet.getInt(1),
            resultSet.getString(2)));
         }
       }
      );
    } catch (final SQLException e) {
      System.out.println();
    }
    return result;
  }

  public Segment save(final Segment employee) {
    try {
      jdbcConnection.executePreparedQuery(
       String.format("insert into %s (segment_name) values (?);", tableName),
       (statement) -> {
         statement.setString(1, employee.getName());
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
       String.format("delete from %s where segment_id = ?;", tableName),
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
