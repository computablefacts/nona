package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.List;

@CheckReturnValue
public class ToInteger extends Function {

  public ToInteger() {
    super(eCategory.STRING_OPERATORS, "TO_INTEGER", "TO_INTEGER(x) converts x to an integer.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "TO_INTEGER takes exactly one parameter.");
    Preconditions.checkArgument(parameters.get(0).isNumber(), "%s should be a number", parameters.get(0));

    return box(parameters.get(0).asBigInteger());
  }
}
