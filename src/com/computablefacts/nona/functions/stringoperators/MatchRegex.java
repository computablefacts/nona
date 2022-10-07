package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.asterix.Span;
import com.computablefacts.asterix.SpanSequence;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.eCategory;
import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Var;
import com.google.re2j.Matcher;
import com.google.re2j.Pattern;
import java.util.List;
import java.util.concurrent.TimeUnit;

@CheckReturnValue
public class MatchRegex extends Function {

  private static final LoadingCache<String, Pattern> cache_ = CacheBuilder.newBuilder().recordStats().maximumSize(100)
      .expireAfterWrite(1, TimeUnit.HOURS).build(new CacheLoader<String, Pattern>() {

        @Override
        public Pattern load(String key) {
          return Pattern.compile(key);
        }
      });

  public MatchRegex() {
    super(eCategory.STRING_OPERATORS, "MATCH_REGEX",
        "MATCH_REGEX(x, y) returns all substrings of x matching a given regular expression y.");
  }

  protected MatchRegex(String expression) {
    super(eCategory.STRING_OPERATORS, expression,
        expression.trim().toUpperCase() + "(x, y) returns all substrings of x matching a given regular expression y.");
  }

  @Override
  public BoxedType<?> evaluate(List<BoxedType<?>> parameters) {

    Preconditions.checkArgument(parameters.size() == 2, "MATCH_REGEX takes exactly two parameters : %s", parameters);

    SpanSequence sequence = new SpanSequence();
    String text = parameters.get(0).asString();
    Pattern pattern = cache_.getUnchecked(parameters.get(1).asString());
    Matcher matcher = pattern.matcher(text);

    while (matcher.find()) {

      int start = matcher.start();
      int end = matcher.end();
      String str = text.substring(start, end);

      @Var int i = 0;
      while (i < str.length() && isWhitespace(str.charAt(i))) { // Trim left
        i++;
      }

      @Var int j = str.length() - 1;
      while (j >= 0 && isWhitespace(str.charAt(j))) { // Trim right
        j--;
      }
      j++;

      if (i <= j) { // Take non-breaking spaces into account

        Span span = new Span(text, start + i, end - (str.length() - j));

        for (int k = 1; k < matcher.groupCount() + 1; k++) {
          span.setGroup(k, matcher.group(k));
        }

        sequence.add(span);
      }
    }
    return box(sequence);
  }

  /**
   * Check if a character is a whitespace. This method takes into account Unicode space characters.
   *
   * @param c character as a unicode code point.
   * @return true if c is a space character.
   */
  private boolean isWhitespace(int c) {
    return Character.isWhitespace(c) || Character.isSpaceChar(c);
  }
}
