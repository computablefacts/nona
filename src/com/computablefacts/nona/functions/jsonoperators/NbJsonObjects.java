package com.computablefacts.nona.functions.jsonoperators;

import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Json;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class NbJsonObjects extends Function {

  public NbJsonObjects() {
    super(eCategory.JSON_OPERATORS, "NB_JSON_OBJECTS",
        "NB_JSON_OBJECTS(json) returns the total number of objects in the JSON array.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1,
        "NB_JSON_OBJECTS takes exactly one parameter.");
    Preconditions.checkArgument(parameters.get(0).value() instanceof Json,
        "%s should be a json array", parameters.get(0));

    return box(((Json) parameters.get(0).value()).nbObjects());
  }
}
