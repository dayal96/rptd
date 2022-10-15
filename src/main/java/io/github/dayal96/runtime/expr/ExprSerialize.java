package io.github.dayal96.runtime.expr;

import io.github.dayal96.expression.cons.ConsPair;
import io.github.dayal96.primitive.Primitive;
import java.util.Collections;
import java.util.List;

public class ExprSerialize extends PartialSerializer<Object> {

  private static final ExprSerialize instance = new ExprSerialize();

  private ExprSerialize() {}

  public static ExprSerialize getInstance() {
    return instance;
  }

  @Override
  public Object visitConsPair(ConsPair consPair) {
    if (consPair.accept(IsObject.getInstance())) {
      return consPair.rest.accept(ObjectSerialize.getInstance());
    } else if (consPair.accept(IsList.getInstance())) {
      List<Object> list = consPair.accept(ListSerialize.getInstance());
      Collections.reverse(list);
      return list;
    }
    return null;
  }

  @Override
  public Object visitPrimitive(Primitive primitive) {
    return primitive.accept(PrimitiveSerialize.getInstance());
  }
}
