package com.computablefacts.nona.functions.controlflowoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Var;
import java.util.List;

@CheckReturnValue
public class Which extends Function {

  public Which() {
    super(eCategory.CONTROL_FLOW_OPERATORS, "SWITCH",
        "WHICH(a, u, ..., c, v, _, w) returns u if a is true, ..., v if c is true. "
            + "If none of the previous tests returns true, w is returned.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() >= 2, "WHICH takes at least two parameters.");
    Preconditions.checkArgument(parameters.size() % 2 == 0, "WHICH contains an invalid number of statements.");

    @Var BoxedType<?> defaultOutput = null;
    BoxedType<?> defaultStatement = box("_");

    for (int i = 0; i < parameters.size(); i += 2) {

      BoxedType<?> statement = parameters.get(i);

      if (statement.equals(defaultStatement)) {
        defaultOutput = parameters.get(i + 1);
      } else {

        Preconditions.checkState(statement.isBoolean(), "%s should be a boolean", statement);

        if (statement.asBool()) {
          return parameters.get(i + 1);
        }
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
