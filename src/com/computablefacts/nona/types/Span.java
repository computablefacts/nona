package com.computablefacts.nona.types;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
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

  public Span(Span span) {
    this(span, false);
  }

  public Span(Span span, boolean copyFeatures) {

    Preconditions.checkNotNull(span, "span is null");

    text_ = span.text_;
    begin_ = span.begin_;
    end_ = span.end_;

    if (copyFeatures) {
      features_.putAll(span.features_);
    }
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

  /**
   * The raw text.
   *
   * @return raw text.
   */
  public String rawText() {
    return text_;
  }

  /**
   * The span extracted from the raw text.
   *
   * @return span.
   */
  public String text() {
    return text_.substring(begin_, end_);
  }

  /**
   * Beginning of the span.
   *
   * @return position.
   */
  public int begin() {
    return begin_;
  }

  /**
   * End of the span.
   *
   * @return position.
   */
  public int end() {
    return end_;
  }

  /**
   * Returns the length of the span.
   *
   * @return The end position less the start position, plus one.
   */
  public int length() {
    return end_ - begin_;
  }

  /**
   * Checks whether the given span overlaps this span.
   *
   * @param span span.
   * @return true if the spans overlap, false otherwise.
   */
  public boolean overlaps(Span span) {

    Preconditions.checkNotNull(span, "span should not be null");

    return begin_ < span.end() && end_ > span.begin();
  }

  /**
   * Checks whether the left of this span (ex. [4, 7]) overlaps the right of the given span (ex. [2,
   * 5]).
   *
   * @param span span span.
   * @return true if the spans overlap, false otherwise.
   */
  public boolean overlapsRight(Span span) {

    Preconditions.checkNotNull(span, "span should not be null");

    return end_ > span.end();
  }

  /**
   * Checks whether the right of this span (ex. [4, 7]) overlaps the left of the given span (ex. [6,
   * 9]).
   *
   * @param span span span.
   * @return true if the spans overlap, false otherwise.
   */
  public boolean overlapsLeft(Span span) {

    Preconditions.checkNotNull(span, "span should not be null");

    return begin_ < span.begin();
  }

  /**
   * Checks whether this span (ex. [4, 7]) completely overlaps the given span (ex. [5, 6]).
   *
   * @param span span span.
   * @return true if the spans overlap, false otherwise.
   */
  public boolean overlapsAll(Span span) {

    Preconditions.checkNotNull(span, "span should not be null");

    return overlapsLeft(span) && overlapsRight(span);
  }

  /**
   * Checks whether a position falls into this span range.
   * 
   * @param position position.
   * @return true if the position falls into this span, false otherwise.
   */
  public boolean overlaps(int position) {
    return begin_ <= position && position <= end_;
  }

  public void setGroupCount(int count) {

    Preconditions.checkArgument(count >= 0, "count should be >= 0 : %s", count);

    setFeature("GROUP_COUNT", Integer.toString(count, 10));
  }

  public void removeAllGroups() {

    Set<String> groups =
        features_.keySet().stream().filter(f -> f.startsWith("GROUP_")).collect(Collectors.toSet());

    for (String group : groups) {
      features_.remove(group);
    }
  }

  public void setGroup(int index, String value) {

    Preconditions.checkArgument(index >= 0, "index should be >= 0 : %s", index);

    setFeature("GROUP_" + index, Strings.nullToEmpty(value));
  }

  public String getGroup(int index) {

    Preconditions.checkArgument(index >= 0, "index should be >= 0 : %s", index);

    return getFeature("GROUP_" + index);
  }

  public void setFeature(String key, String value) {

    Preconditions.checkNotNull(key, "key is null");
    Preconditions.checkNotNull(value, "value is null");

    features_.put(key, value);
  }

  public String getFeature(String key) {
    return features_.get(Preconditions.checkNotNull(key, "key is null"));
  }

  public void removeFeature(String key) {
    features_.remove(Preconditions.checkNotNull(key, "key is null"));
  }
}
