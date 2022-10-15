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
import io.github.dayal96.primitive.bool.MyBoolean;

public class IsList implements ExpressionVisitor<Boolean> {

  private static final IsList instance = new IsList();

  private IsList() {}

  public static IsList getInstance() {
    return instance;
  }

  @Override
  public Boolean visitConsPair(ConsPair consPair) {
    return consPair.rest.accept(this);
  }

  @Override
  public Boolean visitFunctionCall(FunctionCall functionCall) {
    return false;
  }

  @Override
  public Boolean visitLambda(Lambda lambda) {
    return false;
  }

  @Override
  public Boolean visitLambdaEnclosure(LambdaEnclosure lambdaEnclosure) {
    return false;
  }

  @Override
  public Boolean visitLocal(Local local) {
    return false;
  }

  @Override
  public Boolean visitOperator(AOperator aOperator) {
    return false;
  }

  @Override
  public Boolean visitEmptyExpression(EmptyExpression emptyExpression) {
    return false;
  }

  @Override
  public Boolean visitVariable(Variable variable) {
    return false;
  }

  @Override
  public Boolean visitPrimitive(Primitive primitive) {
    return primitive.equals(MyBoolean.FALSE);
  }
}
