package com.computablefacts.nona.types;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
final public class SpanSequence implements Comparable<SpanSequence> {

  private final List<Span> sequence_ = new ArrayList<>();

  public SpanSequence() {

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

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("sequence", sequence_).omitNullValues().toString();
  }

  @Override
  public int compareTo(@NotNull SpanSequence spanSequence) {
    for (int i = 0; i < sequence_.size(); i++) {
      if (i >= spanSequence.sequence_.size()) {
        return 1;
      }
      Span left = sequence_.get(i);
      Span right = spanSequence.sequence_.get(i);
      int cmp = left.compareTo(right);
      if (cmp != 0) {
        return cmp;
      }
    }
    return 0;
  }

  public List<Span> sequence() {
    return sequence_;
  }

  public void add(Span span) {
    sequence_.add(Preconditions.checkNotNull(span, "span is null"));
  }

  public void add(SpanSequence spans) {

    Preconditions.checkNotNull(spans, "spans is null");

    sequence_.addAll(spans.sequence());
  }
}
