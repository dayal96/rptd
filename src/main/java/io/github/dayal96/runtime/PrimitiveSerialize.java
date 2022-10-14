package io.github.dayal96.runtime;

import io.github.dayal96.primitive.PrimitiveVisitor;
import io.github.dayal96.primitive.bool.MyBoolean;
import io.github.dayal96.primitive.number.MyNumber;
import io.github.dayal96.primitive.string.MyString;

public class PrimitiveSerialize implements PrimitiveVisitor<Object> {

  private static final PrimitiveSerialize instance = new PrimitiveSerialize();

  private PrimitiveSerialize() {}

  public static PrimitiveSerialize getInstance() {
    return instance;
  }

  @Override
  public Object visitMyBoolean(MyBoolean myBoolean) {
    return myBoolean.truth();
  }

  @Override
  public <N> Object visitMyNumber(MyNumber<N> myNumber) {
    String number = myNumber.toString();
    try {
      return Integer.parseInt(number);
    } catch (NumberFormatException e) {
      // do nothing
    }

    return Double.parseDouble(number);
  }

  @Override
  public Object visitMyString(MyString myString) {
    return myString.toString();
  }
}
