package io.github.dayal96.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Objects;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public class MapperUtil {
  private static ObjectMapper mapper;

  public static ObjectMapper getInstance() {
    if (Objects.isNull(mapper)) {
      mapper = new Jackson2ObjectMapperBuilder().build();
    }

    return mapper;
  }
}
