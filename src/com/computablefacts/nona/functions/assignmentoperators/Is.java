package com.computablefacts.nona.functions.assignmentoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.List;

@CheckReturnValue
public class Is extends Function {

  public Is() {
    super(eCategory.ASSIGNMENT_OPERATORS, "IS", "IS(x) returns x.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "IS takes exactly one parameter.");

    return box(parameters.get(0).asString());
  }
}
