package com.computablefacts.nona.helpers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.computablefacts.asterix.Span;
import com.computablefacts.asterix.SpanSequence;
import com.computablefacts.asterix.StringIterator;
import com.computablefacts.asterix.codecs.BigDecimalCodec;
import com.google.common.base.Function;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.re2j.Matcher;
import com.google.re2j.Pattern;

@CheckReturnValue
final public class Codecs {

  public static final Span SPAN_EMPTY = new Span("", 0, 0);
  public static final SpanSequence SPAN_SEQUENCE_EMPTY = new SpanSequence();

  /**
   * A function that encodes a primitive to a lexicographically sortable string.
   */
  public static final Function<Object, Span> defaultLexicoder = object -> {
    if (object == null) {
      return SPAN_EMPTY;
    }
    if (object instanceof String) {
      return new Span((String) object, 0, ((String) object).length());
    }
    if (object instanceof Boolean) {
      String str = Boolean.toString((Boolean) object);
      return new Span(str, 0, str.length());
    }
    if (object instanceof BigInteger) {
      String str = BigDecimalCodec.encode(new BigDecimal((BigInteger) object));
      return new Span(str, 0, str.length());
    }
    if (object instanceof BigDecimal) {
      String str = BigDecimalCodec.encode((BigDecimal) object);
      return new Span(str, 0, str.length());
    }
    if (object instanceof Integer) {
      String str = BigDecimalCodec.encode(BigDecimal.valueOf((Integer) object));
      return new Span(str, 0, str.length());
    }
    if (object instanceof Long) {
      String str = BigDecimalCodec.encode(BigDecimal.valueOf((Long) object));
      return new Span(str, 0, str.length());
    }
    if (object instanceof Double) {
      Double d = (Double) object;
      if (Double.isNaN(d)) {
        return new Span("NaN", 0, 3);
      }
      if (Double.isInfinite(d)) {
        return new Span("inf", 0, 3);
      }
      String str = BigDecimalCodec.encode(BigDecimal.valueOf(d));
      return new Span(str, 0, str.length());
    }
    if (object instanceof Float) {
      Float f = (Float) object;
      if (Float.isNaN(f)) {
        return new Span("NaN", 0, 3);
      }
      if (Float.isInfinite(f)) {
        return new Span("inf", 0, 3);
      }
      String str = BigDecimalCodec.encode(BigDecimal.valueOf(f));
      return new Span(str, 0, str.length());
    }
    if (object instanceof Date) {
      String str = DateTimeFormatter.ISO_INSTANT.format(((Date) object).toInstant());
      return new Span(str, 0, str.length());
    }
    return SPAN_EMPTY;
  };

  private static final Pattern splitOnPunct_ = Pattern.compile("[^\r\n\t\\p{P}\\p{Zs}\\|\\^<>+=~]+",
      Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

  /**
   * A naive tokenizer that removes diacritics and split on punctuation marks.
   */
  public static final Function<String, SpanSequence> defaultTokenizer = text -> {
    if (text == null || text.isEmpty()) {
      return SPAN_SEQUENCE_EMPTY;
    }

    SpanSequence spanSequence = new SpanSequence();
    String newText =
        StringIterator.removeDiacriticalMarks(StringIterator.normalize(text)).toLowerCase();
    Matcher matcher = splitOnPunct_.matcher(newText);

    while (matcher.find()) {

      int begin = matcher.start();
      int end = matcher.end();

      spanSequence.add(new Span(newText, begin, end));
    }
    return spanSequence;
  };
}

