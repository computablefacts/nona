package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.List;

@CheckReturnValue
public class EndWith extends Function {

  public EndWith() {
    super(eCategory.STRING_OPERATORS, "END_WITH",
        "END_WITH(x, y) returns true if x has y as a suffix, false otherwise.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 2, "END_WITH takes exactly two parameters.");

    String x = parameters.get(0).asString();
    String y = parameters.get(1).asString();

    Preconditions.checkNotNull(x, "x should not be null");
    Preconditions.checkNotNull(y, "y should not be null");

    return box(x.endsWith(y));
  }
}
