package com.computablefacts.nona.functions.listoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.List;

@CheckReturnValue
public class IsEmptyList extends Function {

  public IsEmptyList() {
    super(eCategory.LIST_OPERATORS, "IS_EMPTY_LIST", "IS_EMPTY(x) returns true iif the list x is empty.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "IS_EMPTY_LIST takes exactly one parameter.");
    Preconditions.checkArgument(parameters.get(0).isCollection(), "%s should be a collection", parameters.get(0));

    return box(parameters.get(0).asCollection().isEmpty());
  }
}
