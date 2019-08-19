package com.jeff.learning.EnterpriseApplicationArchitecturePattern.chapter12.CompositeKeyPattern;

public class MapperRegistry {

  public static LineItemMapper lineItem() {
    return new LineItemMapper();
  }

  public static OrderMapper order() {
    return new OrderMapper();
  }
}
