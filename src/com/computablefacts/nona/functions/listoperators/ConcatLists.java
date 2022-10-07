package com.computablefacts.nona.functions.listoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.ArrayList;
import java.util.List;

@CheckReturnValue
public class ConcatLists extends Function {

  public ConcatLists() {
    super(eCategory.LIST_OPERATORS, "CONCAT_LISTS", "CONCAT_LISTS(x, y) returns the concatenation of lists x and y.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 2, "CONCAT_LISTS takes exactly two parameters.");
    Preconditions.checkArgument(parameters.get(0).isCollection(), "%s should be a collection", parameters.get(0));
    Preconditions.checkArgument(parameters.get(1).isCollection(), "%s should be a collection", parameters.get(1));

    List<Object> list = new ArrayList<>(parameters.get(0).asCollection());
    list.addAll(parameters.get(1).asCollection());
    return box(list);
  }
}
