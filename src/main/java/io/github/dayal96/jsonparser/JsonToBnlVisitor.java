package io.github.dayal96.jsonparser;

import io.github.dayal96.antlr.JsonBaseVisitor;
import io.github.dayal96.antlr.JsonParser.DecimalContext;
import io.github.dayal96.antlr.JsonParser.EmptyArrayContext;
import io.github.dayal96.antlr.JsonParser.EmptyObjectContext;
import io.github.dayal96.antlr.JsonParser.FalseContext;
import io.github.dayal96.antlr.JsonParser.JsonArrayContext;
import io.github.dayal96.antlr.JsonParser.JsonContext;
import io.github.dayal96.antlr.JsonParser.JsonObjectContext;
import io.github.dayal96.antlr.JsonParser.JsonPrimitiveContext;
import io.github.dayal96.antlr.JsonParser.JsonStringContext;
import io.github.dayal96.antlr.JsonParser.MemberContext;
import io.github.dayal96.antlr.JsonParser.NonEmptyArrayContext;
import io.github.dayal96.antlr.JsonParser.NonEmptyObjectContext;
import io.github.dayal96.antlr.JsonParser.NullContext;
import io.github.dayal96.antlr.JsonParser.NumberContext;
import io.github.dayal96.antlr.JsonParser.RecurArrayContext;
import io.github.dayal96.antlr.JsonParser.RecurMemberContext;
import io.github.dayal96.antlr.JsonParser.SingleArrayContext;
import io.github.dayal96.antlr.JsonParser.SingleMemberContext;
import io.github.dayal96.antlr.JsonParser.TrueContext;
import io.github.dayal96.expression.Expression;
import io.github.dayal96.expression.cons.ConsPair;
import io.github.dayal96.primitive.bool.MyBoolean;
import io.github.dayal96.primitive.number.Rational;
import io.github.dayal96.primitive.string.MyString;
import io.github.dayal96.runtime.expr.AppendToList;

public class JsonToBnlVisitor extends JsonBaseVisitor<Expression> {

  @Override
  public Expression visitJson(JsonContext ctx) {
    return this.visit(ctx.value());
  }

  @Override
  public Expression visitJsonObject(JsonObjectContext ctx) {
    return this.visit(ctx.object());
  }

  @Override
  public Expression visitJsonArray(JsonArrayContext ctx) {
    return this.visit(ctx.array());
  }

  @Override
  public Expression visitJsonString(JsonStringContext ctx) {
    return new MyString(unescape(ctx.STRING().getText()));
  }

  @Override
  public Expression visitJsonPrimitive(JsonPrimitiveContext ctx) {
    return this.visit(ctx.primitive());
  }

  @Override
  public Expression visitNumber(NumberContext ctx) {
    try {
      return new Rational(Integer.parseInt(ctx.getText()));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Expression visitDecimal(DecimalContext ctx) {
    try {
      double value = Double.parseDouble(ctx.getText());
      return new Rational((int) (value * 1000000.0), 1000000);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Expression visitTrue(TrueContext ctx) {
    return MyBoolean.TRUE;
  }

  @Override
  public Expression visitFalse(FalseContext ctx) {
    return MyBoolean.FALSE;
  }

  @Override
  public Expression visitNull(NullContext ctx) {
    return MyBoolean.FALSE;
  }

  @Override
  public Expression visitNonEmptyObject(NonEmptyObjectContext ctx) {
    return new ConsPair(
        new ConsPair(new MyString("type"), new MyString("object")),
        this.visit(ctx.members())
    );
  }

  @Override
  public Expression visitEmptyObject(EmptyObjectContext ctx) {
    return new ConsPair(new ConsPair(new MyString("type"), new MyString("object")),
        MyBoolean.FALSE);
  }

  @Override
  public Expression visitRecurMember(RecurMemberContext ctx) {
    return new ConsPair(this.visit(ctx.member()),
        this.visit(ctx.members()));
  }

  @Override
  public Expression visitSingleMember(SingleMemberContext ctx) {
    return new ConsPair(this.visit(ctx.member()),
        MyBoolean.FALSE);
  }

  @Override
  public Expression visitMember(MemberContext ctx) {
    return new ConsPair(new MyString(unescape(ctx.STRING().getText().replaceAll(" ", ""))),
        this.visit(ctx.value()));
  }

  @Override
  public Expression visitEmptyArray(EmptyArrayContext ctx) {
    return MyBoolean.FALSE;
  }

  @Override
  public Expression visitNonEmptyArray(NonEmptyArrayContext ctx) {
    return this.visit(ctx.arrayContent());
  }

  @Override
  public Expression visitSingleArray(SingleArrayContext ctx) {
    return new ConsPair(this.visit(ctx.value()), MyBoolean.FALSE);
  }

  @Override
  public Expression visitRecurArray(RecurArrayContext ctx) {
    return this.visit(ctx.arrayContent()).accept(new AppendToList(this.visit(ctx.value())));
  }

  private static String unescape(String str) {
    return str.substring(1,str.length() - 1).replaceAll("\"", "\"").replaceAll("\\\\", "\\");
  }
}
