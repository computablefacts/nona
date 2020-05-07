package com.computablefacts.nona.functions.comparisonoperators;

import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class Equal extends Function {

  public Equal() {
    super("EQUAL", true);
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 2, "EQUAL takes exactly two parameters.");

    BoxedType left = parameters.get(0);
    BoxedType right = parameters.get(1);

    return BoxedType.create(left.equals(right));
  }
}
