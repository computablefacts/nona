package com.computablefacts.nona.functions.patternoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;

public class Base64Test {

  @Test
  public void testValidBase64String() {

    String b64 =
        "uO3lZkI9fkWmzqM3QQuBCB6XhargnehMptMRKoZQxmNDSlMYi8fBv1M7ATIpdFvQaa/MyzTbYhmeLgrCxqMIlmLDLgHG3fkVe/0Vr7eulqemWjZEJABbpLoIHjtduuzioHzyJANZQZXL9MSvADGZk3RDX6cuE8rvV5x+il1GR5PGFNq4NdFRCYm4PxBcM1XKl2b0CkvIPAY/jJoYM2hWDv9OPP5LKhzFKyNdWT6dVU+wqDInfEHqX7y2DAp+i2bhu0ZJItJmZa6tSe/XUZ/pGt/x5vy6ffXm850a3Gg6o0CwuY0tzcz+6nY0rrswbju5l2YgWb7b4Guu87gz+GLWzw==";

    Function fn = new Function("MATCH_BASE64(" + Function.wrap(b64) + ")");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();
    Span span = spans.span(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals(b64, span.text());
  }

  @Test
  public void testInvalidBase64String() {

    String b64 = "====";

    Function fn = new Function("MATCH_BASE64(" + Function.wrap(b64) + ")");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();

    Assert.assertEquals(0, spans.size());
  }
}
