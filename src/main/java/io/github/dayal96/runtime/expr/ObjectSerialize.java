package io.github.dayal96.runtime.expr;

import static io.github.dayal96.expression.cons.ConsPair.CONS_PAIR_TYPE;

import io.github.dayal96.expression.Expression;
import io.github.dayal96.expression.cons.ConsPair;
import io.github.dayal96.expression.struct.StructObject;
import io.github.dayal96.primitive.Primitive;
import java.util.HashMap;
import java.util.Map;

public class ObjectSerialize extends PartialVisitor<Map<String, Object>> {

  private static final ObjectSerialize instance = new ObjectSerialize();

  private ObjectSerialize() {}

  public static ObjectSerialize getInstance() {
    return instance;
  }

  @Override
  public Map<String, Object> visitConsPair(ConsPair expr) {
    Map<String, Object> result = new HashMap<>();
    result.put(expr.first.toString(), expr.rest.accept(ExprSerialize.getInstance()));
    return result;
  }

  @Override
  public Map<String, Object> visitStruct(StructObject structObject) {
    Map<String, Object> result = new HashMap<>();
    var fieldIterator = structObject.structType.fields.iterator();
    var valueIterator = structObject.values.iterator();

    while (fieldIterator.hasNext() && valueIterator.hasNext()) {
      String fieldName = fieldIterator.next();
      Expression value = valueIterator.next();
      result.put(fieldName, value.accept(ExprSerialize.getInstance()));
    }

    return result;
  }

  @Override
  public Map<String, Object> visitPrimitive(Primitive expr) {
    return new HashMap<>();
  }
}
