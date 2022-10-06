package com.computablefacts.nona.functions.stringoperators;

import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.asterix.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class Contain extends Function {

  public Contain() {
    super(eCategory.STRING_OPERATORS, "CONTAIN",
        "CONTAIN(x, y) returns true if x has y as a substring, false otherwise.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 2, "CONTAIN takes exactly two parameters.");

    String x = parameters.get(0).asString();
    String y = parameters.get(1).asString();

    Preconditions.checkNotNull(x, "x should not be null");
    Preconditions.checkNotNull(y, "y should not be null");

    return box(x.contains(y));
  }
}
