package com.computablefacts.nona.functions.patternoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;

public class JsObjectsPropertiesMethodsTest {

  @Test
  public void testJsObjectsPropertiesAndMethods() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("JS_OBJECTS_PROPERTIES_AND_METHODS", new JsObjectsPropertiesAndMethods());

    Function fn = new Function("JS_OBJECTS_PROPERTIES_AND_METHODS("
        + Function.wrap("Employe.prototype = new Personne();") + ")");
    SpanSequence spans = (SpanSequence) fn.evaluate(functions).value();
    Span spanPrototype = spans.span(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertTrue(spanPrototype.hasTag("JS_OBJECTS_PROPERTIES_AND_METHODS"));
    Assert.assertEquals("prototype", spanPrototype.text());
  }
}
