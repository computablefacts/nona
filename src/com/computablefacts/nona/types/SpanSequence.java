package com.computablefacts.nona.types;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import javax.validation.constraints.NotNull;

import com.computablefacts.nona.Generated;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CheckReturnValue;

@Deprecated
@CheckReturnValue
final public class SpanSequence implements Iterable<Span>, Comparable<SpanSequence> {

  private final List<Span> sequence_ = new ArrayList<>();

  public SpanSequence() {}

  public SpanSequence(List<Span> sequence) {
    sequence_.addAll(Preconditions.checkNotNull(sequence, "sequence should not be null"));
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    SpanSequence other = (SpanSequence) obj;
    return Objects.equal(sequence_, other.sequence_);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(sequence_);
  }

  @Generated
  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("sequence", sequence_).omitNullValues().toString();
  }

  @Override
  public Iterator<Span> iterator() {
    return sequence_.iterator();
  }

  /**
   * WARNING : DO NOT USE ON UNSORTED {@link SpanSequence}.
   * 
   * @param sequence {@link SpanSequence}.
   * @return a negative integer, zero, or a positive integer as this object is less than, equal to,
   *         or greater than the specified object.
   */
  @Override
  public int compareTo(@NotNull SpanSequence sequence) {
    for (int i = 0; i < sequence_.size(); i++) {
      if (i >= sequence.sequence_.size()) {
        return 1;
      }
      Span left = sequence_.get(i);
      Span right = sequence.sequence_.get(i);
      int cmp = left.compareTo(right);
      if (cmp != 0) {
        return cmp;
      }
    }
    return 0;
  }

  public void sort(Comparator<Span> comparator) {
    sequence_.sort(Preconditions.checkNotNull(comparator, "comparator should not be null"));
  }

  public Stream<Span> stream() {
    return sequence_.stream();
  }

  public int size() {
    return sequence_.size();
  }

  public Span span(int index) {

    Preconditions.checkArgument(0 <= index && index < sequence_.size(),
        "index should be such as 0 <= index < %s", size());

    return sequence_.get(index);
  }

  public SpanSequence sequence(int min, int max) {

    Preconditions.checkArgument(0 <= min && min <= sequence_.size(),
        "min should be such as 0 <= min <= %s", size());
    Preconditions.checkArgument(0 <= max && max <= sequence_.size(),
        "max should be such as 0 <= max <= %s", size());
    Preconditions.checkArgument(min < max, "min should be < max");

    return new SpanSequence(Lists.newArrayList(sequence_.subList(min, max)));
  }

  public void add(Span span) {
    sequence_.add(Preconditions.checkNotNull(span, "span should not be null"));
  }

  public void add(SpanSequence spans) {

    Preconditions.checkNotNull(spans, "spans should not be null");

    sequence_.addAll(spans.sequence_);
  }

  @CanIgnoreReturnValue
  public Span remove(int index) {

    Preconditions.checkArgument(0 <= index && index < sequence_.size(),
        "index should be such as 0 <= index < %s", size());

    return sequence_.remove(index);
  }
}
