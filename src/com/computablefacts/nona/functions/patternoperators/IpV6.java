package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.ipv6;

import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class IpV6 extends MatchPattern {

  public IpV6() {
    super("IPV6", ipv6());
  }
}
