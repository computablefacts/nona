package com.computablefacts.nona.functions.comparisonoperators;

import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class LessThanOrEqual extends Function {

  public LessThanOrEqual() {
    super(eCategory.COMPARISON_OPERATORS, "LTE",
        "LTE(x, y) returns true if x and y are both of the same type and x is less than or equal to y.");
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 2, "LTE takes exactly two parameters.");

    return BoxedType.create(parameters.get(0).compareTo(parameters.get(1)) <= 0);
  }
}
