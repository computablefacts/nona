package com.computablefacts.nona.functions.mathematicaloperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.math.RoundingMode;
import java.util.List;

@CheckReturnValue
public class Floor extends Function {

  public Floor() {
    super(eCategory.MATHEMATICAL_OPERATORS, "FLOOR",
        "FLOOR(x) returns the largest (closest to positive infinity) double value that is less than or equal to the argument and is equal to a mathematical integer.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "FLOOR takes exactly one parameter.");
    Preconditions.checkArgument(parameters.get(0).isNumber(), "%s should be a number", parameters.get(0));

    return box(parameters.get(0).asBigDecimal().setScale(0, RoundingMode.FLOOR));
  }
}
