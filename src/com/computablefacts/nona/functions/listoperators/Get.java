package com.computablefacts.nona.functions.listoperators;

import java.util.Collection;
import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class Get extends Function {

  public Get() {
    super(eCategory.LIST_OPERATORS, "GET",
        "GET(x, y) returns the element of list x at position y.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 2, "GET takes exactly two parameters.");
    Preconditions.checkArgument(parameters.get(0).isCollection(), "%s should be a collection",
        parameters.get(0));
    Preconditions.checkArgument(parameters.get(1).isNumber(), "%s should be a number",
        parameters.get(1));
    Preconditions.checkArgument(parameters.get(1).asInt() >= 0, "%s should be >= 0",
        parameters.get(1));

    Collection<?> list = parameters.get(0).asCollection();
    int index = parameters.get(1).asInt();
    return index > list.size() ? BoxedType.empty() : box(Iterables.get(list, index));
  }
}
