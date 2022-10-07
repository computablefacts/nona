package com.computablefacts.nona.functions.jsonoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.List;

@Deprecated
@CheckReturnValue
public class NbJsonObjects extends Function {

  public NbJsonObjects() {
    super(eCategory.JSON_OPERATORS, "NB_JSON_OBJECTS",
        "NB_JSON_OBJECTS(json) returns the total number of objects in the JSON array.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "NB_JSON_OBJECTS takes exactly one parameter.");
    Preconditions.checkArgument(
        parameters.get(0).isEmpty() || parameters.get(0).isCollection() || parameters.get(0).isMap(),
        "%s must be either a Collection or a Map", parameters.get(0));

    BoxedType<?> bt = parameters.get(0);

    if (bt.isCollection()) {
      return box(bt.asCollection().size());
    }
    if (bt.isMap()) {
      return box(1);
    }
    return box(0);
  }
}
