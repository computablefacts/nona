package com.computablefacts.nona.functions.booleanlogicoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.List;

@CheckReturnValue
public class IsBlank extends Function {

  public IsBlank() {
    super(eCategory.BOOLEAN_LOGIC_OPERATORS, "IS_BLANK",
        "IS_BLANK(x) returns true if x is equal to the empty string after trimming.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "IS_BLANK takes exactly one parameter.");

    String string = parameters.get(0).asString();
    return box(string != null && string.trim().isEmpty());
  }
}
