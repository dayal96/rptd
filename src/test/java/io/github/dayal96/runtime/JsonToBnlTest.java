package io.github.dayal96.runtime;

import static org.springframework.test.util.AssertionErrors.assertEquals;

import io.github.dayal96.antlr.JsonLexer;
import io.github.dayal96.antlr.JsonParser;
import io.github.dayal96.expression.Expression;
import io.github.dayal96.expression.local.Local;
import io.github.dayal96.expression.local.StructDefinition;
import io.github.dayal96.expression.struct.StructObject;
import io.github.dayal96.expression.type.NilType;
import io.github.dayal96.expression.type.StructType;
import io.github.dayal96.jsonparser.JsonToBnlVisitor;
import io.github.dayal96.primitive.number.Rational;
import io.github.dayal96.primitive.string.MyString;
import java.util.List;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;

public class JsonToBnlTest {

  public static Expression parseJson(String json) {
    var lexer = new JsonLexer(CharStreams.fromString(json));
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);
    var jsonParser = new JsonParser(tokenStream);
    var jsonToBnlVisitor = new JsonToBnlVisitor();
    return jsonToBnlVisitor.visit(jsonParser.json());
  }

  @Test
  public void testPrimitives() throws Exception {
    List<TestCase> testCases = List.of(
        new TestCase("\"string\"", new MyString("string")),
        new TestCase("123", new Rational(123)),
        new TestCase("-123", new Rational(-123)),
        new TestCase("12.25", new Rational(1225, 100)),
        new TestCase("-0.314", new Rational(-314, 1000)),
        new TestCase("{\"key\": \"value\"}",
            new Local(List.of(new StructDefinition("request-body", List.of("key"))),
            new StructObject(new StructType("request-body", List.of(NilType.NIL), List.of("key")),
                List.of(new MyString("value"))))));

    for (int i = 0; i < testCases.size(); i++) {
      testCases.get(i).verify(i);
    }
  }

  private static class TestCase {
    public final String json;
    public final Expression bnl;

    public TestCase(String json, Expression bnl) {
      this.json = json;
      this.bnl = bnl;
    }

    public void verify(int index) {
      assertEquals("Test Case [" + index + "] failed : ", bnl.toString(), parseJson(json).toString());
    }
  }
}
