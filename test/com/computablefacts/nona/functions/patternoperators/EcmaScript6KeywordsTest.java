package com.computablefacts.nona.functions.patternoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.asterix.Span;
import com.computablefacts.asterix.SpanSequence;
import com.computablefacts.nona.Function;

public class EcmaScript6KeywordsTest {

  @Test
  public void testEcmaScript6Keywords() {

    Function fn = new Function("MATCH_ECMASCRIPT6_KEYWORDS(" + Function.wrap("const x = 2;") + ")");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();
    Span spanConst = spans.span(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertTrue(spanConst.hasTag("ECMASCRIPT6_KEYWORDS"));
    Assert.assertEquals("const", spanConst.text());
  }
}
