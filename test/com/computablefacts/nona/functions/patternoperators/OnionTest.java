package com.computablefacts.nona.functions.patternoperators;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.asterix.Span;
import com.computablefacts.asterix.SpanSequence;
import com.computablefacts.nona.Function;
import com.google.common.collect.Sets;

public class OnionTest {

  private static final Set<String> VALID_URLS_THAT_MATCH = Sets.newHashSet(
      "http://wikitjerrta4qgz4.onion/", "http://zqktlwi4fecvo6ri.onion/wiki/index.php/Main_Page",
      "http://dirnxxdraygbifgc.onion/", "http://jh32yv5zgayyyts3.onion/",
      "http://ffi5v46ttwgx3fby.onion/", "http://torlinkbgs6aabns.onion/",
      "http://underdj5ziov3ic7.onion/", "http://wiki5kauuihowqi5.onion/",
      "http://torwikignoueupfm.onion/index.php?title=Main_Page");

  // TODO : fix regex
  private static final Set<String> VALID_URLS_THAT_SHOULD_MATCH = Sets.newHashSet();

  private static final Set<String> INVALID_URLS = Sets.newHashSet();

  // TODO : fix regex
  private static final Set<String> INVALID_URLS_THAT_SHOULD_NOT_MATCH = Sets.newHashSet();

  @Test
  public void testOnion() {

    Function fn = new Function("MATCH_ONION("
        + Function.wrap("http://torwikignoueupfm.onion/index.php?title=Main_Page") + ")");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();
    Span span = spans.span(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("http://torwikignoueupfm.onion/index.php?title=Main_Page", span.text());
    Assert.assertTrue(span.hasTag("ONION"));
    Assert.assertEquals("http", span.getFeature("PROTOCOL"));
    Assert.assertEquals("torwikignoueupfm.onion", span.getFeature("HOSTNAME"));
    Assert.assertEquals("", span.getFeature("PORT"));
    Assert.assertEquals("/index.php?title=Main_Page", span.getFeature("PATH"));
  }

  @Test
  public void testValidOnions() {

    for (String onion : VALID_URLS_THAT_MATCH) {

      Function fn = new Function("MATCH_ONION(" + Function.wrap(onion) + ")");
      SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();
      Span span = spans.span(0);

      Assert.assertEquals(1, spans.size());
      Assert.assertEquals(onion.toLowerCase(), span.text().toLowerCase());
      Assert.assertTrue(span.hasTag("ONION"));
    }
  }

  @Test
  public void testInvalidOnions() {

    for (String onion : INVALID_URLS) {

      Function fn = new Function("MATCH_ONION(" + Function.wrap(onion) + ")");
      SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();

      Assert.assertEquals(0, spans.size());
    }
  }
}
