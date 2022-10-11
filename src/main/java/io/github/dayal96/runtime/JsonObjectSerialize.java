package io.github.dayal96.runtime;

import static io.github.dayal96.expression.cons.ConsPair.CONS_PAIR_TYPE;

import io.github.dayal96.expression.cons.ConsPair;
import io.github.dayal96.primitive.Primitive;
import java.util.HashMap;
import java.util.Map;

public class JsonObjectSerialize extends PartialSerializer<Map<String, String>> {

  private static final JsonObjectSerialize instance = new JsonObjectSerialize();

  private JsonObjectSerialize() {}

  public static JsonObjectSerialize getInstance() {
    return instance;
  }

  @Override
  public Map<String, String> visitConsPair(ConsPair expr) {
    Map<String, String> result = expr.rest.accept(this);
    if (expr.first.getType().equals(CONS_PAIR_TYPE)) {
      ConsPair pair = (ConsPair) expr.first;
      result.put(pair.first.toString(), pair.rest.accept(JsonSerialize.getInstance()));
    }
    return result;
  }

  @Override
  public Map<String, String> visitPrimitive(Primitive expr) {
    return new HashMap<>();
  }
}
