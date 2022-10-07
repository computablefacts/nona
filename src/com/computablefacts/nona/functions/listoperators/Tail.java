package com.computablefacts.nona.functions.listoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.List;

@CheckReturnValue
public class Tail extends Function {

  public Tail() {
    super(eCategory.LIST_OPERATORS, "TAIL", "TAIL(x) returns the list x minus its first element.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "TAIL takes exactly one parameter.");
    Preconditions.checkArgument(parameters.get(0).isCollection(), "%s should be a collection", parameters.get(0));

    List<?> list = Lists.newArrayList(parameters.get(0).asCollection());
    return list.isEmpty() ? BoxedType.empty() : box(list.subList(1, list.size()));
  }
}
