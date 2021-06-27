package com.computablefacts.nona.functions.booleanlogicoperators;

import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class IsNullOrEmpty extends Function {

  public IsNullOrEmpty() {
    super(eCategory.BOOLEAN_LOGIC_OPERATORS, "IS_NULL_OR_EMPTY",
        "IS_NULL_OR_EMPTY(x) returns true if x is null or equals to the empty string.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1,
        "IS_NULL_OR_EMPTY takes exactly one parameter.");

    if (parameters.get(0).isEmpty()) {
      return BoxedType.create(true);
    }

    String string = parameters.get(0).asString();
    return BoxedType.create(string == null || string.isEmpty());
  }
}
