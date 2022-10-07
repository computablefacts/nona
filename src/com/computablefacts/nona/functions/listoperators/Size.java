package com.computablefacts.nona.functions.listoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.List;

@CheckReturnValue
public class Size extends Function {

  public Size() {
    super(eCategory.LIST_OPERATORS, "SIZE", "SIZE(x) returns the size of list x or the number of keys of map x.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "SIZE takes exactly one parameter.");
    Preconditions.checkArgument(parameters.get(0).isCollection() || parameters.get(0).isMap(),
        "%s must be either a Collection or a Map", parameters.get(0));

    BoxedType<?> bt = parameters.get(0);

    if (bt.isCollection()) {
      return box(bt.asCollection().size());
    }
    return box(bt.asMap().size());
  }
}
