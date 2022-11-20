package io.github.dayal96.runtime.lib;

import static io.github.dayal96.util.StringUtil.removeQuotes;

import io.github.dayal96.environment.Environment;
import io.github.dayal96.expression.Expression;
import io.github.dayal96.expression.cons.ConsPair;
import io.github.dayal96.expression.operator.AOperator;
import io.github.dayal96.expression.struct.StructObject;
import io.github.dayal96.expression.type.IType;
import io.github.dayal96.expression.type.NilType;
import io.github.dayal96.expression.type.PrimType;
import io.github.dayal96.expression.type.StructType;
import java.util.ArrayList;
import java.util.List;

public class MakeObject extends AOperator {

  @Override
  public Expression evaluate(List<Expression> operands, Environment environment) throws Exception {

    List<String> fields = new ArrayList<>();
    List<IType> types = new ArrayList<>();
    List<Expression> values = new ArrayList<>();

    for (var expr : operands) {
      Expression evaluated = expr.evaluate(environment);
      evaluated.getType().join(ConsPair.CONS_PAIR_TYPE);

      ConsPair pair = (ConsPair) evaluated;
      pair.first.getType().join(PrimType.STRING);

      fields.add(removeQuotes(pair.first.toString()));
      types.add(NilType.NIL);
      values.add(pair.rest);
    }

    return new StructObject(new StructType("object", types, fields), values);
  }

  @Override
  public String toString() {
    return "make-object";
  }
}
