package com.vmichael.springbootapp.config;

import lombok.Getter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Getter
public final class DataBaseConfig {
  private static final Path pathConfig = Path.of("../springBootApp/src/main/resources/dataBaseConfig.json");

  private String url;
  private String userName;
  private String password;
  private String employeeTableName;
  private String segmentTableName;
  private String employeeSegmentTableName;

  public DataBaseConfig(
   String url,
   String userName,
   String password,
   String employeeTableName,
   String segmentTableName,
   String employeeSegmentTableName) {
    this.url = url;
    this.userName = userName;
    this.password = password;
    this.employeeTableName = employeeTableName;
    this.segmentTableName = segmentTableName;
    this.employeeSegmentTableName = employeeSegmentTableName;
  }

  public static DataBaseConfig of() throws IOException, ParseException {
    final StringBuilder jsonString = new StringBuilder();
    Files.readAllLines(pathConfig).forEach(jsonString::append);
    final JSONParser parser = new JSONParser();
    JSONObject jsonObject = (JSONObject) parser.parse(jsonString.toString());
    final String url = (String) jsonObject.get("url");
    final String userName = (String) jsonObject.get("user_name");
    final String password = (String) jsonObject.get("password");
    final String employeeTableName = (String) jsonObject.get("employee_table_name");
    final String segmentTableName = (String) jsonObject.get("segment_table_name");
    final String employeeSegmentTableName = (String) jsonObject.get("employee_segment_table_name");
    return new DataBaseConfig(url, userName, password, employeeTableName, segmentTableName, employeeSegmentTableName);
  }
}