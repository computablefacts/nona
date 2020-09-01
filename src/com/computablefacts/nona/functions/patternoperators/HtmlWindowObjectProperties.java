package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.htmlWindowObjectProperties;

public class HtmlWindowObjectProperties extends MatchPattern {

  public HtmlWindowObjectProperties() {
    super("HTML_WINDOW_OBJECT_PROPERTIES", htmlWindowObjectProperties());
  }
}
