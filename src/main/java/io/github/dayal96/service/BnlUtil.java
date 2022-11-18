package io.github.dayal96.service;

import io.github.dayal96.antlr.JsonLexer;
import io.github.dayal96.antlr.JsonParser;
import io.github.dayal96.expression.Expression;
import io.github.dayal96.interpreter.Interpreter;
import io.github.dayal96.jsonparser.JsonToBnlVisitor;
import io.github.dayal96.primitive.string.MyString;
import io.github.dayal96.runtime.expr.ExprSerialize;
import io.github.dayal96.runtime.RptdEvaluator;
import io.github.dayal96.util.CryptoUtil;
import io.github.dayal96.util.MapperUtil;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.springframework.stereotype.Service;

@Service
public class BnlUtil {

  public static String processBnl(Map<String, String> variables, String sourceCode, String uri,
      Optional<String> requestBody) {
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

    requestBody.ifPresent(s -> pathVariables.append("(define requestBody\n")
        .append(requestBodyBnl(s))
        .append("\n)"));

    String code = pathVariables + "\n" + sourceCode;
    try {
      Object result = interpreter.interpret(new StringReader(code.trim()))
          .orElse(new MyString("")).accept(ExprSerialize.getInstance());
      return MapperUtil.getInstance().writeValueAsString(result);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static String requestBodyBnl(String requestBody) {
    var lexer = new JsonLexer(CharStreams.fromString(requestBody));
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);
    var jsonParser = new JsonParser(tokenStream);
    var jsonToBnlVisitor = new JsonToBnlVisitor();
    Expression requestBodyValue = jsonToBnlVisitor.visit(jsonParser.value());
    return requestBodyValue.toString();
  }
}
