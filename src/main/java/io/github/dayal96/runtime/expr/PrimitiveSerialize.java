package io.github.dayal96.runtime.expr;

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

    if (number.contains("/")) {
      int numEnd = number.indexOf('/');
      Double numerator = Double.parseDouble(number.substring(0, numEnd));
      Double denominator = Double.parseDouble(number.substring(numEnd + 1));
      return numerator / denominator;
    }

    return Double.parseDouble(number);
  }

  @Override
  public Object visitMyString(MyString myString) {
    return myString.value;
  }
}
