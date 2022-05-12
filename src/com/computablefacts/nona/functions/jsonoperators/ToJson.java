package com.computablefacts.nona.functions.jsonoperators;

import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Json;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class ToJson extends Function {

  public ToJson() {
    super(eCategory.JSON_OPERATORS, "TO_JSON",
        "TO_JSON(x) returns the JSON object or array associated to string x.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1, "TO_JSON takes exactly one parameter.");
    Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string",
        parameters.get(0));

    String json = parameters.get(0).asString();
    return Strings.isNullOrEmpty(json) ? BoxedType.empty() : box(Json.create(json));
  }
}
