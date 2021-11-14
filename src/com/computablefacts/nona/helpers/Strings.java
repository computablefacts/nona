package com.computablefacts.nona.helpers;

import javax.xml.bind.DatatypeConverter;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Var;

final public class Strings {

  private Strings() {}

  @Deprecated
  public static String encode(String text) {
    return com.computablefacts.nona.Function.encode(text).replace("\n", "\\u000d").replace("\r",
        "\\u000a");
  }

  @Deprecated
  public static String decode(String text) {
    return com.computablefacts.nona.Function
        .decode(text.replace("\\u000d", "\n").replace("\\u000a", "\r"));
  }

  /**
   * Anagram hashing function.
   *
   * See http://www.lrec-conf.org/proceedings/lrec2006/pdf/229_pdf.pdf for details.
   *
   * @param word word.
   * @return hash.
   */
  public static String anagramHash(String word) {

    Preconditions.checkNotNull(word, "word should not be null");

    @Var
    long hash = 0;

    for (int i = 0; i < word.length(); i++) {
      hash = (long) (hash + Math.pow(word.codePointAt(i), 5));
    }
    return Long.toString(hash, 10);
  }

  /**
   * Normalized edit distance.
   *
   * @param word1 word.
   * @param word2 word.
   * @return normalized distance. This value is in [0, 1].
   */
  public static double normalizedLevenshteinDistance(CharSequence word1, CharSequence word2) {

    Preconditions.checkNotNull(word1, "word1 should not be null");
    Preconditions.checkNotNull(word2, "word2 should not be null");

    if (word1.length() == 0 && word2.length() == 0) {
      return 0.0;
    }
    if (word1.equals(word2)) {
      return 0.0;
    }
    return (double) levenshteinDistance(word1, word2)
        / (double) Math.max(word1.length(), word2.length());
  }

  /**
   * Implementation of the Levenshtein algorithm to compute the edit distance between two words.
   *
   * @param word1 word.
   * @param word2 word.
   * @return distance. This value is an integer in [0, max(word1.length, word2.length)]. 0 means
   *         both words are equals.
   */
  public static int levenshteinDistance(CharSequence word1, CharSequence word2) {

    Preconditions.checkNotNull(word1, "word1 should not be null");
    Preconditions.checkNotNull(word2, "word2 should not be null");

    if (word1.length() == 0 && word2.length() == 0) {
      return 0;
    }
    if (word1.equals(word2)) {
      return 0;
    }

    int len0 = word1.length() + 1;
    int len1 = word2.length() + 1;

    // The array of distances
    @Var
    int[] cost = new int[len0];
    @Var
    int[] newcost = new int[len0];

    // Initial cost of skipping prefix in String s0
    for (int i = 0; i < len0; i++) {
      cost[i] = i;
    }

    // Dynamically computing the array of distances

    // Transformation cost for each letter in s1
    for (int j = 1; j < len1; j++) {

      // Initial cost of skipping prefix in String s1
      newcost[0] = j;

      // Transformation cost for each letter in s0
      for (int i = 1; i < len0; i++) {

        // Matching current letters in both strings
        int match = (word1.charAt(i - 1) == word2.charAt(j - 1)) ? 0 : 1;

        // Computing cost for each transformation
        int cost_replace = cost[i - 1] + match;
        int cost_insert = cost[i] + 1;
        int cost_delete = newcost[i - 1] + 1;

        // Keep minimum cost
        newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
      }

      // Swap cost/newcost arrays
      int[] swap = cost;
      cost = newcost;
      newcost = swap;
    }

    // The distance is the cost for transforming all letters in both strings
    return cost[len0 - 1];
  }

  /**
   * Convert a byte array to an hexadecimal string.
   *
   * @param bytes byte array.
   * @return hexadecimal string.
   */
  public static String hex(byte[] bytes) {
    return DatatypeConverter.printHexBinary(bytes);
  }

  /**
   * Convert an hexadecimal string to a byte array.
   *
   * @param string hexadecimal string.
   * @return byte array.
   */
  public static byte[] unhex(String string) {
    return DatatypeConverter.parseHexBinary(string);
  }

  /**
   * <p>
   * Checks whether the String a valid Java number.
   * </p>
   *
   * <p>
   * Valid numbers include hexadecimal marked with the <code>0x</code> qualifier, scientific
   * notation and numbers marked with a type qualifier (e.g. 123L).
   * </p>
   *
   * <p>
   * <code>Null</code> and empty String will return <code>false</code>.
   * </p>
   *
   * @param text the <code>String</code> to check
   * @return <code>true</code> if the string is a correctly formatted number
   *
   * @see <a href=
   *      "https://commons.apache.org/proper/commons-math">https://commons.apache.org/proper/commons-math</a>
   */
  @Deprecated
  public static boolean isNumber(String text) {

    if (com.google.common.base.Strings.isNullOrEmpty(text)) {
      return false;
    }

    char[] chars = text.toCharArray();
    @Var
    int sz = chars.length;
    @Var
    boolean hasExp = false;
    @Var
    boolean hasDecPoint = false;
    @Var
    boolean allowSigns = false;
    @Var
    boolean foundDigit = false;

    // deal with any possible sign up front
    int start = (chars[0] == '-') ? 1 : 0;
    if (sz > start + 1 && chars[start] == '0' && chars[start + 1] == 'x') {
      @Var
      int i = start + 2;
      if (i == sz) {
        return false; // text == "0x"
      }

      // checking hex (it can't be anything else)
      for (; i < chars.length; i++) {
        if ((chars[i] < '0' || chars[i] > '9') && (chars[i] < 'a' || chars[i] > 'f')
            && (chars[i] < 'A' || chars[i] > 'F')) {
          return false;
        }
      }
      return true;
    }

    sz--; // don't want to loop to the last char, check it afterwords

    // for type qualifiers
    @Var
    int i = start;

    // loop to the next to last char or to the last char if we need another digit to
    // make a valid number (e.g. chars[0..5] = "1234E")
    while (i < sz || (i < sz + 1 && allowSigns && !foundDigit)) {
      if (chars[i] >= '0' && chars[i] <= '9') {
        foundDigit = true;
        allowSigns = false;

      } else if (chars[i] == '.') {
        if (hasDecPoint || hasExp) {

          // two decimal points or dec in exponent
          return false;
        }
        hasDecPoint = true;
      } else if (chars[i] == 'e' || chars[i] == 'E') {

        // we've already taken care of hex.
        if (hasExp) {

          // two E's
          return false;
        }
        if (!foundDigit) {
          return false;
        }
        hasExp = true;
        allowSigns = true;
      } else if (chars[i] == '+' || chars[i] == '-') {
        if (!allowSigns) {
          return false;
        }
        allowSigns = false;
        foundDigit = false; // we need a digit after the E
      } else {
        return false;
      }
      i++;
    }
    if (i < chars.length) {
      if (chars[i] >= '0' && chars[i] <= '9') {

        // no type qualifier, OK
        return true;
      }
      if (chars[i] == 'e' || chars[i] == 'E') {

        // can't have an E at the last byte
        return false;
      }
      if (chars[i] == '.') {
        if (hasDecPoint || hasExp) {

          // two decimal points or dec in exponent
          return false;
        }

        // single trailing decimal point after non-exponent is ok
        return foundDigit;
      }
      if (!allowSigns
          && (chars[i] == 'd' || chars[i] == 'D' || chars[i] == 'f' || chars[i] == 'F')) {
        return foundDigit;
      }
      if (chars[i] == 'l' || chars[i] == 'L') {

        // not allowing L with an exponent or decimal point
        return foundDigit && !hasExp && !hasDecPoint;
      }

      // last character is illegal
      return false;
    }

    // allowSigns is true iff the val ends in 'E'
    // found digit it to make sure weird stuff like '.' and '1E-' doesn't pass
    return !allowSigns && foundDigit;
  }
}
