package com.computablefacts.nona.helpers;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;

public class BagOfTextsTest {

  @Test
  public void testEqualsWithNull() {

    IBagOfTexts bag = bagOfTexts();

    Assert.assertFalse(bag.equals(null));
  }

  @Test
  public void testEqualsWithWrongObjectType() {

    IBagOfTexts bag = bagOfTexts();

    Assert.assertFalse(bag.equals("string"));
  }

  @Test
  public void testBagOfTextsEquals() {

    IBagOfTexts bag1 = bagOfTexts();
    IBagOfTexts bag2 = bagOfTexts();

    Assert.assertTrue(bag1.equals(bag2));
    Assert.assertTrue(bag2.equals(bag1));
  }

  @Test
  public void testBagOfTextsHashcode() {

    IBagOfTexts bag1 = bagOfTexts();
    IBagOfTexts bag2 = bagOfTexts();

    Assert.assertEquals(bag1.hashCode(), bag2.hashCode());
  }

  @Test
  public void testFrozenBagOfTextsEquals() {

    IBagOfTexts bag1 = frozenBagOfTexts();
    IBagOfTexts bag2 = frozenBagOfTexts();

    Assert.assertTrue(bag1.equals(bag2));
    Assert.assertTrue(bag2.equals(bag1));
  }

  @Test
  public void testFrozenBagOfTextsHashcode() {

    IBagOfTexts bag1 = frozenBagOfTexts();
    IBagOfTexts bag2 = frozenBagOfTexts();

    Assert.assertEquals(bag1.hashCode(), bag2.hashCode());
  }

  @Test
  public void testBagOfTexts() {

    IBagOfTexts bag1 = bagOfTexts();
    IBagOfTexts bag2 = frozenBagOfTexts();

    Assert.assertEquals(texts(), bag1.bagOfTexts());
    Assert.assertEquals(texts(), bag2.bagOfTexts());
  }

  @Test
  public void testBagOfWords() {

    IBagOfTexts bag1 = bagOfTexts();
    IBagOfTexts bag2 = frozenBagOfTexts();

    Assert.assertEquals(words(), bag1.bagOfWords());
    Assert.assertEquals(words(), bag2.bagOfWords());
  }

  @Test
  public void testBagOfBigrams() {

    IBagOfTexts bag1 = bagOfTexts();
    IBagOfTexts bag2 = frozenBagOfTexts();

    Assert.assertEquals(bigrams(), bag1.bagOfBigrams());
    Assert.assertEquals(bigrams(), bag2.bagOfBigrams());
  }

  @Test
  public void testFreezeBagOfTexts() {

    IBagOfTexts copy = IBagOfTexts.wrap(texts(), words(), bigrams());
    IBagOfTexts frozen = frozenBagOfTexts();

    Assert.assertEquals(copy, frozen);
  }

  @Test
  public void testText() {

    IBagOfTexts bag1 = bagOfTexts();
    IBagOfTexts bag2 = frozenBagOfTexts();

    Assert.assertEquals(textHello(), bag1.text("Hello Kevin!\nHello Joe!"));
    Assert.assertEquals(textGoodbye(), bag1.text("Goodbye Bill.\nGoodbye Joe."));

    Assert.assertEquals(textHello(), bag2.text("Hello Kevin!\nHello Joe!"));
    Assert.assertEquals(textGoodbye(), bag2.text("Goodbye Bill.\nGoodbye Joe."));
  }

  @Test
  public void testUniqueText() {

    IBagOfTexts bag1 = bagOfTexts();
    Set<Text> set1 = bag1.uniqueTexts();

    IBagOfTexts bag2 = frozenBagOfTexts();
    Set<Text> set2 = bag2.uniqueTexts();

    Assert.assertEquals(2, bag1.uniqueTexts().size());
    Assert.assertTrue(set1.contains(textHello()));
    Assert.assertTrue(set1.contains(textGoodbye()));

    Assert.assertEquals(2, bag2.uniqueTexts().size());
    Assert.assertTrue(set2.contains(textHello()));
    Assert.assertTrue(set2.contains(textGoodbye()));
  }

  @Test
  public void testNumberOfTexts() {

    IBagOfTexts bag1 = bagOfTexts();
    IBagOfTexts bag2 = frozenBagOfTexts();

    Assert.assertEquals(3, bag1.numberOfTexts());
    Assert.assertEquals(3, bag2.numberOfTexts());
  }

  @Test
  public void testNumberOfDistinctTexts() {

    IBagOfTexts bag1 = bagOfTexts();
    IBagOfTexts bag2 = frozenBagOfTexts();

    Assert.assertEquals(2, bag1.numberOfDistinctTexts());
    Assert.assertEquals(2, bag2.numberOfDistinctTexts());
  }

  @Test
  public void testAverageTextLength() {

    IBagOfTexts bag1 = bagOfTexts();
    IBagOfTexts bag2 = frozenBagOfTexts();

    Assert.assertEquals(4.0, bag1.averageTextLength(), 0.000001);
    Assert.assertEquals(4.0, bag2.averageTextLength(), 0.000001);
  }

  @Test
  public void testNumberOfDistinctTextsOccurrences1() {

    IBagOfTexts bag1 = bagOfTexts();
    IBagOfTexts bag2 = frozenBagOfTexts();

    Assert.assertEquals(1, bag1.numberOfDistinctTextsOccurrences("hello"));
    Assert.assertEquals(1, bag1.numberOfDistinctTextsOccurrences("kevin"));
    Assert.assertEquals(2, bag1.numberOfDistinctTextsOccurrences("joe"));
    Assert.assertEquals(1, bag1.numberOfDistinctTextsOccurrences("bill"));
    Assert.assertEquals(1, bag1.numberOfDistinctTextsOccurrences("goodbye"));

    Assert.assertEquals(1, bag2.numberOfDistinctTextsOccurrences("hello"));
    Assert.assertEquals(1, bag2.numberOfDistinctTextsOccurrences("kevin"));
    Assert.assertEquals(2, bag2.numberOfDistinctTextsOccurrences("joe"));
    Assert.assertEquals(1, bag2.numberOfDistinctTextsOccurrences("bill"));
    Assert.assertEquals(1, bag2.numberOfDistinctTextsOccurrences("goodbye"));
  }

  @Test
  public void testNumberOfDistinctTextsOccurrences2() {

    IBagOfTexts bag1 = bagOfTexts();
    IBagOfTexts bag2 = frozenBagOfTexts();

    Assert.assertEquals(1, bag1.numberOfDistinctTextsOccurrences("hello", "kevin"));
    Assert.assertEquals(1, bag1.numberOfDistinctTextsOccurrences("hello", "joe"));
    Assert.assertEquals(1, bag1.numberOfDistinctTextsOccurrences("goodbye", "bill"));
    Assert.assertEquals(1, bag1.numberOfDistinctTextsOccurrences("goodbye", "joe"));

    Assert.assertEquals(1, bag2.numberOfDistinctTextsOccurrences("hello", "kevin"));
    Assert.assertEquals(1, bag2.numberOfDistinctTextsOccurrences("hello", "joe"));
    Assert.assertEquals(1, bag2.numberOfDistinctTextsOccurrences("goodbye", "bill"));
    Assert.assertEquals(1, bag2.numberOfDistinctTextsOccurrences("goodbye", "joe"));
  }

  @Test
  public void testDocumentFrequency1() {

    IBagOfTexts bag1 = bagOfTexts();
    IBagOfTexts bag2 = frozenBagOfTexts();

    Assert.assertEquals(0.5, bag1.documentFrequency("hello"), 0.000001);
    Assert.assertEquals(1.0, bag1.documentFrequency("joe"), 0.000001);

    Assert.assertEquals(0.5, bag2.documentFrequency("hello"), 0.000001);
    Assert.assertEquals(1.0, bag2.documentFrequency("joe"), 0.000001);
  }

  @Test
  public void testDocumentFrequency2() {

    IBagOfTexts bag1 = bagOfTexts();
    IBagOfTexts bag2 = frozenBagOfTexts();

    Assert.assertEquals(0.5, bag1.documentFrequency("hello", "joe"), 0.000001);
    Assert.assertEquals(0.5, bag1.documentFrequency("goodbye", "joe"), 0.000001);

    Assert.assertEquals(0.5, bag2.documentFrequency("hello", "joe"), 0.000001);
    Assert.assertEquals(0.5, bag2.documentFrequency("goodbye", "joe"), 0.000001);
  }

  @Test
  public void testTermFrequency1() {

    IBagOfTexts bag1 = bagOfTexts();
    IBagOfTexts bag2 = frozenBagOfTexts();

    Assert.assertEquals(0.5, bag1.termFrequency(textHello(), "hello"), 0.000001);
    Assert.assertEquals(0.25, bag1.termFrequency(textHello(), "joe"), 0.000001);
    Assert.assertEquals(0.0, bag1.termFrequency(textGoodbye(), "hello"), 0.000001);
    Assert.assertEquals(0.25, bag1.termFrequency(textGoodbye(), "joe"), 0.000001);

    Assert.assertEquals(0.5, bag2.termFrequency(textHello(), "hello"), 0.000001);
    Assert.assertEquals(0.25, bag2.termFrequency(textHello(), "joe"), 0.000001);
    Assert.assertEquals(0.0, bag2.termFrequency(textGoodbye(), "hello"), 0.000001);
    Assert.assertEquals(0.25, bag2.termFrequency(textGoodbye(), "joe"), 0.000001);
  }

  @Test
  public void testTermFrequency2() {

    IBagOfTexts bag1 = bagOfTexts();
    IBagOfTexts bag2 = frozenBagOfTexts();

    Assert.assertEquals(0.5, bag1.termFrequency(textHello(), "hello", "joe"), 0.000001);
    Assert.assertEquals(0.0, bag1.termFrequency(textHello(), "goodbye", "joe"), 0.000001);
    Assert.assertEquals(0.0, bag1.termFrequency(textGoodbye(), "hello", "joe"), 0.000001);
    Assert.assertEquals(0.5, bag1.termFrequency(textGoodbye(), "goodbye", "joe"), 0.000001);

    Assert.assertEquals(0.5, bag2.termFrequency(textHello(), "hello", "joe"), 0.000001);
    Assert.assertEquals(0.0, bag2.termFrequency(textHello(), "goodbye", "joe"), 0.000001);
    Assert.assertEquals(0.0, bag2.termFrequency(textGoodbye(), "hello", "joe"), 0.000001);
    Assert.assertEquals(0.5, bag2.termFrequency(textGoodbye(), "goodbye", "joe"), 0.000001);
  }

  @Test
  public void testInverseDocumentFrequency1() {

    IBagOfTexts bag1 = bagOfTexts();
    IBagOfTexts bag2 = frozenBagOfTexts();

    Assert.assertEquals(1.0, bag1.inverseDocumentFrequency("hello"), 0.000001);
    Assert.assertEquals(0.5945348918918356, bag1.inverseDocumentFrequency("joe"), 0.000001);

    Assert.assertEquals(1.0, bag2.inverseDocumentFrequency("hello"), 0.000001);
    Assert.assertEquals(0.5945348918918356, bag2.inverseDocumentFrequency("joe"), 0.000001);
  }

  @Test
  public void testInverseDocumentFrequency2() {

    IBagOfTexts bag1 = bagOfTexts();
    IBagOfTexts bag2 = frozenBagOfTexts();

    Assert.assertEquals(1.0, bag1.inverseDocumentFrequency("hello", "joe"), 0.000001);
    Assert.assertEquals(1.0, bag1.inverseDocumentFrequency("goodbye", "joe"), 0.000001);

    Assert.assertEquals(1.0, bag2.inverseDocumentFrequency("hello", "joe"), 0.000001);
    Assert.assertEquals(1.0, bag2.inverseDocumentFrequency("goodbye", "joe"), 0.000001);
  }

  @Test
  public void testTfIdf1() {

    IBagOfTexts bag1 = bagOfTexts();
    IBagOfTexts bag2 = frozenBagOfTexts();

    Assert.assertEquals(0.5, bag1.tfIdf(textHello(), "hello"), 0.000001);
    Assert.assertEquals(0.1486337229729589, bag1.tfIdf(textHello(), "joe"), 0.000001);
    Assert.assertEquals(0.0, bag1.tfIdf(textGoodbye(), "hello"), 0.000001);
    Assert.assertEquals(0.1486337229729589, bag1.tfIdf(textGoodbye(), "joe"), 0.000001);

    Assert.assertEquals(0.5, bag2.tfIdf(textHello(), "hello"), 0.000001);
    Assert.assertEquals(0.1486337229729589, bag2.tfIdf(textHello(), "joe"), 0.000001);
    Assert.assertEquals(0.0, bag2.tfIdf(textGoodbye(), "hello"), 0.000001);
    Assert.assertEquals(0.1486337229729589, bag2.tfIdf(textGoodbye(), "joe"), 0.000001);
  }

  @Test
  public void testTfIdf2() {

    IBagOfTexts bag1 = bagOfTexts();
    IBagOfTexts bag2 = frozenBagOfTexts();

    Assert.assertEquals(0.5, bag1.tfIdf(textHello(), "hello", "joe"), 0.000001);
    Assert.assertEquals(0.5, bag1.tfIdf(textHello(), "hello", "joe"), 0.000001);
    Assert.assertEquals(0.5, bag1.tfIdf(textGoodbye(), "goodbye", "joe"), 0.000001);
    Assert.assertEquals(0.5, bag1.tfIdf(textGoodbye(), "goodbye", "joe"), 0.000001);

    Assert.assertEquals(0.5, bag2.tfIdf(textHello(), "hello", "joe"), 0.000001);
    Assert.assertEquals(0.5, bag2.tfIdf(textHello(), "hello", "joe"), 0.000001);
    Assert.assertEquals(0.5, bag2.tfIdf(textGoodbye(), "goodbye", "joe"), 0.000001);
    Assert.assertEquals(0.5, bag2.tfIdf(textGoodbye(), "goodbye", "joe"), 0.000001);
  }

  @Test
  public void testFindNoWord() {

    IBagOfTexts bag1 = bagOfTexts();
    List<Text> set1 = bag1.find(Sets.newHashSet(), 3);

    IBagOfTexts bag2 = frozenBagOfTexts();
    List<Text> set2 = bag2.find(Sets.newHashSet(), 3);

    Assert.assertEquals(0, set1.size());
    Assert.assertEquals(0, set2.size());
  }

  @Test
  public void testFindOneWord() {

    IBagOfTexts bag1 = bagOfTexts();
    List<Text> set1 = bag1.find(Sets.newHashSet("joe"), 3);

    IBagOfTexts bag2 = frozenBagOfTexts();
    List<Text> set2 = bag2.find(Sets.newHashSet("joe"), 3);

    Assert.assertEquals(2, set1.size());
    Assert.assertTrue(set1.contains(textHello()));
    Assert.assertTrue(set1.contains(textGoodbye()));

    Assert.assertEquals(2, set2.size());
    Assert.assertTrue(set2.contains(textHello()));
    Assert.assertTrue(set2.contains(textGoodbye()));
  }

  @Test
  public void testFindTwoWords() {

    IBagOfTexts bag1 = bagOfTexts();
    List<Text> set1 = bag1.find(Sets.newHashSet("john", "hello"), 3);

    IBagOfTexts bag2 = frozenBagOfTexts();
    List<Text> set2 = bag2.find(Sets.newHashSet("john", "hello"), 3);

    Assert.assertEquals(1, set1.size());
    Assert.assertTrue(set1.contains(textHello()));
    Assert.assertFalse(set1.contains(textGoodbye()));

    Assert.assertEquals(1, set2.size());
    Assert.assertTrue(set2.contains(textHello()));
    Assert.assertFalse(set2.contains(textGoodbye()));
  }

  private IBagOfTexts frozenBagOfTexts() {
    return bagOfTexts().freezeBagOfTexts();
  }

  private IBagOfTexts bagOfTexts() {

    BagOfTexts bag = new BagOfTexts(sentenceSplitter(), wordSplitter());
    bag.add(textHello().text());
    bag.add(textHello().text());
    bag.add(textGoodbye().text());
    return bag;
  }

  private Multiset<Text> texts() {

    Multiset<Text> texts = HashMultiset.create();
    texts.add(textHello(), 2);
    texts.add(textGoodbye());
    return texts;
  }

  private Multiset<String> words() {

    Multiset<String> words = HashMultiset.create();
    words.add("hello", 2);
    words.add("kevin");
    words.add("joe", 2);
    words.add("goodbye", 2);
    words.add("bill");
    return words;
  }

  private Multiset<List<String>> bigrams() {

    Multiset<List<String>> bigrams = HashMultiset.create();
    bigrams.add(Lists.newArrayList("hello", "kevin"));
    bigrams.add(Lists.newArrayList("hello", "joe"));
    bigrams.add(Lists.newArrayList("goodbye", "bill"));
    bigrams.add(Lists.newArrayList("goodbye", "joe"));
    return bigrams;
  }

  private Text textHello() {
    return new Text("Hello Kevin!\nHello Joe!", sentenceSplitter(), wordSplitter());
  }

  private Text textGoodbye() {
    return new Text("Goodbye Bill.\nGoodbye Joe.", sentenceSplitter(), wordSplitter());
  }

  private Function<String, List<String>> sentenceSplitter() {
    return text -> Splitter.on(CharMatcher.anyOf("\n\r")).trimResults().omitEmptyStrings()
        .splitToList(text);
  }

  private Function<String, List<String>> wordSplitter() {
    return text -> Splitter.on(CharMatcher.whitespace().or(CharMatcher.breakingWhitespace()))
        .trimResults().omitEmptyStrings().splitToList(
            StringIterator.removeDiacriticalMarks(text).replaceAll("\\p{P}", " ").toLowerCase());
  }
}
