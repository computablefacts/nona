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

public class ExtractUrlTest {

  private static final Set<String> VALID_URLS_THAT_MATCH =
      Sets.newHashSet("http://foo.com/blah_blah", "http://foo.com/blah_blah/",
          "http://foo.com/blah_blah_(wikipedia)", "http://foo.com/blah_blah_(wikipedia)_(again)",
          "http://www.example.com/wpstyle/?p=364", "http://userid:password@example.com:8080",
          "http://userid:password@example.com:8080/", "http://userid@example.com",
          "http://userid@example.com/", "http://userid@example.com:8080",
          "http://userid@example.com:8080/", "http://userid:password@example.com",
          "http://userid:password@example.com/", "http://foo.com/blah_(wikipedia)#cite-1",
          "http://foo.com/blah_(wikipedia)_blah#cite-1", "http://foo.com/unicode_(✪)_in_parens",
          "http://foo.com/(something)?after=parens", "http://j.mp", "ftp://foo.bar/baz",
          "http://foo.bar/?q=Test%20URL-encoded%20stuff", "http://1337.net", "http://a.b-c.de");

  // TODO : fix regex
  private static final Set<String> VALID_URLS_THAT_SHOULD_MATCH =
      Sets.newHashSet("https://www.example.com/foo/?bar=baz&inga;=42&quux;",
          "http://code.google.com/events/#&product;=browser", "http://⌘.ws/", "http://مثال.إختبار",
          "http://➡.ws/䨹", "http://उदाहरण.परीक्षा", "http://⌘.ws", "http://142.42.1.1/",
          "http://☺.damowmow.com/", "http://223.255.255.254",
          "http://-.~_!$&'()*+,;=:%40:80%2f::::::@example.com", "http://例子.测试",
          "http://142.42.1.1:8080/", "http://✪df.ws/123");

  private static final Set<String> INVALID_URLS = Sets.newHashSet("http://", "http://.",
      "http://..", "http://../", "http://?", "http://??", "http://??/", "http://#", "http://##",
      "http://##/", "//", "//a", "///a", "///", "http:///a", "foo.com", "rdar://1234", "h://test",
      "http:// shouldfail.com", ":// should fail", "http://-error-.invalid/", "http://a.b--c.de/",
      "http://-a.b.co", "http://a.b-.co", "http://0.0.0.0", "http://10.1.1.0", "http://10.1.1.255",
      "http://224.1.1.1", "http://1.1.1.1.1", "http://123.123.123", "http://3628126748",
      "http://.www.foo.bar/", "http://.www.foo.bar./", "http://10.1.1.1", "http://10.1.1.254");

  // TODO : fix regex
  private static final Set<String> INVALID_URLS_THAT_SHOULD_NOT_MATCH =
      Sets.newHashSet("http://foo.bar?q=Spaces should be encoded",
          "http://foo.bar/foo(bar)baz quux", "ftps://foo.bar/", "http://www.foo.bar./");

  @Test
  public void testValidUrls() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("EURL", new ExtractUrl());

    for (String url : VALID_URLS_THAT_MATCH) {

      Function fn = new Function("EURL(" + Function.wrap(url) + ")");
      List<Span> spans = ((SpanSequence) fn.evaluate(functions).value()).sequence();
      Span span = spans.get(0);

      Assert.assertEquals(1, spans.size());
      Assert.assertEquals(url.toLowerCase(), span.text().toLowerCase());
    }
  }

  @Test
  public void testInvalidUrls() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("EURL", new ExtractUrl());

    for (String url : INVALID_URLS) {

      Function fn = new Function("EURL(" + Function.wrap(url) + ")");
      List<Span> spans = ((SpanSequence) fn.evaluate(functions).value()).sequence();

      Assert.assertEquals(0, spans.size());
    }
  }
}
