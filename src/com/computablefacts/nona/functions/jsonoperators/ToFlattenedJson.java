package com.computablefacts.nona.functions.jsonoperators;

import java.util.List;
import java.util.Map;

import com.computablefacts.asterix.codecs.JsonCodec;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Json;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class ToFlattenedJson extends Function {

  public ToFlattenedJson() {
    super(eCategory.JSON_OPERATORS, "TO_FLATTENED_JSON",
        "TO_FLATTENED_JSON(x) returns the flattened JSON object or array associated to string x.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1,
        "TO_FLATTENED_JSON takes exactly one parameter.");
    Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string",
        parameters.get(0));

    String json = parameters.get(0).asString();
    Map<String, Object> map = JsonCodec.flatten(json, '.');

    // the empty array is flattened to "{\"root\":[]}"
    if (map.size() == 1 && map.containsKey("root")) {
      return map.get("root") == null ? BoxedType.empty() : box(new Json());
    }
    return Strings.isNullOrEmpty(json) ? BoxedType.empty() : box(new Json(map));
  }
}
