package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.dateTime;

import java.util.HashMap;
import java.util.Map;

import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class DateTime extends MatchPattern {

  private static final Map<Integer, String> GROUPS = new HashMap<>();

  static {
    GROUPS.put(2, "DATE");
    GROUPS.put(3, "TIME");
    GROUPS.put(4, "TIMEZONE");
  }

  public DateTime() {
    super("DATE_TIME", dateTime(), GROUPS);
  }
}
