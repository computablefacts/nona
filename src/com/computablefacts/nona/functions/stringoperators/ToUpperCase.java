package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.List;

@CheckReturnValue
public class ToUpperCase extends Function {

  public ToUpperCase() {
    super(eCategory.STRING_OPERATORS, "TO_UPPERCASE", "TO_UPPERCASE(x) converts x to uppercase.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "TO_UPPERCASE takes exactly one parameter.");

    String x = parameters.get(0).asString();
    Preconditions.checkNotNull(x, "x should not be null");
    return box(x.toUpperCase());
  }
}
