package com.computablefacts.nona.functions.patternoperators;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;
import com.google.common.collect.Sets;

public class ExtractOnionTest {

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

    Map<String, Function> functions = new HashMap<>();
    functions.put("EONION", new ExtractOnion());

    Function fn = new Function(
        "EONION(" + Function.wrap("http://torwikignoueupfm.onion/index.php?title=Main_Page") + ")");
    List<Span> spans = ((SpanSequence) fn.evaluate(functions).value()).sequence();
    Span span = spans.get(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("http://torwikignoueupfm.onion/index.php?title=Main_Page", span.text());
    Assert.assertEquals("http", span.getFeature("PROTOCOL"));
    Assert.assertEquals("torwikignoueupfm.onion", span.getFeature("HOSTNAME"));
    Assert.assertEquals("", span.getFeature("PORT"));
    Assert.assertEquals("/index.php?title=Main_Page", span.getFeature("PATH"));
  }

  @Test
  public void testValidOnions() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("EONION", new ExtractOnion());

    for (String onion : VALID_URLS_THAT_MATCH) {

      Function fn = new Function("EONION(" + Function.wrap(onion) + ")");
      List<Span> spans = ((SpanSequence) fn.evaluate(functions).value()).sequence();
      Span span = spans.get(0);

      Assert.assertEquals(1, spans.size());
      Assert.assertEquals(onion.toLowerCase(), span.text().toLowerCase());
    }
  }

  @Test
  public void testInvalidOnions() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("EONION", new ExtractOnion());

    for (String onion : INVALID_URLS) {

      Function fn = new Function("EONION(" + Function.wrap(onion) + ")");
      List<Span> spans = ((SpanSequence) fn.evaluate(functions).value()).sequence();

      Assert.assertEquals(0, spans.size());
    }
  }
}
