package io.github.dayal96.runtime;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.dayal96.expression.cons.ConsPair;
import io.github.dayal96.primitive.Primitive;
import io.github.dayal96.util.MapperUtil;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class JsonSerialize extends PartialSerializer<String> {

  private static final JsonSerialize instance = new JsonSerialize();

  private JsonSerialize() {}

  public static JsonSerialize getInstance() {
    return instance;
  }

  @Override
  public String visitConsPair(ConsPair consPair) {
    if (consPair.accept(IsObject.getInstance())) {
      Map<String, String> object = consPair.rest.accept(JsonObjectSerialize.getInstance());
      try {
        return MapperUtil.getInstance().writeValueAsString(object);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    } else if (consPair.accept(IsList.getInstance())) {
      List<String> list = consPair.accept(JsonListSerialize.getInstance());
      Collections.reverse(list);
      try {
        return MapperUtil.getInstance().writeValueAsString(list);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }

    return null;
  }

  @Override
  public String visitPrimitive(Primitive primitive) {
    return primitive.toString();
  }
}
