package com.computablefacts.nona.functions.booleanlogicoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.List;

@CheckReturnValue
public class IsFalse extends Function {

  public IsFalse() {
    super(eCategory.BOOLEAN_LOGIC_OPERATORS, "IS_FALSE", "IS_FALSE(x) returns true if x is a boolean and x is false.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "IS_FALSE takes exactly one parameter.");

    return box(parameters.get(0).asBool() == null || !parameters.get(0).asBool());
  }
}
