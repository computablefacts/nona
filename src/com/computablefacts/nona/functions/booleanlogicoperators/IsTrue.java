package com.computablefacts.nona.functions.booleanlogicoperators;

import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class IsTrue extends Function {

  public IsTrue() {
    super(eCategory.BOOLEAN_LOGIC_OPERATORS, "IS_TRUE",
        "IS_TRUE(x) returns true if x is a boolean and x is true.");
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "IS_TRUE takes exactly one parameter.");

    return BoxedType.create(parameters.get(0).asBool() != null && parameters.get(0).asBool());
  }
}
