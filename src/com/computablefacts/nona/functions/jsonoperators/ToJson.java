package com.computablefacts.nona.functions.jsonoperators;

import java.util.List;
import java.util.Map;

import com.computablefacts.asterix.codecs.JsonCodec;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Json;
import com.github.wnameless.json.flattener.FlattenMode;
import com.github.wnameless.json.flattener.JsonFlattener;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class ToJson extends Function {

  public ToJson() {
    super(eCategory.JSON_OPERATORS, "TO_JSON",
        "TO_JSON(x, m) returns the JSON object or array associated to string x using optional flatten mode x in {flatten, keep_primitive_arrays, keep_arrays}.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 1 || parameters.size() == 2,
        "TO_JSON takes at least one parameter and at most 2.");
    Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string",
        parameters.get(0));

    String json = parameters.get(0).asString();

    if (Strings.isNullOrEmpty(json)) {
      return BoxedType.empty();
    }
    if (parameters.size() == 1) {
      Json newJson = new Json(json);
      return newJson.nbObjects() == 0 && !newJson.isArray() ? BoxedType.empty() : box(newJson);
    }

    Preconditions.checkArgument(parameters.size() == 2, "TO_JSON takes exactly two parameters.");

    String mode = parameters.get(1).asString();

    if ("keep_primitive_arrays".equals(mode)) {

      Map<String, Object> map = (new JsonFlattener(json))
          .withFlattenMode(FlattenMode.KEEP_PRIMITIVE_ARRAYS).withSeparator('.').flattenAsMap();

      if (map == null || map.isEmpty()) {
        return BoxedType.empty();
      }

      // The empty array is flattened to "{\"root\":[]}"
      if (map.size() == 1 && map.containsKey("root")) {
        if (map.get("root") == null) {
          return BoxedType.empty();
        }
        return box(new Json(JsonCodec.asString(map.get("root"))));
      }
      return box(new Json(map));
    }
    if ("keep_arrays".equals(mode)) {

      Map<String, Object> map = (new JsonFlattener(json)).withFlattenMode(FlattenMode.KEEP_ARRAYS)
          .withSeparator('.').flattenAsMap();

      if (map == null || map.isEmpty()) {
        return BoxedType.empty();
      }

      // The empty array is flattened to "{\"root\":[]}"
      if (map.size() == 1 && map.containsKey("root")) {
        if (map.get("root") == null) {
          return BoxedType.empty();
        }
        return box(new Json(JsonCodec.asString(map.get("root"))));
      }
      return box(new Json(map));
    }

    Map<String, Object> map = (new JsonFlattener(json)).withSeparator('.').flattenAsMap();

    if (map == null || map.isEmpty()) {
      return BoxedType.empty();
    }

    // The empty array is flattened to "{\"root\":[]}"
    if (map.size() == 1 && map.containsKey("root")) {
      if (map.get("root") == null) {
        return BoxedType.empty();
      }
      return box(new Json(JsonCodec.asString(map.get("root"))));
    }
    return box(new Json(map));
  }
}
