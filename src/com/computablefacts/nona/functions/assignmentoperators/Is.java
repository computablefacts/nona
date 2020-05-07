package com.computablefacts.nona.functions.assignmentoperators;

import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class Is extends Function {

  public Is() {
    super("IS", true);
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "IS takes exactly one parameter.");

    return BoxedType.create(parameters.get(0).asString());
  }
}
