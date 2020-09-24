package com.computablefacts.nona.functions.patternoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;

public class HtmlWindowObjectPropertiesTest {

  @Test
  public void testHtmlWindowObjectProperties() {

    Function fn = new Function(
        "MATCH_HTML_WINDOW_OBJECT_PROPERTIES(" + Function.wrap("window.alert(\"Error !\")") + ")");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();
    Span spanWindow = spans.span(0);
    Span spanAlert = spans.span(1);

    Assert.assertEquals(2, spans.size());
    Assert.assertTrue(spanWindow.hasTag("HTML_WINDOW_OBJECT_PROPERTIES"));
    Assert.assertTrue(spanAlert.hasTag("HTML_WINDOW_OBJECT_PROPERTIES"));
    Assert.assertEquals("window", spanWindow.text());
    Assert.assertEquals("alert", spanAlert.text());
  }
}
