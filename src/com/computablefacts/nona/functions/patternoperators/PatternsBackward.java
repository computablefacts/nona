package com.computablefacts.nona.functions.patternoperators;

import java.util.Set;
import java.util.stream.Collectors;

import com.computablefacts.nona.dictionaries.Elf;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.google.re2j.Pattern;

/**
 * Patterns that should be applied on a reversed text. See {@link #reverse(String)} for details.
 */
final public class PatternsBackward {

  private PatternsBackward() {}

  public static String reverse(String text) {
    return new StringBuilder(Strings.nullToEmpty(text)).reverse().toString();
  }

  public static String elfs() {
    return Joiner.on("|").join(Elf.load().stream().flatMap(elf -> {

      Set<String> abbr1 =
          elf.abbreviationLocal().stream().map(String::toUpperCase).collect(Collectors.toSet());
      Set<String> abbr2 = elf.abbreviationTransliterated().stream().map(String::toUpperCase)
          .collect(Collectors.toSet());

      return Sets.union(abbr1, abbr2).stream();
    }).filter(abbr -> !Strings.isNullOrEmpty(abbr)).map(abbr -> Pattern.quote(reverse(abbr))
        .replace("\\.", "\\.?").replaceAll("\\p{Zs}+", "\\\\p{Zs}+")).collect(Collectors.toSet()));
  }

  /**
   * Match and capture 2 groups :
   *
   * <ol>
   * <li>Group 1 : the entity legal form</li>
   * <li>Group 2 : the entity name</li>
   * </ol>
   * 
   * @return regular expression.
   */
  public static String companyName() {

    String elf = elfs();
    String connector1 = "\\p{Zs}+\\p{Ll}{1,3}\\p{Zs}+|\\p{Zs}+|[&,]";
    String connector2 = "\\p{Zs}+\\p{Ll}{1,3}\\p{Zs}+|\\p{Zs}+|[&,-]";
    String word = "(?:\\p{L}|\\p{N})+\\p{Lu}";

    // Company name must start with an upper-case letter
    // TODO : can a company name starts with a number?
    return "(?i)(" + elf + ")(?-i)(?:" + connector1 + ")((?:(?:" + connector2 + ")*(?:" + word
        + "))+)";
  }
}
