package io.github.dayal96.runtime.expr;

import io.github.dayal96.expression.cons.ConsPair;
import io.github.dayal96.primitive.Primitive;
import java.util.LinkedList;
import java.util.List;

public class ListSerialize extends PartialSerializer<List<Object>> {

  private static final ListSerialize instance = new ListSerialize();

  private ListSerialize() {}

  public static ListSerialize getInstance() {
    return instance;
  }

  @Override
  public List<Object> visitConsPair(ConsPair expr) {
    List<Object> otherInList = expr.rest.accept(this);
    otherInList.add(expr.first.accept(ExprSerialize.getInstance()));
    return otherInList;
  }

  @Override
  public List<Object> visitPrimitive(Primitive expr) {
    return new LinkedList<>();
  }
}
