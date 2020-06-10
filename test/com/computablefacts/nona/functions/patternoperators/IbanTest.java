package com.computablefacts.nona.functions.patternoperators;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;

public class IbanTest {

  /**
   * Extracted from https://fr.iban.com/testibans
   */
  @Test
  public void testMatchExact1() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("IBAN", new Iban());

    Function fn = new Function("IBAN(GB33BUKB20201555555555)");
    List<Span> spans = ((SpanSequence) fn.evaluate(functions).value()).sequence();

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("GB33BUKB20201555555555", spans.get(0).text());
    Assert.assertTrue(spans.get(0).hasTag("IBAN"));
  }

  /**
   * Extracted from https://fr.iban.com/testibans
   */
  @Test
  public void testMatchExact2() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("IBAN", new Iban());

    Function fn = new Function("IBAN(GB94BARC10201530093459)");
    List<Span> spans = ((SpanSequence) fn.evaluate(functions).value()).sequence();

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("GB94BARC10201530093459", spans.get(0).text());
    Assert.assertTrue(spans.get(0).hasTag("IBAN"));
  }

  /**
   * Extracted from https://fr.iban.com/testibans
   */
  @Test
  public void testNoMatch1() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("IBAN", new Iban());

    Function fn = new Function("IBAN(GB94BARC20201530093459)");
    List<Span> spans = ((SpanSequence) fn.evaluate(functions).value()).sequence();

    Assert.assertEquals(0, spans.size());
  }

  /**
   * Extracted from https://fr.iban.com/testibans
   */
  @Test
  public void testNoMatch2() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("IBAN", new Iban());

    Function fn = new Function("IBAN(GB96BARC202015300934591)");
    List<Span> spans = ((SpanSequence) fn.evaluate(functions).value()).sequence();

    Assert.assertEquals(0, spans.size());
  }

  @Test
  public void testIbansDictionary() {

    Map<String, com.computablefacts.nona.dictionaries.Iban> ibans =
        com.computablefacts.nona.dictionaries.Iban.load();
    Map<String, Function> functions = new HashMap<>();
    functions.put("IBAN", new Iban());

    for (com.computablefacts.nona.dictionaries.Iban iban : ibans.values()) {

      Function fn = new Function("IBAN(" + iban.ibanExample() + ")");
      List<Span> spans = ((SpanSequence) fn.evaluate(functions).value()).sequence();

      Assert.assertEquals(1, spans.size());
      Assert.assertEquals(iban.ibanExample().replaceAll("[^A-Z0-9]", ""), spans.get(0).text());
      Assert.assertTrue(spans.get(0).hasTag("IBAN"));
    }
  }

  @Test
  public void testExtractIbanFromNoisyText() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("IBAN", new Iban());

    Function fn = new Function(
        "IBAN(Beneficiary Bank: BARCLAYS\nBeneficiary IBAN: GB 94 BARC10201530093459\nSwift Code: BUKBGB22)");
    List<Span> spans = ((SpanSequence) fn.evaluate(functions).value()).sequence();

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("GB94BARC10201530093459", spans.get(0).text());
    Assert.assertTrue(spans.get(0).hasTag("IBAN"));
  }

  @Test
  public void testMismatchBetweenExtractedIbanLengthAndTheoreticLength() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("IBAN", new Iban());

    Function fn = new Function("IBAN(SE35 5000 0000 0549 1000)");
    List<Span> spans = ((SpanSequence) fn.evaluate(functions).value()).sequence();

    Assert.assertEquals(0, spans.size());
  }
}
