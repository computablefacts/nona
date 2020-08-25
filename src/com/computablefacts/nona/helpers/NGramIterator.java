package com.computablefacts.nona.helpers;

import com.computablefacts.nona.types.Span;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
final public class NGramIterator extends AbstractIterator<Span> {

  private final String string_;
  private final int length_;
  private final boolean overlaps_;
  private int pos_ = 0;

  public NGramIterator(int length, String str) {
    this(length, str, true);
  }

  public NGramIterator(int length, String str, boolean overlaps) {

    Preconditions.checkNotNull(str, "str should not be null");
    Preconditions.checkArgument(length > 0, "length must be > 0");

    string_ = str;
    length_ = length;
    overlaps_ = overlaps;
  }

  @Override
  protected Span computeNext() {
    if (overlaps_) {
      if (pos_ < string_.length() - length_ + 1) {
        Span span = new Span(string_, pos_, pos_ + length_);
        pos_ += 1;
        return span;
      }
    } else {
      if (pos_ < string_.length()) {
        Span span = new Span(string_, pos_, Math.min(string_.length(), pos_ + length_));
        pos_ += length_;
        return span;
      }
    }
    return endOfData();
  }
}
