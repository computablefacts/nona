package com.computablefacts.nona.functions.mathematicaloperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.math.RoundingMode;
import java.util.List;

@CheckReturnValue
public class Ceil extends Function {

  public Ceil() {
    super(eCategory.MATHEMATICAL_OPERATORS, "CEIL",
        "CEIL(x) returns the smallest (closest to negative infinity) double value that is greater than or equal to the argument and is equal to a mathematical integer.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "CEIL takes exactly one parameter.");
    Preconditions.checkArgument(parameters.get(0).isNumber(), "%s should be a number", parameters.get(0));

    return box(parameters.get(0).asBigDecimal().setScale(0, RoundingMode.CEILING));
  }
}
