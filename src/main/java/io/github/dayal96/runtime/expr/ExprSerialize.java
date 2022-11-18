package io.github.dayal96.runtime.expr;

import io.github.dayal96.expression.cons.ConsPair;
import io.github.dayal96.expression.operator.IsList;
import io.github.dayal96.expression.struct.StructObject;
import io.github.dayal96.primitive.Primitive;
import java.util.Collections;
import java.util.List;

public class ExprSerialize extends PartialVisitor<Object> {

  private static final ExprSerialize instance = new ExprSerialize();

  private ExprSerialize() {}

  public static ExprSerialize getInstance() {
    return instance;
  }

  @Override
  public Object visitConsPair(ConsPair consPair) {
    if (consPair.accept(IsList.LIST_CHECKER)) {
      List<Object> list = consPair.accept(ListSerialize.getInstance());
      Collections.reverse(list);
      return list;
    } else {
      return consPair.accept(ObjectSerialize.getInstance());
    }
  }

  @Override
  public Object visitStruct(StructObject structObject) {
    return structObject.accept(ObjectSerialize.getInstance());
  }

  @Override
  public Object visitPrimitive(Primitive primitive) {
    return primitive.accept(PrimitiveSerialize.getInstance());
  }
}
