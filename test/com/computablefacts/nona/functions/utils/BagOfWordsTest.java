package com.computablefacts.nona.functions.utils;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

public class BagOfWordsTest {

  @Test
  public void testSimpleBagOfWordsEquals() {

    IBagOfWords bag1 = bagOfWords();
    IBagOfWords bag2 = bagOfWords();

    Assert.assertTrue(bag1.equals(bag2));
    Assert.assertTrue(bag2.equals(bag1));
  }

  @Test
  public void testSimpleBagOfWordsHashcode() {

    IBagOfWords bag1 = bagOfWords();
    IBagOfWords bag2 = bagOfWords();

    Assert.assertEquals(bag1.hashCode(), bag2.hashCode());
  }

  @Test
  public void testBagOfWords() {

    IBagOfWords bag = bagOfWords();

    Assert.assertEquals(words(), bag.bagOfWords());
  }

  @Test
  public void testFreezeBagOfWords() {

    IBagOfWords bag = bagOfWords();
    IBagOfWords copy = IBagOfWords.wrap(words());
    IBagOfWords frozen = bag.freezeBagOfWords();

    Assert.assertEquals(copy, frozen);
  }

  @Test
  public void testUniqueWords() {

    IBagOfWords bag = bagOfWords();

    Set<String> words = new HashSet<>();
    words.add("a");
    words.add("b");
    words.add("c");
    words.add("d");

    Assert.assertEquals(words, bag.uniqueWords());
  }

  @Test
  public void testNumberOfWords() {

    IBagOfWords bag = bagOfWords();

    Assert.assertEquals(7, bag.numberOfWords());
  }

  @Test
  public void testNumberOfDistinctBigrams() {

    IBagOfWords bag = bagOfWords();

    Assert.assertEquals(4, bag.numberOfDistinctWords());
  }

  @Test
  public void testFrequency() {

    IBagOfWords bag = bagOfWords();

    Assert.assertEquals(1, bag.frequency("a"));
    Assert.assertEquals(1, bag.frequency("b"));
    Assert.assertEquals(1, bag.frequency("c"));
    Assert.assertEquals(4, bag.frequency("d"));
  }

  @Test
  public void testNormalizedFrequency() {

    IBagOfWords bag = bagOfWords();

    Assert.assertEquals(1.0 / 7.0, bag.normalizedFrequency("a"), 0.00000001);
    Assert.assertEquals(1.0 / 7.0, bag.normalizedFrequency("b"), 0.00000001);
    Assert.assertEquals(1.0 / 7.0, bag.normalizedFrequency("c"), 0.00000001);
    Assert.assertEquals(4.0 / 7.0, bag.normalizedFrequency("d"), 0.00000001);
  }

  @Test
  public void testMergeWith() {

    IBagOfWords bag1 = bagOfWords();
    IBagOfWords bag2 = bagOfWords();

    Multiset<String> words = bag1.mergeWith(bag2);

    // Ensure bag1 has not been modified
    Assert.assertEquals(7, bag1.numberOfWords());
    Assert.assertEquals(1, bag1.frequency("a"));
    Assert.assertEquals(1, bag1.frequency("b"));
    Assert.assertEquals(1, bag1.frequency("c"));
    Assert.assertEquals(4, bag1.frequency("d"));

    // Ensure bag2 has not been modified
    Assert.assertEquals(7, bag2.numberOfWords());
    Assert.assertEquals(1, bag2.frequency("a"));
    Assert.assertEquals(1, bag2.frequency("b"));
    Assert.assertEquals(1, bag2.frequency("c"));
    Assert.assertEquals(4, bag2.frequency("d"));

    // Ensure merge is ok
    Assert.assertEquals(14, words.size());
    Assert.assertEquals(2, words.count("a"));
    Assert.assertEquals(2, words.count("b"));
    Assert.assertEquals(2, words.count("c"));
    Assert.assertEquals(8, words.count("d"));
  }

  private IBagOfWords bagOfWords() {
    return IBagOfWords.wrap(words());
  }

  private Multiset<String> words() {

    Multiset<String> bag = HashMultiset.create();

    bag.add("a");
    bag.add("b");
    bag.add("c");
    bag.add("d", 4);

    return bag;
  }
}
