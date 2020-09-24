package com.computablefacts.nona.functions.patternoperators;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.SpanSequence;

public class IbanTest {

  /**
   * Extracted from https://fr.iban.com/testibans
   */
  @Test
  public void testMatchExact1() {

    Function fn = new Function("MATCH_IBAN(GB33BUKB20201555555555)");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("GB33BUKB20201555555555", spans.span(0).text());
    Assert.assertTrue(spans.span(0).hasTag("IBAN"));
  }

  /**
   * Extracted from https://fr.iban.com/testibans
   */
  @Test
  public void testMatchExact2() {

    Function fn = new Function("MATCH_IBAN(GB94BARC10201530093459)");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("GB94BARC10201530093459", spans.span(0).text());
    Assert.assertTrue(spans.span(0).hasTag("IBAN"));
  }

  /**
   * Extracted from https://fr.iban.com/testibans
   */
  @Test
  public void testNoMatch1() {

    Function fn = new Function("MATCH_IBAN(GB94BARC20201530093459)");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();

    Assert.assertEquals(0, spans.size());
  }

  /**
   * Extracted from https://fr.iban.com/testibans
   */
  @Test
  public void testNoMatch2() {

    Function fn = new Function("MATCH_IBAN(GB96BARC202015300934591)");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();

    Assert.assertEquals(0, spans.size());
  }

  @Test
  public void testIbansDictionary() {

    Map<String, com.computablefacts.nona.dictionaries.Iban> ibans =
        com.computablefacts.nona.dictionaries.Iban.load();

    for (com.computablefacts.nona.dictionaries.Iban iban : ibans.values()) {

      Function fn = new Function("MATCH_IBAN(" + iban.ibanExample() + ")");
      SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();

      Assert.assertEquals(1, spans.size());
      Assert.assertEquals(iban.ibanExample().replaceAll("[^A-Z0-9]", ""), spans.span(0).text());
      Assert.assertTrue(spans.span(0).hasTag("IBAN"));
    }
  }

  @Test
  public void testExtractIbanFromNoisyText() {

    Function fn = new Function(
        "MATCH_IBAN(Beneficiary Bank: BARCLAYS\nBeneficiary IBAN: GB 94 BARC10201530093459\nSwift Code: BUKBGB22)");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("GB94BARC10201530093459", spans.span(0).text());
    Assert.assertTrue(spans.span(0).hasTag("IBAN"));
  }

  @Test
  public void testMismatchBetweenExtractedIbanLengthAndTheoreticLength() {

    Function fn = new Function("MATCH_IBAN(SE35 5000 0000 0549 1000)");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();

    Assert.assertEquals(0, spans.size());
  }
}
