package com.computablefacts.nona.functions.booleanlogicoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.List;

@CheckReturnValue
public class IsTrue extends Function {

  public IsTrue() {
    super(eCategory.BOOLEAN_LOGIC_OPERATORS, "IS_TRUE", "IS_TRUE(x) returns true if x is a boolean and x is true.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "IS_TRUE takes exactly one parameter.");

    return box(parameters.get(0).asBool() != null && parameters.get(0).asBool());
  }
}
