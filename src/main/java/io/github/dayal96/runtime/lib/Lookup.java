package io.github.dayal96.runtime.lib;

import io.github.dayal96.environment.Environment;
import io.github.dayal96.expression.Expression;
import io.github.dayal96.expression.operator.AOperator;
import io.github.dayal96.expression.struct.StructObject;
import io.github.dayal96.expression.type.PrimType;
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

    StructObject object = (StructObject) obj;
    int accessor = object.structType.fields.indexOf(unescape(field.toString()));

    if (accessor >= 0 && accessor < object.values.size()) {
      return object.values.get(accessor);
    }

    throw new Exception("Field " + field + " not found on struct " + obj.getType());
  }

  @Override
  public String toString() {
    return "lookup";
  }

  private static String unescape(String str) {
    return str.substring(1,str.length() - 1).replaceAll("\"", "\"").replaceAll("\\\\", "\\");
  }
}
