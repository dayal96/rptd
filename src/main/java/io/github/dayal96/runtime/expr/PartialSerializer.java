package io.github.dayal96.runtime.expr;

import io.github.dayal96.expression.EmptyExpression;
import io.github.dayal96.expression.Variable;
import io.github.dayal96.expression.cons.ConsPair;
import io.github.dayal96.expression.lambda.FunctionCall;
import io.github.dayal96.expression.lambda.Lambda;
import io.github.dayal96.expression.lambda.LambdaEnclosure;
import io.github.dayal96.expression.local.Local;
import io.github.dayal96.expression.operator.AOperator;
import io.github.dayal96.expression.visitor.ExpressionVisitor;
import io.github.dayal96.primitive.Primitive;

public abstract class PartialSerializer<T> implements ExpressionVisitor<T> {

  public PartialSerializer() {}

  @Override
  public T visitConsPair(ConsPair consPair) {
    throw new RuntimeException("Operation not supported.");
  }

  @Override
  public T visitFunctionCall(FunctionCall functionCall) {
    throw new RuntimeException("Operation not supported.");
  }

  @Override
  public T visitLambda(Lambda lambda) {
    throw new RuntimeException("Operation not supported.");
  }

  @Override
  public T visitLambdaEnclosure(LambdaEnclosure lambdaEnclosure) {
    throw new RuntimeException("Operation not supported.");
  }

  @Override
  public T visitLocal(Local local) {
    throw new RuntimeException("Operation not supported.");
  }

  @Override
  public T visitOperator(AOperator aOperator) {
    throw new RuntimeException("Operation not supported.");
  }

  @Override
  public T visitEmptyExpression(EmptyExpression emptyExpression) {
    throw new RuntimeException("Operation not supported.");
  }

  @Override
  public T visitVariable(Variable variable) {
    throw new RuntimeException("Operation not supported.");
  }

  @Override
  public T visitPrimitive(Primitive primitive) {
    throw new RuntimeException("Operation not supported.");
  }
}
