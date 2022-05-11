package com.computablefacts.nona.functions.listoperators;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.computablefacts.asterix.codecs.JsonCodec;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Json;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class Get extends Function {

  public Get() {
    super(eCategory.LIST_OPERATORS, "GET",
        "GET(x, y) returns the element of list x at position y or returns the path's y value of JSON x.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 2, "GET takes exactly two parameters.");

    if (parameters.get(0).isCollection()) {

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

    Preconditions.checkArgument(parameters.get(0).value() instanceof Json,
        "%s should be a JSON object", parameters.get(0));
    Preconditions.checkArgument(parameters.get(1).isString(), "%s should be a JSON path",
        parameters.get(1));
    Preconditions.checkArgument(!Strings.isNullOrEmpty(parameters.get(1).asString()),
        "a JSON path should neither be null nor empty", parameters.get(1));

    Json json = (Json) parameters.get(0).value();
    String path = parameters.get(1).asString();
    Map<String, Object> map = JsonCodec.flatten(json.asString(), '.');
    return map.get(path) == null ? BoxedType.empty() : box(map.get(path));
  }
}
