package com.computablefacts.nona.functions.listoperators;

import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class Size extends Function {

  public Size() {
    super(eCategory.LIST_OPERATORS, "SIZE", "SIZE(x) returns the size of list x.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "SIZE takes exactly one parameter.");
    Preconditions.checkArgument(parameters.get(0).isCollection(), "%s should be a collection",
        parameters.get(0));

    return box(parameters.get(0).asCollection().size());
  }
}
