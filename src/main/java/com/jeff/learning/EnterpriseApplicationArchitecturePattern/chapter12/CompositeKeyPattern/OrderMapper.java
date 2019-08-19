package com.jeff.learning.EnterpriseApplicationArchitecturePattern.chapter12.CompositeKeyPattern;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * orders (ID int PK, customer varchar)
 * line_items (OrderID int, seq int, amount int, product varchar) = PK(OrderID, seq)
 */

public class OrderMapper extends AbstractMapper {

  public OrderMapper() {
  }

  public Order find(Key key) {
    return (Order) this.abstractFind(key);
  }

  public Order find(Long id) {
    return find(new Key(id));
  }

  @Override
  protected String findStatementString() {
    return "SELECT * FROM orders WHERE id = ?";
  }

  @Override
  protected DomainObjectWithKey doLoad(Key key, ResultSet rs) throws SQLException {
    String customer = rs.getString("customer");
    Order result = new Order(key, customer);
    MapperRegistry.lineItem().loadAllLineItemFor(result);
    return result;
  }
}
