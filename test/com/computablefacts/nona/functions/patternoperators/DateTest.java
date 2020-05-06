package com.computablefacts.nona.functions.patternoperators;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;

public class DateTest {

  @Test
  public void testDate() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("DATE", new Date());

    Function fn = new Function("DATE(16/07/1997)");
    List<Span> spans = ((SpanSequence) fn.evaluate(functions).value()).sequence();
    Span span = spans.get(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("16/07/1997", span.text());
  }
}
