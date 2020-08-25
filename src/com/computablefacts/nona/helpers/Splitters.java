package com.computablefacts.nona.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.computablefacts.nona.types.SpanSequence;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
final public class Splitters {

  private Splitters() {}

  public static Function<String, SpanSequence> overlappingNGrams(int windowLength) {

    Preconditions.checkArgument(windowLength >= 1, "windowLength must be >= 1");

    return string -> new SpanSequence(
        Lists.newArrayList(new NGramIterator(windowLength, string, true)));
  }

  public static Function<String, SpanSequence> nonOverlappingNGrams(int windowLength) {

    Preconditions.checkArgument(windowLength >= 1, "windowLength must be >= 1");

    return string -> new SpanSequence(
        Lists.newArrayList(new NGramIterator(windowLength, string, false)));
  }

  public static Function<SpanSequence, List<SpanSequence>> overlappingGroupBy(int windowLength,
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

  public static Function<SpanSequence, List<SpanSequence>> nonOverlappingGroupBy(int windowLength) {
    return overlappingGroupBy(windowLength, 0);
  }
}
