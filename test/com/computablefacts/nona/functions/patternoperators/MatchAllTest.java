package com.computablefacts.nona.functions.patternoperators;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;
import com.google.common.collect.Lists;

public class MatchAllTest {

  @Test
  public void testMatchAll1() throws IOException {

    List<String> keywords = Lists.newArrayList("UBS Group AG", "Goldman Sachs", "JPMorgan Chase");
    Path file = Files.createTempFile("dico-", ".txt");
    Files.write(file, keywords);

    String text =
        "As of 2017, UBS Group AG is the 11th largest bank in Europe with a market capitalization of $64.5 billion.\n"
            + "It has over CHF 3.2 trillion in assets under management (AUM), approximately CHF 2.8 trillion of which are invested assets.\n"
            + "In June 2017, its return on invested capital was 11.1%, followed by Goldman Sachs' 9.5%, and JPMorgan Chase's 9.2%.";

    Map<String, Function> functions = new HashMap<>();
    functions.put("MATCH_ALL", new MatchAll());

    Function fn = new Function(
        "MATCH_ALL(" + Function.wrap(text) + ", " + Function.wrap(file.toString()) + ")");
    List<Span> spans = ((SpanSequence) fn.evaluate(functions).value()).sequence();

    Assert.assertEquals(11, spans.size());

    Assert.assertEquals("2017", spans.get(0).text());
    Assert.assertTrue(spans.get(0).hasTag("NUMBER"));

    Assert.assertEquals("UBS Group AG", spans.get(1).text());
    Assert.assertTrue(spans.get(1).hasTag("COMPANY_NAME"));

    Assert.assertEquals("$64.5", spans.get(2).text());
    Assert.assertTrue(spans.get(2).hasTag("MONETARY_AMOUNT"));

    Assert.assertEquals("3.2", spans.get(3).text());
    Assert.assertTrue(spans.get(3).hasTag("FINANCIAL_NUMBER"));

    Assert.assertEquals("2.8", spans.get(4).text());
    Assert.assertTrue(spans.get(4).hasTag("FINANCIAL_NUMBER"));

    Assert.assertEquals("2017", spans.get(5).text());
    Assert.assertTrue(spans.get(5).hasTag("NUMBER"));

    Assert.assertEquals("11.1%", spans.get(6).text());
    Assert.assertTrue(spans.get(6).hasTag("PERCENT"));

    Assert.assertEquals("Goldman Sachs", spans.get(7).text());

    Assert.assertEquals("9.5%", spans.get(8).text());
    Assert.assertTrue(spans.get(8).hasTag("PERCENT"));

    Assert.assertEquals("JPMorgan Chase", spans.get(9).text());

    Assert.assertEquals("9.2%", spans.get(10).text());
    Assert.assertTrue(spans.get(10).hasTag("PERCENT"));
  }
}
