package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.ecmaScript6Keywords;

import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class EcmaScript6Keywords extends MatchPattern {

  public EcmaScript6Keywords() {
    super("ECMASCRIPT6_KEYWORDS", ecmaScript6Keywords());
  }
}
