package com.computablefacts.nona.functions.stringoperators;

import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class StrLength extends Function {

  public StrLength() {
    super(eCategory.STRING_OPERATORS, "STR_LENGTH",
        "STR_LENGTH(x) returns the length in characters of x.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "STR_LENGTH takes exactly one parameter.");

    String x = parameters.get(0).asString();
    Preconditions.checkNotNull(x, "x should not be null");
    return BoxedType.create(x.length());
  }
}
