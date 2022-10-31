package io.github.dayal96.runtime.lib;

import io.github.dayal96.environment.Environment;
import io.github.dayal96.expression.Expression;
import io.github.dayal96.expression.cons.ConsPair;
import io.github.dayal96.expression.operator.AOperator;
import io.github.dayal96.primitive.string.MyString;
import io.github.dayal96.runtime.expr.IsList;
import java.util.List;

public class MakeObject extends AOperator {

  public MakeObject() {}

  @Override
  public Expression evaluate(List<Expression> operands, Environment environment) throws Exception {
    if (operands.size() != 1) {
      throw new Exception("make-object : expected 1 argument, found " + operands.size());
    }
    Expression evaluated = operands.get(0).evaluate(environment);

    if (!evaluated.accept(IsList.getInstance())) {
      throw new Exception("make-object : Type Mismatch - expected list as input");
    }

    return new ConsPair(new ConsPair(new MyString("type"), new MyString("object")), evaluated);
  }

  @Override
  public String toString() {
    return "make-object";
  }
}
