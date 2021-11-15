package com.computablefacts.nona.functions.patternoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.asterix.Span;
import com.computablefacts.asterix.SpanSequence;
import com.computablefacts.nona.Function;

public class JsObjectsPropertiesMethodsTest {

  @Test
  public void testJsObjectsPropertiesAndMethods() {

    Function fn = new Function("MATCH_JS_OBJECTS_PROPERTIES_AND_METHODS("
        + Function.wrap("Employe.prototype = new Personne();") + ")");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();
    Span spanPrototype = spans.span(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertTrue(spanPrototype.hasTag("JS_OBJECTS_PROPERTIES_AND_METHODS"));
    Assert.assertEquals("prototype", spanPrototype.text());
  }
}
