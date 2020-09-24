package com.computablefacts.nona.functions.comparisonoperators;

import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class GreaterThanOrEqual extends Function {

  public GreaterThanOrEqual() {
    super(eCategory.COMPARISON_OPERATORS, "GTE",
        "GTE(x, y) returns true if x and y are both of the same type and x is greater than or equal to y.");
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 2, "GTE takes exactly two parameters.");

    return BoxedType.create(parameters.get(0).compareTo(parameters.get(1)) >= 0);
  }
}
