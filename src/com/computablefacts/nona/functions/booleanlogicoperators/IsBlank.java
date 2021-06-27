package com.computablefacts.nona.functions.booleanlogicoperators;

import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class IsBlank extends Function {

  public IsBlank() {
    super(eCategory.BOOLEAN_LOGIC_OPERATORS, "IS_BLANK",
        "IS_BLANK(x) returns true if x is null or equals to the empty string after trimming.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "IS_BLANK takes exactly one parameter.");

    String string = parameters.get(0).asString();
    return BoxedType.create(string == null || string.trim().isEmpty());
  }
}
