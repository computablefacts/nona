package com.computablefacts.nona.functions.jsonoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.asterix.codecs.JsonCodec;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.github.wnameless.json.flattener.JsonFlattener;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Var;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string", parameters.get(0));

    @Var String json = parameters.get(0).asString();

    if (Strings.isNullOrEmpty(json)) {
      return BoxedType.empty();
    }
    if (parameters.size() == 1) {

      Optional<Object> newJson = json(json);

      if (!newJson.isPresent()) {
        return BoxedType.empty();
      }

      Object obj = newJson.get();

      if (obj instanceof Map && ((Map<?, ?>) obj).isEmpty()) {
        return BoxedType.empty();
      }
      return box(obj);
    }

    Preconditions.checkArgument(parameters.size() == 2, "TO_JSON takes exactly two parameters.");

    String mode = parameters.get(1).asString();

    if ("keep_primitive_arrays".equals(mode)) {

      Map<String, Object> map = JsonCodec.flattenKeepPrimitiveArrays(json, '.');

      if (map == null || map.isEmpty()) {
        return BoxedType.empty();
      }

      // The empty array is flattened to "{\"root\":[]}"
      if (map.size() == 1 && map.containsKey("root")) {
        if (map.get("root") == null) {
          return BoxedType.empty();
        }
        return box(map.get("root"));
      }
      return box(map);
    }
    if ("keep_arrays".equals(mode)) {

      Map<String, Object> map = JsonCodec.flattenKeepArrays(json, '.');

      if (map == null || map.isEmpty()) {
        return BoxedType.empty();
      }

      // The empty array is flattened to "{\"root\":[]}"
      if (map.size() == 1 && map.containsKey("root")) {
        if (map.get("root") == null) {
          return BoxedType.empty();
        }
        return box(map.get("root"));
      }
      return box(map);
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
      return box(map.get("root"));
    }
    return box(map);
  }

  private Optional<Object> json(String json) {

    Preconditions.checkNotNull(json, "json should not be null");

    String newJson = json.trim();

    if (newJson.startsWith("[") && newJson.endsWith("]")) {

      Map<String, Object>[] array = JsonCodec.asArray(json);

      if (array != null && array.length > 0) {
        return Optional.of(Lists.newArrayList(array));
      }
      return Optional.of(Lists.newArrayList());
    }
    if (newJson.startsWith("{") && newJson.endsWith("}")) {

      Map<String, Object> object = JsonCodec.asObject(json);

      if (object != null && !object.isEmpty()) {
        return Optional.of(object);
      }
      return Optional.of(new HashMap<>());
    }
    return Optional.empty();
  }
}
