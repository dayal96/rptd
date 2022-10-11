package io.github.dayal96.service;

import io.github.dayal96.expression.Expression;
import io.github.dayal96.interpreter.Interpreter;
import io.github.dayal96.primitive.string.MyString;
import io.github.dayal96.runtime.JsonSerialize;
import io.github.dayal96.runtime.RptdEvaluator;
import java.io.StringReader;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class BnlService {

  public String testBnlObject() {
    String code = """
        (cons (cons "type" "object")
              (cons (cons "field" "value")
                    (cons (cons "intfield" 1)
                          false)))
        """;

    var interpreter = new Interpreter<>(new RptdEvaluator());

    try {
      Optional<Expression> result = interpreter.interpret(new StringReader(code.trim()));
      return result.orElse(new MyString("")).accept(JsonSerialize.getInstance());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
