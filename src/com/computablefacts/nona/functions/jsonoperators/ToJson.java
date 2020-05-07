package com.computablefacts.nona.functions.jsonoperators;

import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Json;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class ToJson extends Function {

  public ToJson() {
    super("TOJSON", true);
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "TOJSON takes exactly one parameter.");
    Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string",
        parameters.get(0));

    return BoxedType.create(Json.create(parameters.get(0).asString()));
  }
}
