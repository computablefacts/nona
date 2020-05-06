package com.computablefacts.nona.functions.stringoperators;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Var;
import com.google.re2j.Matcher;
import com.google.re2j.Pattern;

@CheckReturnValue
public class RegexExtract extends Function {

  private static final LoadingCache<String, Pattern> cache_ =
      CacheBuilder.newBuilder().recordStats().maximumSize(100).expireAfterWrite(1, TimeUnit.HOURS)
          .build(new CacheLoader<String, Pattern>() {

            @Override
            public Pattern load(String key) {
              return Pattern.compile(key);
            }
          });

  public RegexExtract() {
    super("REGEXEXTRACT", true);
  }

  protected RegexExtract(String expression) {
    super(expression, true);
  }

  @Override
  public BoxedType evaluate(List<BoxedType> parameters) {

    Preconditions.checkArgument(parameters.size() == 2,
        "REGEXEXTRACT takes exactly two parameters : %s", parameters);
    Preconditions.checkArgument(parameters.get(0).isString(), "%s should be a string",
        parameters.get(0));
    Preconditions.checkArgument(parameters.get(1).isString(), "%s should be a string",
        parameters.get(1));

    SpanSequence sequence = new SpanSequence();
    String text = parameters.get(0).asString();
    Pattern pattern = cache_.getUnchecked(parameters.get(1).asString());
    Matcher matcher = pattern.matcher(text);

    while (matcher.find()) {

      int start = matcher.start();
      int end = matcher.end();
      String str = text.substring(start, end);

      @Var
      int i = 0;
      while (i < str.length() && isWhitespace(str.charAt(i))) { // Trim left
        i++;
      }

      @Var
      int j = str.length() - 1;
      while (j >= 0 && isWhitespace(str.charAt(j))) { // Trim right
        j--;
      }
      j++;

      if (i <= j) { // Take non-breaking spaces into account

        Span span = new Span(text, start + i, end - (str.length() - j));

        for (int k = 1; k < matcher.groupCount() + 1; k++) {
          span.setFeature("GROUP_" + k, Strings.nullToEmpty(matcher.group(k)));
        }
        span.setFeature("GROUP_COUNT", Integer.toString(matcher.groupCount(), 10));

        sequence.add(span);
      }
    }
    return BoxedType.create(sequence);
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
