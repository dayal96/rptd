package io.github.dayal96.runtime.expr;

import io.github.dayal96.expression.Expression;
import io.github.dayal96.expression.cons.ConsPair;
import io.github.dayal96.primitive.Primitive;
import io.github.dayal96.primitive.bool.MyBoolean;

public class AppendToList extends PartialVisitor<Expression> {

  public final Expression toAdd;

  public AppendToList(Expression toAdd) {
    this.toAdd = toAdd;
  }

  @Override
  public Expression visitConsPair(ConsPair consPair) {
    return new ConsPair(consPair.first, consPair.rest.accept(this));
  }

  @Override
  public Expression visitPrimitive(Primitive primitive) {
    if (primitive.equals(MyBoolean.FALSE)) {
      return new ConsPair(toAdd, MyBoolean.FALSE);
    }
    throw new RuntimeException("Operation not supported.");
  }
}
