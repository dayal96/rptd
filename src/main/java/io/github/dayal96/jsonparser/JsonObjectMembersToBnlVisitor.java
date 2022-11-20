package io.github.dayal96.jsonparser;

import static io.github.dayal96.util.StringUtil.removeQuotes;

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
import java.util.HashMap;
import java.util.Map;

public class JsonObjectMembersToBnlVisitor extends JsonBaseVisitor<Map<String, Expression>> {

  @Override
  public Map<String, Expression> visitJson(JsonContext ctx) {
    throw new RuntimeException("Operation not supported");
  }

  @Override
  public Map<String, Expression> visitJsonObject(JsonObjectContext ctx) {
    throw new RuntimeException("Operation not supported");
  }

  @Override
  public Map<String, Expression> visitJsonArray(JsonArrayContext ctx) {
    throw new RuntimeException("Operation not supported");
  }

  @Override
  public Map<String, Expression> visitJsonPrimitive(JsonPrimitiveContext ctx) {
    throw new RuntimeException("Operation not supported");
  }

  @Override
  public Map<String, Expression> visitJsonString(JsonStringContext ctx) {
    throw new RuntimeException("Operation not supported");
  }

  @Override
  public Map<String, Expression> visitNumber(NumberContext ctx) {
    throw new RuntimeException("Operation not supported");
  }

  @Override
  public Map<String, Expression> visitDecimal(DecimalContext ctx) {
    throw new RuntimeException("Operation not supported");
  }

  @Override
  public Map<String, Expression> visitTrue(TrueContext ctx) {
    throw new RuntimeException("Operation not supported");
  }

  @Override
  public Map<String, Expression> visitFalse(FalseContext ctx) {
    throw new RuntimeException("Operation not supported");
  }

  @Override
  public Map<String, Expression> visitNull(NullContext ctx) {
    throw new RuntimeException("Operation not supported");
  }

  @Override
  public Map<String, Expression> visitNonEmptyObject(NonEmptyObjectContext ctx) {
    throw new RuntimeException("Operation not supported");
  }

  @Override
  public Map<String, Expression> visitEmptyObject(EmptyObjectContext ctx) {
    throw new RuntimeException("Operation not supported");
  }

  @Override
  public Map<String, Expression> visitRecurMember(RecurMemberContext ctx) {
    Map<String, Expression> obj = ctx.members().accept(this);
    obj.putAll(ctx.member().accept(this));
    return obj;
  }

  @Override
  public Map<String, Expression> visitSingleMember(SingleMemberContext ctx) {
    return ctx.member().accept(this);
  }

  @Override
  public Map<String, Expression> visitMember(MemberContext ctx) {
    HashMap<String, Expression> map = new HashMap<>();
    map.put(removeQuotes(ctx.STRING().getText()), ctx.value().accept(new JsonToBnlVisitor()));
    return map;
  }

  @Override
  public Map<String, Expression> visitEmptyArray(EmptyArrayContext ctx) {
    throw new RuntimeException("Operation not supported");
  }

  @Override
  public Map<String, Expression> visitNonEmptyArray(NonEmptyArrayContext ctx) {
    throw new RuntimeException("Operation not supported");
  }

  @Override
  public Map<String, Expression> visitSingleArray(SingleArrayContext ctx) {
    throw new RuntimeException("Operation not supported");
  }

  @Override
  public Map<String, Expression> visitRecurArray(RecurArrayContext ctx) {
    throw new RuntimeException("Operation not supported");
  }
}
