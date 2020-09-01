package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.url;

import java.util.HashMap;
import java.util.Map;

import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class Url extends MatchPattern {

  private static final Map<Integer, String> GROUPS = new HashMap<>();

  static {
    GROUPS.put(2, "PROTOCOL");
    GROUPS.put(3, "USERNAME");
    GROUPS.put(4, "PASSWORD");
    GROUPS.put(5, "HOSTNAME");
    GROUPS.put(6, "PORT");
    GROUPS.put(7, "PATH");
    GROUPS.put(8, "QUERY_STRING");
  }

  public Url() {
    super("URL", url(), GROUPS);
  }
}
