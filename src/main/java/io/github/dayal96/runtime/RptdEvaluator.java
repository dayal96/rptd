package io.github.dayal96.runtime;

import io.github.dayal96.environment.Environment;
import io.github.dayal96.environment.SymbolTable;
import io.github.dayal96.expression.Expression;
import io.github.dayal96.expression.operator.AOperator;
import io.github.dayal96.interpreter.Evaluable;
import io.github.dayal96.interpreter.evaluator.Evaluator;
import io.github.dayal96.runtime.lib.RandomFunction;
import java.util.List;
import java.util.Optional;

public class RptdEvaluator implements Evaluator<Optional<Expression>> {

  private final Environment environment;

  /**
   * Create a SimpleEvaluator that has the given pre-defined expressions.
   *
   * @param predefined The pre-defined expressions for this evaluator.
   */
  public RptdEvaluator(Environment predefined) {
    this.environment = predefined;
  }

  public RptdEvaluator() {
    this(SymbolTable.getPrimitiveOperations());
    AOperator randomFunc = new RandomFunction();
    this.environment.addEntry(randomFunc.toString(), randomFunc);
  }

  public RptdEvaluator(byte[] seed) {
    this(SymbolTable.getPrimitiveOperations());
    AOperator randomFunc = new RandomFunction(seed);
    this.environment.addEntry(randomFunc.toString(), randomFunc);
  }

  @Override
  public Optional<Expression> evaluateProgram(List<Evaluable> toEval) throws Exception {
    for (Evaluable eval : toEval) {
      Optional<Expression> result = eval.evaluate(this.environment);
      if (result.isPresent()) {
        return result;
      }
    }

    return Optional.empty();
  }
}
