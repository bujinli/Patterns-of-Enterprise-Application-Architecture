package com.jeff.learning.EnterpriseApplicationArchitecturePattern;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DB {
  public static PreparedStatement prepare(String statementString) {
    return new FakePreparedStatement();
  }

  public static void cleanUp(PreparedStatement findStatement, ResultSet rs) {
  }
}
