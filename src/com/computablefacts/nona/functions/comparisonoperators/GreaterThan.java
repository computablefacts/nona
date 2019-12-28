package com.computablefacts.nona.functions.comparisonoperators;

import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class GreaterThan extends Function {

  public GreaterThan() {
    super("GT", true);
  }

  @Override
  protected BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 2, "GT takes exactly two parameters.");

    return BoxedType.create(parameters.get(0).compareTo(parameters.get(1)) > 0);
  }
}
