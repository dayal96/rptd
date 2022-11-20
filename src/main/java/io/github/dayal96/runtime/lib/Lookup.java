package io.github.dayal96.runtime.lib;

import static io.github.dayal96.util.StringUtil.removeQuotes;

import io.github.dayal96.environment.Environment;
import io.github.dayal96.expression.Expression;
import io.github.dayal96.expression.operator.AOperator;
import io.github.dayal96.expression.struct.StructObject;
import io.github.dayal96.expression.type.PrimType;
import io.github.dayal96.runtime.expr.PartialVisitor;
import java.util.List;

public class Lookup extends AOperator {

  @Override
  public Expression evaluate(List<Expression> operands, Environment environment) throws Exception {
    if (operands.size() != 2) {
      throw new Exception("lookup : expected 2 arguments, found " + operands.size());
    }

    Expression obj = operands.get(0).evaluate(environment);
    Expression field = operands.get(1).evaluate(environment);

    PrimType.STRING.join(field.getType());
    if (!(obj instanceof StructObject)) {
      throw new Exception("Expected a struct, found " + obj.getType());
    }

    return obj.accept(new LookupVisitor(removeQuotes(field.toString())));
  }

  @Override
  public String toString() {
    return "lookup";
  }

  private static class LookupVisitor extends PartialVisitor<Expression> {

    private final String field;

    private LookupVisitor(String field) {
      this.field = field;
    }

    @Override
    public Expression visitStruct(StructObject object) {
      int accessor = object.structType.fields.indexOf(field);
      if (accessor >= 0 && accessor < object.values.size()) {
        return object.values.get(accessor);
      }
      throw new RuntimeException("Field " + field + " not found on struct " + object.getType());
    }
  }
}
