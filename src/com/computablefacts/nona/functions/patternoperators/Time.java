package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.time;

import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class Time extends MatchPattern {

  public Time() {
    super("TIME", time());
  }
}
