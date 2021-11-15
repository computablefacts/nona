package com.computablefacts.nona.functions.patternoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.asterix.Span;
import com.computablefacts.asterix.SpanSequence;
import com.computablefacts.nona.Function;

public class CssPropertiesTest {

  @Test
  public void testCssProperties() {

    Function fn = new Function(
        "MATCH_CSS_PROPERTIES(" + Function.wrap("p.padding { padding-left: 2cm; }") + ")");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();
    Span spanPadding = spans.span(0);
    Span spanPaddingLeft = spans.span(1);

    Assert.assertEquals(2, spans.size());
    Assert.assertTrue(spanPadding.hasTag("CSS_PROPERTIES"));
    Assert.assertTrue(spanPaddingLeft.hasTag("CSS_PROPERTIES"));
    Assert.assertEquals("padding", spanPadding.text());
    Assert.assertEquals("padding-left", spanPaddingLeft.text());
  }
}
