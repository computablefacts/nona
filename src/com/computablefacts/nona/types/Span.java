package com.computablefacts.nona.types;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
final public class Span implements Comparable<Span> {

  private final String text_;
  private final int begin_;
  private final int end_;
  private final Map<String, String> features_ = new HashMap<>();

  public Span(String text) {
    this(text, 0, text.length());
  }

  public Span(String text, int begin, int end) {

    Preconditions.checkNotNull(text, "text is null");
    Preconditions.checkArgument(begin >= 0 && begin <= text.length());
    Preconditions.checkArgument(end >= begin && end <= text.length());

    begin_ = begin;
    end_ = end;
    text_ = text;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Span other = (Span) obj;
    return Objects.equal(text_, other.text_) && Objects.equal(begin_, other.begin_)
        && Objects.equal(end_, other.end_) && Objects.equal(features_, other.features_);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(text_, begin_, end_, features_);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("text", text_).add("begin", begin_).add("end", end_)
        .add("features", features_).omitNullValues().toString();
  }

  @Override
  public int compareTo(@NotNull Span span) {
    return text_.compareTo(span.text_);
  }

  public String text() {
    return text_.substring(begin_, end_);
  }

  public int begin() {
    return begin_;
  }

  public int end() {
    return end_;
  }

  public void setFeature(String key, String value) {

    Preconditions.checkNotNull(key, "key is null");
    Preconditions.checkNotNull(value, "value is null");

    features_.put(key, value);
  }

  public String getFeature(String key) {
    return features_.get(Preconditions.checkNotNull(key, "key is null"));
  }
}
