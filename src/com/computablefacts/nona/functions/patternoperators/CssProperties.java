package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.cssProperties;

public class CssProperties extends MatchPattern {

  public CssProperties() {
    super("CSS_PROPERTIES", cssProperties());
  }
}
