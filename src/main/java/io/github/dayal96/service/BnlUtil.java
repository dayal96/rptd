package io.github.dayal96.service;

import io.github.dayal96.interpreter.Interpreter;
import io.github.dayal96.primitive.string.MyString;
import io.github.dayal96.runtime.expr.ExprSerialize;
import io.github.dayal96.runtime.RptdEvaluator;
import io.github.dayal96.util.CryptoUtil;
import io.github.dayal96.util.MapperUtil;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class BnlUtil {

  public static String processBnl(Map<String, String> variables, String sourceCode, String uri) {
    byte[] uribin = uri.getBytes(StandardCharsets.UTF_8);
    var interpreter = new Interpreter<>(new RptdEvaluator(CryptoUtil.sha256(uribin)));
    StringBuilder pathVariables = new StringBuilder();

    for (var key : variables.keySet()) {
      try {
        Long number = Long.parseLong(variables.get(key));
        pathVariables.append("(define ").append(key).append(" ")
            .append(number).append(")\n");
        continue;
      } catch (Exception e) {
        // Do nothing
      }
      pathVariables.append("(define ").append(key).append(" \"")
          .append(variables.get(key)).append("\")\n");
    }

    String code = pathVariables + "\n" + sourceCode;
    try {
      Object result = interpreter.interpret(new StringReader(code.trim()))
          .orElse(new MyString("")).accept(ExprSerialize.getInstance());
      return MapperUtil.getInstance().writeValueAsString(result);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
