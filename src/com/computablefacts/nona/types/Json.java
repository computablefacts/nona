package com.computablefacts.nona.types;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
final public class Json implements Comparable<Json> {

  private static final ObjectMapper mapper_ = new ObjectMapper();
  private static final ObjectReader reader_ = mapper_.readerFor(Map.class);

  private final Map<String, Object>[] jsons_;

  public Json(Map<String, Object>[] jsons) {

    Preconditions.checkNotNull(jsons, "jsons should not be null");

    jsons_ = jsons;
  }

  public static Json create(String json) {

    Map<String, Object>[] array = asJsonArray(json);

    if (array != null && array.length > 0) {
      return new Json(array);
    }

    Map<String, Object> object = asJsonObject(json);

    if (object != null && !object.isEmpty()) {
      return new Json(new Map[] {object});
    }
    return new Json(new Map[] {});
  }

  private static @NotNull Map<String, Object> asJsonObject(String json) {
    try {
      return reader_.readValue(json);
    } catch (IOException e) {
      // TODO
    }
    return new HashMap<>();
  }

  private static @NotNull Map<String, Object>[] asJsonArray(String json) {
    try {
      return mapper_.readValue(json, TypeFactory.defaultInstance().constructArrayType(Map.class));
    } catch (IOException e) {
      // TODO
    }
    return new Map[0];
  }

  public int nbObjects() {
    return jsons_.length;
  }

  public Map<String, Object> object(int index) {

    Preconditions.checkArgument(index >= 0 && index < nbObjects(), "index must be >= 0 and <%s",
        nbObjects());

    return jsons_[index];
  }

  public Object value(int index, String key) {

    Preconditions.checkArgument(!Strings.isNullOrEmpty(key),
        "key should neither be null nor empty");

    return object(index).get(key);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Json other = (Json) obj;
    return Arrays.equals(jsons_, other.jsons_);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(jsons_);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("json", jsons_).omitNullValues().toString();
  }

  /**
   * WARNING : DO NOT USE.
   *
   * This method exists to ensure the {@link Json} datatype can be boxed using {@link BoxedType}.
   */
  @Override
  public int compareTo(@NotNull Json json) {
    return 0;
  }
}
