package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.percent;

import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class Percent extends MatchPattern {

  public Percent() {
    super("PERCENT", percent());
  }
}
