package com.computablefacts.nona.functions.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.computablefacts.nona.types.SpanSequence;
import com.google.common.base.Preconditions;

final public class Splitters {

  private Splitters() {}

  public static Function<SpanSequence, List<SpanSequence>> overlapping(int windowLength,
      int overlapLength) {

    Preconditions.checkArgument(windowLength >= 1, "windowLength must be >= 1");
    Preconditions.checkArgument(overlapLength < windowLength,
        "overlapLength must be < windowLength");

    return sequence -> {

      int shift = windowLength - overlapLength;
      List<SpanSequence> ngrams = new ArrayList<>();

      for (int i = 0; i < sequence.size(); i += shift) {
        ngrams.add(sequence.sequence(i, Math.min(i + windowLength, sequence.size())));
      }
      return ngrams;
    };
  }

  public static Function<SpanSequence, List<SpanSequence>> nonOverlapping(int windowLength) {
    return overlapping(windowLength, 0);
  }
}
