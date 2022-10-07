package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

public class MatchFuzzyTest {

  @Test
  public void testMatchFuzzyWithPartialRatio() {

    Function fn = new Function("MATCH_FUZZY(partial, 72, \"mysmilarstring\", \"myawfullysimilarstirng\")");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testMatchFuzzyWithSimpleRatio1() {

    Function fn = new Function("MATCH_FUZZY(simple, 72, \"mysmilarstring\", \"myawfullysimilarstirng\")");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testMatchFuzzyWithSimpleRatio2() {

    Function fn = new Function("MATCH_FUZZY(simple, 97, \"mysmilarstring\", \"mysimilarstring\")");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testMatchFuzzyWithTokenSortRatio() {

    Function fn = new Function("MATCH_FUZZY(token_sort_simple, 100, \"order words out of\", \"  words out of order\")");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testMatchFuzzyWithTokenSortPartialRatio() {

    Function fn = new Function(
        "MATCH_FUZZY(token_sort_partial, 100, \"order words out of\", \"  words out of order\")");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testMatchFuzzyWithTokenSetRatio() {

    Function fn = new Function("MATCH_FUZZY(token_set_simple, 100, \"fuzzy was a bear\", \"fuzzy fuzzy fuzzy bear\")");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testMatchFuzzyWithTokenSetPartialRatio() {

    Function fn = new Function("MATCH_FUZZY(token_set_partial, 100, \"fuzzy was a bear\", \"fuzzy fuzzy fuzzy bear\")");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testMatchFuzzyWithWeightedRatio() {

    Function fn = new Function(
        "MATCH_FUZZY(weighted, 97, \"The quick brown fox jimps ofver the small lazy dog\", \"the quick brown fox jumps over the small lazy dog\")");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }
}
