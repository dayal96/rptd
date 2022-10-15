package io.github.dayal96.runtime.expr;

import static io.github.dayal96.expression.cons.ConsPair.CONS_PAIR_TYPE;

import io.github.dayal96.expression.cons.ConsPair;
import io.github.dayal96.primitive.Primitive;
import java.util.HashMap;
import java.util.Map;

public class ObjectSerialize extends PartialSerializer<Map<String, Object>> {

  private static final ObjectSerialize instance = new ObjectSerialize();

  private ObjectSerialize() {}

  public static ObjectSerialize getInstance() {
    return instance;
  }

  @Override
  public Map<String, Object> visitConsPair(ConsPair expr) {
    Map<String, Object> result = expr.rest.accept(this);
    if (expr.first.getType().equals(CONS_PAIR_TYPE)) {
      ConsPair pair = (ConsPair) expr.first;
      result.put(pair.first.toString(), pair.rest.accept(ExprSerialize.getInstance()));
    }
    return result;
  }

  @Override
  public Map<String, Object> visitPrimitive(Primitive expr) {
    return new HashMap<>();
  }
}
