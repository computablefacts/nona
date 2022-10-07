package com.computablefacts.nona.functions.controlflowoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.List;

@CheckReturnValue
public class If extends Function {

  public If() {
    super(eCategory.CONTROL_FLOW_OPERATORS, "IF", "IF(x, a, b) returns a if x is true, b otherwise.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 3, "IF takes exactly three parameters.");
    Preconditions.checkArgument(parameters.get(0).isBoolean(), "%s should be a boolean", parameters.get(0));

    boolean condition = parameters.get(0).asBool();
    return condition ? parameters.get(1) : parameters.get(2);
  }

  @Override
  protected boolean isCacheable() {
    return false;
  }
}
