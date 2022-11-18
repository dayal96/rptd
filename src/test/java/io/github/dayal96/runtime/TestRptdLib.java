package io.github.dayal96.runtime;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.dayal96.environment.Environment;
import io.github.dayal96.environment.SymbolTable;
import io.github.dayal96.expression.Expression;
import io.github.dayal96.expression.cons.ConsPair;
import io.github.dayal96.expression.lambda.FunctionCall;
import io.github.dayal96.expression.operator.AOperator;
import io.github.dayal96.expression.struct.StructObject;
import io.github.dayal96.expression.type.NilType;
import io.github.dayal96.expression.type.StructType;
import io.github.dayal96.primitive.Empty;
import io.github.dayal96.primitive.number.Rational;
import io.github.dayal96.primitive.string.MyString;
import io.github.dayal96.runtime.lib.Lookup;
import io.github.dayal96.runtime.lib.MakeObject;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TestRptdLib {

  private static final Environment env = new SymbolTable();

  @Test
  public void testLookup() throws Exception {
    AOperator lookup = new Lookup();

    Expression obj1 = new StructObject(new StructType("posn", List.of(NilType.NIL, NilType.NIL), List.of("x", "y")),
        List.of(new Rational(14), new MyString("water")));

    assertEquals(new Rational(14), new FunctionCall(lookup, List.of(obj1, new MyString("x"))).evaluate(env));
    assertEquals(new MyString("water"), new FunctionCall(lookup, List.of(obj1, new MyString("y"))).evaluate(env));
  }

  @Test
  public void testMakeObject() throws Exception {
    AOperator makeObject = new MakeObject();
    AOperator lookup = new Lookup();

    Expression obj =
        new FunctionCall(makeObject, List.of(new ConsPair(new MyString("field-1"), new Rational(15)),
            new ConsPair(new MyString("field-2"), new MyString("value")),
            new ConsPair(new MyString("field-3"), new ConsPair(new Rational(15), Empty.EMPTY))))
            .evaluate(env);

    assertEquals(new Rational(15), new FunctionCall(lookup, List.of(obj, new MyString("field-1"))).evaluate(env));
    assertEquals(new MyString("value"), new FunctionCall(lookup, List.of(obj, new MyString("field-2"))).evaluate(env));
    assertEquals(new ConsPair(new Rational(15), Empty.EMPTY), new FunctionCall(lookup, List.of(obj, new MyString("field-3"))).evaluate(env));
  }
}
