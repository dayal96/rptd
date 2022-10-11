package io.github.dayal96.runtime;

import io.github.dayal96.expression.cons.ConsPair;
import io.github.dayal96.primitive.Primitive;
import java.util.LinkedList;
import java.util.List;

public class JsonListSerialize extends PartialSerializer<List<String>> {

  private static final JsonListSerialize instance = new JsonListSerialize();

  private JsonListSerialize() {}

  public static JsonListSerialize getInstance() {
    return instance;
  }

  @Override
  public List<String> visitConsPair(ConsPair expr) {
    List<String> otherInList = expr.rest.accept(this);
    otherInList.add(expr.first.accept(JsonSerialize.getInstance()));
    return otherInList;
  }

  @Override
  public List<String> visitPrimitive(Primitive expr) {
    return new LinkedList<>();
  }
}
