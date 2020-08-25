/*
 * Copyright (c) 2011-2020 MNCC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @author http://www.mncc.fr
 */
package com.computablefacts.nona.helpers;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
final public class NGramIterator extends AbstractIterator<String> {

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
  protected String computeNext() {
    if (overlaps_) {
      if (pos_ < string_.length() - length_ + 1) {
        String ngram = string_.substring(pos_, pos_ + length_);
        pos_ += 1;
        return ngram;
      }
    } else {
      if (pos_ < string_.length()) {
        String ngram = string_.substring(pos_, Math.min(string_.length(), pos_ + length_));
        pos_ += length_;
        return ngram;
      }
    }
    return endOfData();
  }
}
