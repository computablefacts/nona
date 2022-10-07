package com.computablefacts.nona.functions.comparisonoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.List;

@CheckReturnValue
public class Equal extends Function {

  public Equal() {
    super(eCategory.COMPARISON_OPERATORS, "EQUAL",
        "EQUAL(x, y) returns true if x and y are both of the same type and x is equal to y.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 2, "EQUAL takes exactly two parameters.");

    BoxedType<?> left = parameters.get(0);
    BoxedType<?> right = parameters.get(1);

    return box(left.equals(right));
  }
}
