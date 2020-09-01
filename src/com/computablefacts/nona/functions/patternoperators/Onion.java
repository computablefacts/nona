package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.onion;

import java.util.HashMap;
import java.util.Map;

import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class Onion extends MatchPattern {

  private static final Map<Integer, String> GROUPS = new HashMap<>();

  static {
    GROUPS.put(2, "PROTOCOL");
    GROUPS.put(3, "HOSTNAME");
    GROUPS.put(4, "PORT");
    GROUPS.put(5, "PATH");
  }

  public Onion() {
    super("ONION", onion(), GROUPS);
  }
}
