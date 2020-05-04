package com.computablefacts.nona.functions.patternoperators;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;

public class ExtractBicTest {

  /**
   * Extracted from
   * https://github.com/franckverrot/codes-bic-france/blob/master/codes-bic-france.csv
   */
  @Test
  public void testAleInternational() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("EBIC", new ExtractBic());

    Function fn = new Function("EBIC(AALE FR 22)");
    List<Span> spans = ((SpanSequence) fn.evaluate(functions).value()).sequence();

    Assert.assertEquals("AALEFR22", spans.get(0).text());
  }

  /**
   * Extracted from
   * https://github.com/franckverrot/codes-bic-france/blob/master/codes-bic-france.csv
   */
  @Test
  public void testAgenceFranceTresor() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("EBIC", new ExtractBic());

    Function fn = new Function("EBIC(AFTR-FR-PP-CDP)");
    List<Span> spans = ((SpanSequence) fn.evaluate(functions).value()).sequence();

    Assert.assertEquals("AFTRFRPPCDP", spans.get(0).text());
  }

  @Test
  public void testExtractFromNoisyText() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("EBIC", new ExtractBic());

    Function fn = new Function(
        "EBIC(Beneficiary Bank: BARCLAYS\nBeneficiary IBAN: GB 94 BARC10201530093459\nSwift Code: BUKBGB22)");
    List<Span> spans = ((SpanSequence) fn.evaluate(functions).value()).sequence();

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("BUKBGB22", spans.get(0).text());
  }
}
