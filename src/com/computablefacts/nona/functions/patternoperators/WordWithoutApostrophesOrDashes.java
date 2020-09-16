package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.wordWithoutApostrophesOrDashes;

import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class WordWithoutApostrophesOrDashes extends MatchPattern {

  public WordWithoutApostrophesOrDashes() {
    super("WORD_WITHOUT_APOSTROPHES_OR_DASHES", wordWithoutApostrophesOrDashes());
  }
}
