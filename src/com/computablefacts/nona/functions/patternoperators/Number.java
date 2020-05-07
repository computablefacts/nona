package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.number;

public class Number extends MatchPattern {

  public Number() {
    super("NUMBER", number());
  }
}
