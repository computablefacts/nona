package com.computablefacts.nona.functions.patternoperators;

import com.google.common.base.Strings;

/**
 * Patterns that should be applied on a compacted text. See {@link #compact(String)} for details.
 */
final public class PatternsCompact {

  private PatternsCompact() {}

  public static String compact(String text) {
    return Strings.nullToEmpty(text).toUpperCase().replaceAll("[^\\p{L}\\p{N}]", "");
  }

  /**
   * Match and capture 3 groups :
   *
   * <ol>
   * <li>Group 1 : the country code</li>
   * <li>Group 2 : the check digits</li>
   * <li>Group 3 : the rest</li>
   * </ol>
   *
   * @return regular expression.
   */
  public static String iban() {

    String country = "[A-Za-z]{2}";
    String checksum = "\\d{2}";
    String rest = "[A-Za-z\\d]+";

    return "(" + country + ")(" + checksum + ")(" + rest + ")";
  }
}
