package com.computablefacts.nona.functions.controlflowoperators;

import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class If extends Function {

  public If() {
    super("IF", true);
  }

  @Override
  protected BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 3, "IF takes exactly three parameters.");
    Preconditions.checkArgument(parameters.get(0).isBoolean(), "%s should be a boolean",
        parameters.get(0));

    boolean condition = parameters.get(0).asBool();
    return condition ? parameters.get(1) : parameters.get(2);
  }

  @Override
  protected boolean isCacheable() {
    return false;
  }
}
