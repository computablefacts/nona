package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.number;

import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class Number extends MatchPattern {

  public Number() {
    super("NUMBER", number());
  }
}
