package com.computablefacts.nona.functions.stringoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class MatchFuzzyTest {

  @Test
  public void testMatchFuzzyWithSimpleRatio1() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("MATCH_FUZZY", new MatchFuzzy());

    Function fn =
        new Function("MATCH_FUZZY(simple, 72, \"mysmilarstring\", \"myawfullysimilarstirng\")");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(functions));
  }

  @Test
  public void testMatchFuzzyWithSimpleRatio2() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("MATCH_FUZZY", new MatchFuzzy());

    Function fn = new Function("MATCH_FUZZY(simple, 97, \"mysmilarstring\", \"mysimilarstring\")");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(functions));
  }

  @Test
  public void testMatchFuzzyWithTokenSortRatio() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("MATCH_FUZZY", new MatchFuzzy());

    Function fn = new Function(
        "MATCH_FUZZY(token_sort_simple, 100, \"order words out of\", \"  words out of order\")");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(functions));
  }

  @Test
  public void testMatchFuzzyWithTokenSortPartialRatio() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("MATCH_FUZZY", new MatchFuzzy());

    Function fn = new Function(
        "MATCH_FUZZY(token_sort_partial, 100, \"order words out of\", \"  words out of order\")");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(functions));
  }

  @Test
  public void testMatchFuzzyWithTokenSetRatio() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("MATCH_FUZZY", new MatchFuzzy());

    Function fn = new Function(
        "MATCH_FUZZY(token_set_simple, 100, \"fuzzy was a bear\", \"fuzzy fuzzy fuzzy bear\")");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(functions));
  }

  @Test
  public void testMatchFuzzyWithTokenSetPartialRatio() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("MATCH_FUZZY", new MatchFuzzy());

    Function fn = new Function(
        "MATCH_FUZZY(token_set_partial, 100, \"fuzzy was a bear\", \"fuzzy fuzzy fuzzy bear\")");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(functions));
  }

  @Test
  public void testMatchFuzzyWithWeightedRatio() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("MATCH_FUZZY", new MatchFuzzy());

    Function fn = new Function(
        "MATCH_FUZZY(weighted, 97, \"The quick brown fox jimps ofver the small lazy dog\", \"the quick brown fox jumps over the small lazy dog\")");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(functions));
  }
}
