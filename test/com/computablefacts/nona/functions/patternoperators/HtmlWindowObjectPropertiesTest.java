package com.computablefacts.nona.functions.patternoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;

public class HtmlWindowObjectPropertiesTest {

  @Test
  public void testHtmlWindowObjectProperties() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("HTML_WINDOW_OBJECT_PROPERTIES", new HtmlWindowObjectProperties());

    Function fn = new Function(
        "HTML_WINDOW_OBJECT_PROPERTIES(" + Function.wrap("window.alert(\"Error !\")") + ")");
    SpanSequence spans = (SpanSequence) fn.evaluate(functions).value();
    Span spanWindow = spans.span(0);
    Span spanAlert = spans.span(1);

    Assert.assertEquals(2, spans.size());
    Assert.assertTrue(spanWindow.hasTag("HTML_WINDOW_OBJECT_PROPERTIES"));
    Assert.assertTrue(spanAlert.hasTag("HTML_WINDOW_OBJECT_PROPERTIES"));
    Assert.assertEquals("window", spanWindow.text());
    Assert.assertEquals("alert", spanAlert.text());
  }
}
