package com.jeff.learning.EnterpriseApplicationArchitecturePattern.chapter12.CompositeKeyPattern;

import com.jeff.learning.EnterpriseApplicationArchitecturePattern.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LineItemMapper extends AbstractMapper {

  public LineItem find(long orderId, long seq) {
    Key key = new Key(new Long(orderId), new Long(seq));
    return (LineItem) this.abstractFind(key);
  }

  @Override
  protected String findStatementString() {
    return "SELECT * FROM line_items WHERE (orderId=?) and (seq =?)";
  }

  @Override
  protected void loadFindStatement(Key key, PreparedStatement finder) throws SQLException {
    finder.setLong(1, orderId(key));
    finder.setLong(2, sequenceNumber(key));
  }

  @Override
  protected DomainObjectWithKey doLoad(Key key, ResultSet rs) throws SQLException {
    Order theOrder = MapperRegistry.order().find(orderId(key));
    return doLoad(key, rs, theOrder);
  }

  protected DomainObjectWithKey doLoad(Key key, ResultSet rs, Order order) throws SQLException {

    LineItem lineItem;
    int amount = rs.getInt("amount");
    String product = rs.getString("product");
    lineItem = new LineItem(key, amount, product);
    order.addLineItem(lineItem);
    return lineItem;
  }

  @Override
  protected Key createKey(ResultSet rs) throws SQLException {
    return new Key(rs.getLong("orderId"), rs.getLong("seq"));
  }

  private long orderId(Key key) {
    return key.longValue(0);
  }

  private long sequenceNumber(Key key) {
    return key.longValue(1);
  }

  public void loadAllLineItemFor(Order order) {

    PreparedStatement stmt = null;
    ResultSet rs = null;
    stmt = DB.prepare(findForOrederString());

    try {
      stmt.setLong(1, order.getKey().longValue());
      rs = stmt.executeQuery();
      while (rs.next()) {
        load(rs, order);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private DomainObjectWithKey load(ResultSet rs, Order order) throws SQLException {
    Key key = createKey(rs);
    if (loadMap.containsKey(key)) {
      return loadMap.get(key);
    }

    DomainObjectWithKey result = doLoad(key, rs, order);
    loadMap.put(key, result);
    return result;
  }

  private String findForOrederString() {
    return "SELECT * from line_items Where orderId= ?";
  }
}
