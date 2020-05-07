package com.computablefacts.nona.functions.patternoperators;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;

public class BicTest {

  @Test
  public void testAleInternational() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("BIC", new Bic());

    Function fn = new Function("BIC(AALE FR 22)");
    List<Span> spans = ((SpanSequence) fn.evaluate(functions).value()).sequence();
    Span span = spans.get(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("AALE FR 22", span.text());
    Assert.assertEquals(true, span.hasTag("BIC"));
    Assert.assertEquals("22", span.getFeature("LOCATION_CODE"));
    Assert.assertEquals("France", span.getFeature("COUNTRY_NAME"));
    Assert.assertEquals("", span.getFeature("BRANCH_CODE"));
    Assert.assertEquals("AALE", span.getFeature("INSTITUTION_CODE"));
    Assert.assertEquals("false", span.getFeature("IS_TEST_BIC"));
    Assert.assertEquals("FR", span.getFeature("COUNTRY_CODE"));
    Assert.assertEquals("COLOMBES", span.getFeature("CITY"));
    Assert.assertEquals("ALE INTERNATIONAL", span.getFeature("BANK_NAME"));
  }

  @Test
  public void testAgenceFranceTresor() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("BIC", new Bic());

    Function fn = new Function("BIC(AFTR-FR-PP-CDP)");
    List<Span> spans = ((SpanSequence) fn.evaluate(functions).value()).sequence();
    Span span = spans.get(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("AFTR-FR-PP-CDP", span.text());
    Assert.assertEquals(true, span.hasTag("BIC"));
    Assert.assertEquals("PP", span.getFeature("LOCATION_CODE"));
    Assert.assertEquals("France", span.getFeature("COUNTRY_NAME"));
    Assert.assertEquals("CDP", span.getFeature("BRANCH_CODE"));
    Assert.assertEquals("AFTR", span.getFeature("INSTITUTION_CODE"));
    Assert.assertEquals("false", span.getFeature("IS_TEST_BIC"));
    Assert.assertEquals("FR", span.getFeature("COUNTRY_CODE"));
    Assert.assertEquals("PARIS", span.getFeature("CITY"));
    Assert.assertEquals("AGENCE FRANCE TRESOR (AFT)", span.getFeature("BANK_NAME"));
  }
}
