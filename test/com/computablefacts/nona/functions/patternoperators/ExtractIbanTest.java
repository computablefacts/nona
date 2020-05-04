package com.computablefacts.nona.functions.patternoperators;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;
import com.google.common.base.Splitter;

public class ExtractIbanTest {

  /**
   * Extracted from https://fr.iban.com/testibans
   */
  @Test
  public void testMatchExact1() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("EIBAN", new ExtractIban());

    Function fn = new Function("EIBAN(GB33BUKB20201555555555)");
    List<Span> spans = ((SpanSequence) fn.evaluate(functions).value()).sequence();

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("GB33BUKB20201555555555", spans.get(0).text());
  }

  /**
   * Extracted from https://fr.iban.com/testibans
   */
  @Test
  public void testMatchExact2() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("EIBAN", new ExtractIban());

    Function fn = new Function("EIBAN(GB94BARC10201530093459)");
    List<Span> spans = ((SpanSequence) fn.evaluate(functions).value()).sequence();

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("GB94BARC10201530093459", spans.get(0).text());
  }

  /**
   * Extracted from https://fr.iban.com/testibans
   */
  @Test
  public void testNoMatch1() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("EIBAN", new ExtractIban());

    Function fn = new Function("EIBAN(GB94BARC20201530093459)");
    List<Span> spans = ((SpanSequence) fn.evaluate(functions).value()).sequence();

    Assert.assertEquals(0, spans.size());
  }

  /**
   * Extracted from https://fr.iban.com/testibans
   */
  @Test
  public void testNoMatch2() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("EIBAN", new ExtractIban());

    Function fn = new Function("EIBAN(GB96BARC202015300934591)");
    List<Span> spans = ((SpanSequence) fn.evaluate(functions).value()).sequence();

    Assert.assertEquals(0, spans.size());
  }

  /**
   * Extracted from https://bank-code.net/iban/country-list
   */
  @Test
  public void testDictionary() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("EIBAN", new ExtractIban());

    for (String row : ExtractIban.DICTIONARY) {

      List<String> cols = Splitter.on(',').splitToList(row);
      Function fn = new Function("EIBAN(" + cols.get(4) + ")");
      List<Span> spans = ((SpanSequence) fn.evaluate(functions).value()).sequence();

      Assert.assertEquals(1, spans.size());
      Assert.assertEquals(cols.get(4).replaceAll("[^A-Z0-9]", ""), spans.get(0).text());
    }
  }

  @Test
  public void testExtractFromNoisyText() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("EIBAN", new ExtractIban());

    Function fn = new Function(
        "EIBAN(Beneficiary Bank: BARCLAYS\nBeneficiary IBAN: GB 94 BARC10201530093459\nSwift Code: BUKBGB22)");
    List<Span> spans = ((SpanSequence) fn.evaluate(functions).value()).sequence();

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("GB94BARC10201530093459", spans.get(0).text());
  }
}
