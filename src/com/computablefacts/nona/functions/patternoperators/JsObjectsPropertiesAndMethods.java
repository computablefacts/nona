package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.jsObjectsPropertiesAndMethods;

import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class JsObjectsPropertiesAndMethods extends MatchPattern {

  public JsObjectsPropertiesAndMethods() {
    super("JS_OBJECTS_PROPERTIES_AND_METHODS", jsObjectsPropertiesAndMethods());
  }
}
