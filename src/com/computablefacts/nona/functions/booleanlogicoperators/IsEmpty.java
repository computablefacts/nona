package com.computablefacts.nona.functions.booleanlogicoperators;

import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class IsEmpty extends Function {

  public IsEmpty() {
    super(eCategory.BOOLEAN_LOGIC_OPERATORS, "IS_EMPTY",
        "IS_EMPTY(x) returns true if and only if x is equals to the empty string.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "IS_EMPTY takes exactly one parameter.");
    Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string",
        parameters.get(0));

    String str = parameters.get(0).asString();
    if (str.startsWith("\"") && str.endsWith("\"")) {
      return BoxedType.create(str.substring(1, str.length() - 1).isEmpty());
    }
    return BoxedType.create(str.isEmpty());
  }
}
