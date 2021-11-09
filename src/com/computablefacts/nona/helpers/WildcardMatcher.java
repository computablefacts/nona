package com.computablefacts.nona.helpers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Var;

@Deprecated
@CheckReturnValue
final public class WildcardMatcher {

  private WildcardMatcher() {}

  /**
   * Compute the minimum number of literals contained in a given pattern.
   *
   * @param pattern pattern.
   * @return the minimum number of literals contained in pattern.
   */
  @Beta
  public static int minNumberOfCharacters(String pattern) {

    if (pattern == null || pattern.isEmpty()) {
      return 0;
    }

    @Var
    int nbLiterals = 0;

    for (int i = 0; i < pattern.length(); i++) {
      if (pattern.charAt(i) != '*') {
        nbLiterals++;
      }
    }
    return nbLiterals;
  }

  /**
   * Get all prefix literals.
   *
   * @param pattern pattern.
   * @return literals found as pattern prefix.
   */
  public static @NotNull String prefix(String pattern) {

    Preconditions.checkNotNull(pattern, "pattern should not be null");

    StringBuilder builder = new StringBuilder(pattern.length());

    for (int i = 0; i < pattern.length(); i++) {
      if (pattern.charAt(i) != '*' && pattern.charAt(i) != '?') {
        builder.append(pattern.charAt(i));
      } else {
        return builder.toString();
      }
    }
    return builder.toString();
  }

  /**
   * Get all suffix literals.
   *
   * @param pattern pattern.
   * @return literals found as pattern suffix.
   */
  public static @NotNull String suffix(String pattern) {

    Preconditions.checkNotNull(pattern, "pattern should not be null");

    StringBuilder builder = new StringBuilder(pattern.length());

    for (int i = pattern.length() - 1; i >= 0; i--) {
      if (pattern.charAt(i) != '*' && pattern.charAt(i) != '?') {
        builder.append(pattern.charAt(i));
      } else {
        return builder.reverse().toString();
      }
    }
    return builder.reverse().toString();
  }

  /**
   * Split pattern around wildcards.
   *
   * @param pattern pattern.
   * @return sequence of alternating wildcards and literals.
   */
  public static @NotNull List<String> split(String pattern) {

    Preconditions.checkNotNull(pattern, "pattern should not be null");

    List<String> splits = new ArrayList<>();
    StringBuilder builder = new StringBuilder(pattern.length());

    for (int i = 0; i < pattern.length(); i++) {
      if (pattern.charAt(i) != '*' && pattern.charAt(i) != '?') {
        builder.append(pattern.charAt(i));
      } else {
        String str = builder.toString();
        if (!Strings.isNullOrEmpty(str)) {
          splits.add(str);
        }
        builder.setLength(0);
        splits.add(Character.toString(pattern.charAt(i)));
      }
    }
    if (builder.length() > 0) {
      String str = builder.toString();
      if (!Strings.isNullOrEmpty(str)) {
        splits.add(str);
      }
    }
    return splits;
  }

  /**
   * Check if a pattern contains wildcard characters.
   *
   * @param pattern pattern.
   * @return true iif pattern contains at least one wildcard character.
   */
  public static boolean hasWildcards(String pattern) {

    Preconditions.checkNotNull(pattern, "pattern should not be null");

    for (int i = 0; i < pattern.length(); i++) {
      if (pattern.charAt(i) == '*' || pattern.charAt(i) == '?') {
        return true;
      }
    }
    return false;
  }

  /**
   * Check if a pattern contains only wildcard characters.
   *
   * @param pattern pattern.
   * @return true iif pattern contains only wildcard characters.
   */
  public static boolean isOnlyMadeOfWildcards(String pattern) {

    Preconditions.checkNotNull(pattern, "pattern should not be null");

    if (pattern.length() == 0) {
      return false;
    }

    for (int i = 0; i < pattern.length(); i++) {
      if (pattern.charAt(i) != '*' && pattern.charAt(i) != '?') {
        return false;
      }
    }
    return true;
  }

  /**
   * Check if a pattern starts with a wildcard character.
   *
   * @param pattern pattern.
   * @return true iif pattern starts with a wildcard character.
   */
  public static boolean startsWithWildcard(String pattern) {

    Preconditions.checkNotNull(pattern, "pattern should not be null");

    if (pattern.length() == 0) {
      return false;
    }

    char c = pattern.charAt(0);
    return c == '*' || c == '?';
  }

  /**
   * Check if a pattern ends with a wildcard character.
   *
   * @param pattern pattern.
   * @return true iif pattern ends with a wildcard character.
   */
  public static boolean endsWithWildcard(String pattern) {

    Preconditions.checkNotNull(pattern, "pattern should not be null");

    if (pattern.length() == 0) {
      return false;
    }

    char c = pattern.charAt(pattern.length() - 1);
    return c == '*' || c == '?';
  }

  /**
   * Compact a pattern by removing sequences of consecutive '*' characters.
   *
   * @param pattern pattern.
   * @return Compacted pattern.
   */
  public static @NotNull String compact(String pattern) {
    return Preconditions.checkNotNull(pattern, "pattern should not be null").replaceAll("(\\*)\\1+",
        "*");
  }

  /**
   * Based on @{link https://research.swtch.com/glob}.
   *
   * @param name where to search.
   * @param pattern what to search.
   * @return true iif pattern has been matched in name. False otherwise. This method is case
   *         insensitive.
   */
  public static boolean match(String name, String pattern) {
    return match(name, pattern, null);
  }

  /**
   * Based on @{link https://research.swtch.com/glob}.
   *
   * @param name where to search.
   * @param pattern what to search.
   * @param match the matched string (optional).
   * @return true iif pattern has been matched in name. False otherwise. This method is case
   *         insensitive.
   */
  public static boolean match(String name, String pattern, StringBuilder match) {

    String newPattern = pattern == null ? null : compact(pattern);
    int lenName = name == null ? 0 : name.length();
    int lenPattern = newPattern == null ? 0 : newPattern.length();

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
      if (match != null) {
        if (px == 0) {
          match.setLength(nx);
        }
        if (nx >= nextNx) {
          if (nx < match.length()) {
            match.setLength(nx);
          }
          if (nx < lenName) {
            match.append(name.charAt(nx));
          }
        }
      }
      if (px < lenPattern) {
        char c = Character.toLowerCase(newPattern.charAt(px));
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
      if (match != null) {
        match.setLength(0);
      }
      return false;
    }

    // Matched all of pattern to all of name. Success.
    return true;
  }
}
