package com.computablefacts.nona.functions.stringoperators;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;
import com.google.common.collect.Lists;

public class MatchDictionaryTest {

  @Test
  public void testExactlyOneMatch() throws IOException {

    // Create dictionary
    List<String> keywords = Lists.newArrayList("sugar");
    Path file = Files.createTempFile("dico-", ".txt");
    Files.write(file, keywords);

    // Execute function
    Map<String, Function> functions = new HashMap<>();
    functions.put("DICO", new MatchDictionary());

    Function fn = new Function("DICO(" + Function.wrap(file.toString()) + ", "
        + Function.wrap("sugarcane sugar canesugar") + ")");
    SpanSequence spans = (SpanSequence) fn.evaluate(functions).value();

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("sugar", spans.span(0).text());
    Assert.assertEquals(10, spans.span(0).begin());
    Assert.assertEquals(15, spans.span(0).end());
  }

  @Test
  public void testMoreThanOneMatch() throws IOException {

    // Create dictionary
    List<String> keywords = Lists.newArrayList("great question", "forty-two", "deep thought");
    Path file = Files.createTempFile("dico-", ".txt");
    Files.write(file, keywords);

    // Execute function
    Map<String, Function> functions = new HashMap<>();
    functions.put("DICO", new MatchDictionary());

    Function fn = new Function("DICO(" + Function.wrap(file.toString()) + ", " + Function.wrap(
        "The Answer to the Great Question... Of Life,\nthe Universe and Everything... Is... Forty-two,' said\nDeep Thought, with infinite majesty and calm.")
        + ")");
    SpanSequence spans = (SpanSequence) fn.evaluate(functions).value();

    Assert.assertEquals(3, spans.size());
    Assert.assertTrue(
        spans.stream().map(Span::text).collect(Collectors.toSet()).contains("Great Question"));
    Assert.assertTrue(
        spans.stream().map(Span::text).collect(Collectors.toSet()).contains("Forty-two"));
    Assert.assertTrue(
        spans.stream().map(Span::text).collect(Collectors.toSet()).contains("Deep Thought"));
  }
}
