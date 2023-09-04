package com.vmichael.springbootapp.repository;

import com.vmichael.springbootapp.config.DataBaseConfig;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.SQLException;

public abstract class AbstractRepository {
  protected final DataBaseConfig dataBaseConfig;
  protected final JdbcTemplate jdbcConnection;
  protected String tableName;

  protected AbstractRepository() throws IOException, ParseException, SQLException {
    this.dataBaseConfig = DataBaseConfig.of();
    this.jdbcConnection = new JdbcTemplate(
     dataBaseConfig.url(),
     dataBaseConfig.userName(),
     dataBaseConfig.password());
  }
}
