package com.computablefacts.nona.functions.booleanlogicoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.List;

@CheckReturnValue
public class Or extends Function {

  public Or() {
    super(eCategory.BOOLEAN_LOGIC_OPERATORS, "OR", "OR(x, ..., z) returns true if either x or ... or z is true.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() >= 2, "OR takes at least two parameters.");

    for (BoxedType<?> parameter : parameters) {

      Preconditions.checkArgument(parameter.isBoolean(), "%s should be a boolean", parameter);

      if (parameter.asBool()) {
        return box(true);
      }
    }
    return box(false);
  }
}
