package com.computablefacts.nona.functions.stringoperators;

import java.util.List;
import java.util.stream.Collectors;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Csv;
import com.computablefacts.nona.types.Json;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

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
      return box(((SpanSequence) x).stream().map(Span::text).collect(Collectors.joining(", ")));
    }
    if (x instanceof Json) {
      return box(((Json) x).asString());
    }
    if (x instanceof Csv) {
      return box(((Csv) x).asString());
    }
    return box(x);
  }
}
