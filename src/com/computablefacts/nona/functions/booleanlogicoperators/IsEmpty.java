package com.computablefacts.nona.functions.booleanlogicoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.List;

@CheckReturnValue
public class IsEmpty extends Function {

  public IsEmpty() {
    super(eCategory.BOOLEAN_LOGIC_OPERATORS, "IS_EMPTY", "IS_EMPTY(x) returns true if x is equal to the empty string.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "IS_EMPTY takes exactly one parameter.");

    String string = parameters.get(0).asString();
    return box(string != null && string.isEmpty());
  }
}
