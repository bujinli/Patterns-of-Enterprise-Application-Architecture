package com.jeff.learning.EnterpriseApplicationArchitecturePattern.chapter12.CompositeKeyPattern;

// application pattern

public class Key {

  private Object[] fields;

  public Key(Object[] fields) {
    checkKeyNotNull(fields);
    this.fields = fields;
  }

  public Key(Object arg1, Object arg2) {
    this.fields = new Object[2];
    this.fields[0] = arg1;
    this.fields[1] = arg2;
  }

  public Key(long arg) {
    this.fields = new Object[1];
    this.fields[0] = arg;
  }

  public Key(Object arg) {
    checkKeyNotNull(arg);
    this.fields = new Object[1];
    this.fields[0] = arg;
  }

  private void checkKeyNotNull(Object... fields) {
    if (fields == null) {
      throw new IllegalArgumentException("Error: null argument");
    }

    for (Object field : fields) {
      if (field == null) {
        throw new IllegalArgumentException("Error: null argument");
      }
    }
  }

  public long longValue() {
    return this.longValue(0);
  }

  public long longValue(int i) {
    if (this.fields[i] instanceof Long) {
      return ((Long) this.fields[i]).longValue();
    } else {
      throw new RuntimeException("error");
    }
  }
}
