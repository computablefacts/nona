package com.computablefacts.nona.functions.patternoperators;

import com.google.common.base.Strings;
import com.google.errorprone.annotations.CheckReturnValue;

/**
 * Patterns that should be applied on a compacted text. See {@link #compact(String)} for details.
 *
 * The first group must always be the full matched pattern.
 */
@CheckReturnValue
final public class PatternsCompact {

  private PatternsCompact() {}

  public static String compact(String text) {
    return Strings.nullToEmpty(text).toUpperCase().replaceAll("[^\\p{L}\\p{N}]", "");
  }

  /**
   * Regex for International Bank Account Number extraction. Match and capture 4 groups :
   *
   * <ol>
   * <li>Group 1 : whole match</li>
   * <li>Group 2 : the country code</li>
   * <li>Group 3 : the check digits</li>
   * <li>Group 4 : the rest</li>
   * </ol>
   *
   * @return regular expression.
   */
  public static String iban() {

    String country = "[A-Za-z]{2}";
    String checksum = "\\d{2}";
    String rest = "[A-Za-z\\d]+";

    return "((" + country + ")(" + checksum + ")(" + rest + "))";
  }
}
