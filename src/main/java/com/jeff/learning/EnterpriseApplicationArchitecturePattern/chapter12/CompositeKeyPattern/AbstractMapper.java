package com.jeff.learning.EnterpriseApplicationArchitecturePattern.chapter12.CompositeKeyPattern;

import com.jeff.learning.EnterpriseApplicationArchitecturePattern.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMapper {
  abstract protected String findStatementString();

  protected Map<Key, DomainObjectWithKey> loadMap = new HashMap<>();

  public DomainObjectWithKey abstractFind(Key key) {
    DomainObjectWithKey result = loadMap.get(key);
    if (result != null) {
      return result;
    }

    ResultSet rs = null;
    PreparedStatement findStatement = null;
    try {
      findStatement = DB.prepare(findStatementString());
      loadFindStatement(key, findStatement);

      rs = findStatement.executeQuery();
      rs.next();
      if (rs.isAfterLast()) {
        return null;
      }
      result = load(rs);
      return result;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      DB.cleanUp(findStatement, rs);
    }
  }

  //  protected DomainObjectWithKey load(ResultSet rs) {
  //    return new DomainObjectWithKey(new Key(100L));
  //  }

  protected void loadFindStatement(Key key, PreparedStatement findStatement) throws SQLException {
    findStatement.setLong(1, key.longValue());
  }

  protected DomainObjectWithKey load(ResultSet rs) throws SQLException {
    Key key = createKey(rs);
    if (loadMap.containsKey(key)) {
      return loadMap.get(key);
    }

    DomainObjectWithKey result = doLoad(key, rs);
    loadMap.put(key, result);
    return result;
  }

  protected abstract DomainObjectWithKey doLoad(Key key, ResultSet rs) throws SQLException;

  protected Key createKey(ResultSet rs) throws SQLException {
    return new Key(rs.getLong(1));
  }
}


