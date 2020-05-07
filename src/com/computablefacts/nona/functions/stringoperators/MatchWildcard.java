package com.computablefacts.nona.functions.stringoperators;

import java.util.List;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Var;

@CheckReturnValue
public class MatchWildcard extends Function {

  public MatchWildcard() {
    super("MATCHWILDCARD", true);
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 2,
        "MATCHWILDCARD takes exactly two parameters.");
    Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string",
        parameters.get(0));
    Preconditions.checkArgument(parameters.get(1).isString(), "%s should be a string",
        parameters.get(1));

    String string = parameters.get(0).asString();
    String pattern = parameters.get(1).asString();

    return BoxedType.create(match(string, pattern));
  }

  /**
   * Based on @{link https://research.swtch.com/glob}.
   *
   * @param name where to search.
   * @param pattern what to search.
   * @return true iif pattern has been matched in name. False otherwise. This method is case
   *         insensitive.
   */
  boolean match(String name, String pattern) {

    int lenName = name == null ? 0 : name.length();
    int lenPattern = pattern == null ? 0 : pattern.length();

    if (lenName == 0) {
      return (lenPattern == 0);
    }

    @Var
    int px = 0;
    @Var
    int nx = 0;
    @Var
    int nextPx = 0;
    @Var
    int nextNx = 0;

    while (px < lenPattern || nx < lenName) {
      if (px < lenPattern) {
        char c = Character.toLowerCase(pattern.charAt(px));
        switch (c) {
          case '*': { // zero-or-more-character wildcard
            // Try to match at nx. If that doesn't work out, restart at nx+1 next.
            nextPx = px;
            nextNx = nx + 1;
            px++;
            continue;
          }
          case '?': { // single-character wildcard
            if (nx < lenName) {
              px++;
              nx++;
              continue;
            }
            // fall through
          }
          default: { // ordinary character
            if (nx < lenName && Character.toLowerCase(name.charAt(nx)) == c) {
              px++;
              nx++;
              continue;
            }
          }
        }
      }

      // Mismatch. Maybe restart.
      if (0 < nextNx && nextNx <= lenName) {
        px = nextPx;
        nx = nextNx;
        continue;
      }
      return false;
    }

    // Matched all of pattern to all of name. Success.
    return true;
  }
}
