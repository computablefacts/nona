package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.List;

@CheckReturnValue
public class Trim extends Function {

  public Trim() {
    super(eCategory.STRING_OPERATORS, "TRIM",
        "TRIM(x) returns the string x with the whitespaces at the beginning and the end of the string removed.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "TRIM takes exactly one parameter.");

    String x = parameters.get(0).asString();
    Preconditions.checkNotNull(x, "x should not be null");
    return box(x.trim());
  }
}
