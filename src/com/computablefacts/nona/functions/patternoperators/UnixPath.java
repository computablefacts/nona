package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.unixPath;

import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class UnixPath extends MatchPattern {

  public UnixPath() {
    super("UNIX_PATH", unixPath());
  }
}
