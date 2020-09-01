package com.computablefacts.nona.functions.patternoperators;

import static com.computablefacts.nona.functions.patternoperators.PatternsForward.winPath;

import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public class WinPath extends MatchPattern {

  public WinPath() {
    super("WIN_PATH", winPath());
  }
}
