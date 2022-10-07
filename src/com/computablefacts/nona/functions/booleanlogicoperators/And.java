package com.computablefacts.nona.functions.booleanlogicoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.List;

@CheckReturnValue
public class And extends Function {

  public And() {
    super(eCategory.BOOLEAN_LOGIC_OPERATORS, "AND", "AND(x, ..., z) returns true if x and ... and z are true.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() >= 2, "AND takes at least two parameters.");

    for (BoxedType<?> parameter : parameters) {

      Preconditions.checkArgument(parameter.isBoolean(), "%s should be a boolean", parameter);

      if (!parameter.asBool()) {
        return box(false);
      }
    }
    return box(true);
  }
}
