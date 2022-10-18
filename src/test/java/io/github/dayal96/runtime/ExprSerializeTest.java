package io.github.dayal96.runtime;

import io.github.dayal96.expression.Expression;
import io.github.dayal96.interpreter.Interpreter;
import io.github.dayal96.runtime.expr.ExprSerialize;
import io.github.dayal96.util.MapperUtil;
import java.io.StringReader;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class ExprSerializeTest {

  private final Interpreter<Optional<Expression>> interpreter =
      new Interpreter<>(new RptdEvaluator());

  @Test
  public void testSerialize1() throws Exception {
    String code = """
        (define obj
        	(cons (cons "type" "object")
        		    (cons (cons "test2" "test2") #f)))

        (cons (cons "type" "object")
              (cons (cons "test1" "test1")
        	          (cons (cons "obj" obj) #f)))
        """;

    Optional<Expression> result = interpreter.interpret(new StringReader(code));

    if (result.isEmpty()) {
      assert false;
    }

    Object obj = result.get().accept(ExprSerialize.getInstance());
    System.out.println(MapperUtil.getInstance().writeValueAsString(obj));
  }

  @Test
  public void testSerialize2() throws Exception {
    Object obj = Map.of("test1", "test1");
    System.out.println(MapperUtil.getInstance().writeValueAsString(obj));
  }
}
