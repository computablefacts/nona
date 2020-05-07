package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.time;

public class Time extends MatchPattern {

  public Time() {
    super("TIME", time());
  }
}
