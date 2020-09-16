package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.wordWithApostrophesOrDashes;

import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class WordWithApostrophesOrDashes extends MatchPattern {

  public WordWithApostrophesOrDashes() {
    super("WORD_WITH_APOSTROPHES_OR_DASHES", wordWithApostrophesOrDashes());
  }
}
