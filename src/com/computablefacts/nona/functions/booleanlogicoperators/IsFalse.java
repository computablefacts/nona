package com.computablefacts.nona.functions.booleanlogicoperators;

import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class IsFalse extends Function {

  public IsFalse() {
    super(eCategory.BOOLEAN_LOGIC_OPERATORS, "IS_FALSE",
        "IS_FALSE(x) returns true if x is a boolean and x is false.");
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "IS_FALSE takes exactly one parameter.");

    return BoxedType.create(parameters.get(0).asBool() == null || !parameters.get(0).asBool());
  }
}
