package com.computablefacts.nona.types;

import java.util.Arrays;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.computablefacts.asterix.codecs.JsonCodec;
import com.computablefacts.nona.Generated;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
final public class Json implements Comparable<Json> {

  private final Map<String, Object>[] jsons_;

  public Json() {
    jsons_ = new Map[] {};
  }

  public Json(Map<String, Object> json) {

    Preconditions.checkNotNull(json, "json should not be null");

    jsons_ = new Map[] {json};
  }

  public Json(Map<String, Object>[] jsons) {

    Preconditions.checkNotNull(jsons, "jsons should not be null");

    jsons_ = jsons;
  }

  public static Json create(String json) {

    Map<String, Object>[] array = JsonCodec.asArray(json);

    if (array != null && array.length > 0) {
      return new Json(array);
    }

    Map<String, Object> object = JsonCodec.asObject(json);

    if (object != null && !object.isEmpty()) {
      return new Json(object);
    }
    return new Json();
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

  @Generated
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

  public String asString() {
    return jsons_.length == 0 ? ""
        : jsons_.length > 1 ? JsonCodec.asString(jsons_) : JsonCodec.asString(jsons_[0]);
  }
}
