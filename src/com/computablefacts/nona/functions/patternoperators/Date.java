package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.date;

public class Date extends MatchPattern {

  public Date() {
    super("DATE", date());
  }
}
