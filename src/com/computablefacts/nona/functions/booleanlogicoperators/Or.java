package com.computablefacts.nona.functions.booleanlogicoperators;

import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class Or extends Function {

  public Or() {
    super("OR", true);
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() >= 2, "OR takes at least two parameters.");

    for (BoxedType parameter : parameters) {

      Preconditions.checkArgument(parameter.isBoolean(), "%s should be a boolean", parameter);

      if (parameter.asBool()) {
        return BoxedType.create(true);
      }
    }
    return BoxedType.create(false);
  }
}
