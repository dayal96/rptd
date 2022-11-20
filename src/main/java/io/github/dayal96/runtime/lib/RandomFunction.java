package io.github.dayal96.runtime.lib;

import io.github.dayal96.environment.Environment;
import io.github.dayal96.expression.Expression;
import io.github.dayal96.expression.operator.AOperator;
import io.github.dayal96.expression.type.PrimType;
import io.github.dayal96.primitive.number.Rational;
import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

public class RandomFunction extends AOperator {

  private final RandomGenerator random;

  public RandomFunction() {
    this.random = new Random();
  }

  public RandomFunction(byte[] seed) {
    RandomGeneratorFactory<RandomGenerator> factory = RandomGeneratorFactory.of("L128X128MixRandom");
    this.random = factory.create(seed);
  }

  @Override
  public Expression evaluate(List<Expression> operands, Environment environment) throws Exception {
    if (operands.size() != 1) {
      throw new Exception("random : expected 1 argument, found " + operands.size());
    }
    Expression evaluated = operands.get(0).evaluate(environment);
    PrimType.NUMBER.join(evaluated.getType());
    double num = Double.parseDouble(evaluated.toString());
    return new Rational(this.random.nextInt((int) num));
  }

  @Override
  public String toString() {
    return "random";
  }
}
