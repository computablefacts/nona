package com.computablefacts.nona.types;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

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
  private final boolean isArray_;

  public Json() {
    jsons_ = new Map[] {};
    isArray_ = false;
  }

  public Json(String json) {

    Preconditions.checkNotNull(json, "json should not be null");

    Map<String, Object>[] array = JsonCodec.asArray(json);

    if (array != null && array.length > 0) {
      jsons_ = array;
      isArray_ = true;
    } else {

      Map<String, Object> object = JsonCodec.asObject(json);

      if (object != null && !object.isEmpty()) {
        jsons_ = new Map[] {object};
        isArray_ = false;
      } else {
        jsons_ = new Map[] {};
        isArray_ = json.trim().indexOf("[") == 0;
      }
    }
  }

  public Json(Map<String, Object> json) {

    Preconditions.checkNotNull(json, "json should not be null");

    jsons_ = new Map[] {json};
    isArray_ = false;
  }

  public Json(Map<String, Object>[] jsons) {

    Preconditions.checkNotNull(jsons, "jsons should not be null");

    jsons_ = jsons;
    isArray_ = true;
  }

  public int nbObjects() {
    return jsons_.length;
  }

  public Map<String, Object> object() {
    return object(0);
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
    return Arrays.equals(jsons_, other.jsons_) && isArray_ == other.isArray_;
  }

  @Override
  public int hashCode() {
    return Objects.hash(Arrays.hashCode(jsons_), isArray_);
  }

  @Generated
  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("json", jsons_).add("is_array", isArray_)
        .omitNullValues().toString();
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
    return jsons_.length == 0 ? isArray_ ? "[]" : "{}"
        : jsons_.length > 1 || isArray_ ? JsonCodec.asString(jsons_)
            : JsonCodec.asString(jsons_[0]);
  }
}
