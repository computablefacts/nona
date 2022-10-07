package com.computablefacts.nona.functions.booleanlogicoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.List;

@CheckReturnValue
public class IsNullOrEmpty extends Function {

  public IsNullOrEmpty() {
    super(eCategory.BOOLEAN_LOGIC_OPERATORS, "IS_NULL_OR_EMPTY",
        "IS_NULL_OR_EMPTY(x) returns true if x is null or equals to the empty string.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "IS_NULL_OR_EMPTY takes exactly one parameter.");

    if (parameters.get(0).isEmpty()) {
      return box(true);
    }

    String string = parameters.get(0).asString();
    return box(string == null || string.isEmpty());
  }
}
