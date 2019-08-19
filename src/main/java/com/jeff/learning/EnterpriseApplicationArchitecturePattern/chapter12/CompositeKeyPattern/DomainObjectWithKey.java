package com.jeff.learning.EnterpriseApplicationArchitecturePattern.chapter12.CompositeKeyPattern;

public class DomainObjectWithKey {

  private Key key;

  public DomainObjectWithKey() {
  }

  public DomainObjectWithKey(Key id) {
    this.key = id;
  }

  public Key getKey() {
    return key;
  }

  public void setKey(Key key) {
    this.key = key;
  }
}
