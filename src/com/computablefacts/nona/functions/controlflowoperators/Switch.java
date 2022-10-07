package com.computablefacts.nona.functions.controlflowoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Var;
import java.util.List;

@CheckReturnValue
public class Switch extends Function {

  public Switch() {
    super(eCategory.CONTROL_FLOW_OPERATORS, "SWITCH",
        "SWITCH(x, a, u, ..., c, v, _, w) returns u if x is equal to a, ..., v if x is equal to c. "
            + "If x is equal to none of the specified values, w is returned.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() >= 3, "SWITCH takes at least three parameters.");
    Preconditions.checkArgument((parameters.size() - 1) % 2 == 0, "SWITCH contains an invalid number of statements.");

    @Var BoxedType<?> defaultOutput = null;
    BoxedType<?> defaultStatement = box("_");
    BoxedType<?> input = parameters.get(0);

    for (int i = 1; i < parameters.size(); i += 2) {

      BoxedType<?> statement = parameters.get(i);

      if (statement.equals(input)) {
        return parameters.get(i + 1);
      }
      if (statement.equals(defaultStatement)) {
        defaultOutput = parameters.get(i + 1);
      }
    }

    if (defaultOutput != null) {
      return defaultOutput;
    }

    Preconditions.checkState(false, "Neither a valid nor a default statement has been found.");

    return BoxedType.empty();
  }

  @Override
  protected boolean isCacheable() {
    return false;
  }
}
