package com.computablefacts.nona.functions.booleanlogicoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.List;

@CheckReturnValue
public class IsNull extends Function {

  public IsNull() {
    super(eCategory.BOOLEAN_LOGIC_OPERATORS, "IS_NULL", "IS_NULL(x) returns true if and only if x is null.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "IS_NULL takes exactly one parameter.");

    return box(parameters.get(0).isEmpty());
  }
}
