package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.unixPath;

public class UnixPath extends MatchPattern {

  public UnixPath() {
    super("UNIX_PATH", unixPath());
  }
}
