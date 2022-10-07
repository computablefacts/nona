package com.computablefacts.nona.functions.listoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.List;

@CheckReturnValue
public class Head extends Function {

  public Head() {
    super(eCategory.LIST_OPERATORS, "HEAD", "HEAD(x) returns the first element of list x.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "HEAD takes exactly one parameter.");
    Preconditions.checkArgument(parameters.get(0).isCollection(), "%s should be a collection", parameters.get(0));

    Object first = Iterables.getFirst(parameters.get(0).asCollection(), null);
    return first == null ? BoxedType.empty() : box(first);
  }
}
