package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.asterix.Span;
import com.computablefacts.asterix.SpanSequence;
import com.computablefacts.asterix.codecs.JsonCodec;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.nona.types.Csv;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CheckReturnValue
public class ToText extends Function {

  public ToText() {
    super(eCategory.STRING_OPERATORS, "TO_TEXT", "TO_TEXT(x) serialize x to a string.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "TO_TEXT takes exactly one parameter.");

    Object x = parameters.get(0).value();

    if (x == null) {
      return box("");
    }
    if (x instanceof Span) {
      return box(((Span) x).text());
    }
    if (x instanceof SpanSequence) {
      return box(JsonCodec.asString(((SpanSequence) x).stream().map(Span::text).collect(Collectors.toList())));
    }
    if (x instanceof Collection || x instanceof Map) {
      return box(parameters.get(0).asString());
    }
    if (x instanceof Csv) {
      return box(((Csv) x).asString());
    }
    return box(x);
  }
}
