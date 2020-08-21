package com.computablefacts.nona.functions.utils;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.types.Text;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;

public class BagOfTextsImplTest {

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

    IBagOfTexts bag = frozenBagOfTexts();

    Assert.assertEquals(texts(), bag.bagOfTexts());
  }

  @Test
  public void testBagOfWords() {

    IBagOfTexts bag = frozenBagOfTexts();

    Assert.assertEquals(words(), bag.bagOfWords());
  }

  @Test
  public void testBagOfBigrams() {

    IBagOfTexts bag = frozenBagOfTexts();

    Assert.assertEquals(bigrams(), bag.bagOfBigrams());
  }

  @Test
  public void testFreezeBagOfTexts() {

    IBagOfTexts copy = BagOfTexts.wrap(texts(), words(), bigrams());
    IBagOfTexts frozen = frozenBagOfTexts();

    Assert.assertEquals(copy, frozen);
  }

  @Test
  public void testText() {

    IBagOfTexts bag = frozenBagOfTexts();

    Assert.assertEquals(textHello(), bag.text("Hello Kevin!\nHello Joe!"));
    Assert.assertEquals(textGoodbye(), bag.text("Goodbye Bill.\nGoodbye Joe."));
  }

  @Test
  public void testUniqueText() {

    IBagOfTexts bag = frozenBagOfTexts();
    Set<Text> set = texts().elementSet();

    Assert.assertEquals(2, bag.uniqueTexts().size());
    Assert.assertTrue(set.contains(textHello()));
    Assert.assertTrue(set.contains(textGoodbye()));
  }

  @Test
  public void testNumberOfTexts() {

    IBagOfTexts bag = frozenBagOfTexts();

    Assert.assertEquals(3, bag.numberOfTexts());
  }

  @Test
  public void testNumberOfDistinctTexts() {

    IBagOfTexts bag = frozenBagOfTexts();

    Assert.assertEquals(2, bag.numberOfDistinctTexts());
  }

  @Test
  public void testAverageTextLength() {

    IBagOfTexts bag = frozenBagOfTexts();

    Assert.assertEquals(4.0, bag.averageTextLength(), 0.000001);
  }

  @Test
  public void testNumberOfDistinctTextsOccurrences1() {

    IBagOfTexts bag = frozenBagOfTexts();

    Assert.assertEquals(1, bag.numberOfDistinctTextsOccurrences("hello"));
    Assert.assertEquals(1, bag.numberOfDistinctTextsOccurrences("kevin"));
    Assert.assertEquals(2, bag.numberOfDistinctTextsOccurrences("joe"));
    Assert.assertEquals(1, bag.numberOfDistinctTextsOccurrences("bill"));
    Assert.assertEquals(1, bag.numberOfDistinctTextsOccurrences("goodbye"));
  }

  @Test
  public void testNumberOfDistinctTextsOccurrences2() {

    IBagOfTexts bag = frozenBagOfTexts();

    Assert.assertEquals(1, bag.numberOfDistinctTextsOccurrences("hello", "kevin"));
    Assert.assertEquals(1, bag.numberOfDistinctTextsOccurrences("hello", "joe"));
    Assert.assertEquals(1, bag.numberOfDistinctTextsOccurrences("goodbye", "bill"));
    Assert.assertEquals(1, bag.numberOfDistinctTextsOccurrences("goodbye", "joe"));
  }

  @Test
  public void testDocumentFrequency1() {

    IBagOfTexts bag = frozenBagOfTexts();

    Assert.assertEquals(0.5, bag.documentFrequency("hello"), 0.000001);
    Assert.assertEquals(1.0, bag.documentFrequency("joe"), 0.000001);
  }

  @Test
  public void testDocumentFrequency2() {

    IBagOfTexts bag = frozenBagOfTexts();

    Assert.assertEquals(0.5, bag.documentFrequency("hello", "joe"), 0.000001);
    Assert.assertEquals(0.5, bag.documentFrequency("goodbye", "joe"), 0.000001);
  }

  @Test
  public void testTermFrequency1() {

    IBagOfTexts bag = frozenBagOfTexts();

    Assert.assertEquals(0.5, bag.termFrequency(textHello(), "hello"), 0.000001);
    Assert.assertEquals(0.25, bag.termFrequency(textHello(), "joe"), 0.000001);
    Assert.assertEquals(0.0, bag.termFrequency(textGoodbye(), "hello"), 0.000001);
    Assert.assertEquals(0.25, bag.termFrequency(textGoodbye(), "joe"), 0.000001);
  }

  @Test
  public void testTermFrequency2() {

    IBagOfTexts bag = frozenBagOfTexts();

    Assert.assertEquals(0.5, bag.termFrequency(textHello(), "hello", "joe"), 0.000001);
    Assert.assertEquals(0.0, bag.termFrequency(textHello(), "goodbye", "joe"), 0.000001);
    Assert.assertEquals(0.0, bag.termFrequency(textGoodbye(), "hello", "joe"), 0.000001);
    Assert.assertEquals(0.5, bag.termFrequency(textGoodbye(), "goodbye", "joe"), 0.000001);
  }

  @Test
  public void testInverseDocumentFrequency1() {

    IBagOfTexts bag = frozenBagOfTexts();

    Assert.assertEquals(1.0, bag.inverseDocumentFrequency("hello"), 0.000001);
    Assert.assertEquals(0.5945348918918356, bag.inverseDocumentFrequency("joe"), 0.000001);
  }

  @Test
  public void testInverseDocumentFrequency2() {

    IBagOfTexts bag = frozenBagOfTexts();

    Assert.assertEquals(1.0, bag.inverseDocumentFrequency("hello", "joe"), 0.000001);
    Assert.assertEquals(1.0, bag.inverseDocumentFrequency("goodbye", "joe"), 0.000001);
  }

  @Test
  public void testTfIdf1() {

    IBagOfTexts bag = frozenBagOfTexts();

    Assert.assertEquals(0.5, bag.tfIdf(textHello(), "hello"), 0.000001);
    Assert.assertEquals(0.1486337229729589, bag.tfIdf(textHello(), "joe"), 0.000001);
    Assert.assertEquals(0.0, bag.tfIdf(textGoodbye(), "hello"), 0.000001);
    Assert.assertEquals(0.1486337229729589, bag.tfIdf(textGoodbye(), "joe"), 0.000001);
  }

  @Test
  public void testTfIdf2() {

    IBagOfTexts bag = frozenBagOfTexts();

    Assert.assertEquals(0.5, bag.tfIdf(textHello(), "hello", "joe"), 0.000001);
    Assert.assertEquals(0.5, bag.tfIdf(textHello(), "hello", "joe"), 0.000001);
    Assert.assertEquals(0.5, bag.tfIdf(textGoodbye(), "goodbye", "joe"), 0.000001);
    Assert.assertEquals(0.5, bag.tfIdf(textGoodbye(), "goodbye", "joe"), 0.000001);
  }

  @Test
  public void testFindNoWord() {

    IBagOfTexts bag = frozenBagOfTexts();
    List<Text> set = bag.find(Sets.newHashSet(), 3);

    Assert.assertEquals(0, set.size());
  }

  @Test
  public void testFindOneWord() {

    IBagOfTexts bag = frozenBagOfTexts();
    List<Text> set = bag.find(Sets.newHashSet("joe"), 3);

    Assert.assertEquals(2, set.size());
    Assert.assertTrue(set.contains(textHello()));
    Assert.assertTrue(set.contains(textGoodbye()));
  }

  @Test
  public void testFindTwoWords() {

    IBagOfTexts bag = frozenBagOfTexts();
    List<Text> set = bag.find(Sets.newHashSet("john", "hello"), 3);

    Assert.assertEquals(1, set.size());
    Assert.assertTrue(set.contains(textHello()));
    Assert.assertFalse(set.contains(textGoodbye()));
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

  private Multiset<Map.Entry<String, String>> bigrams() {

    Multiset<Map.Entry<String, String>> bigrams = HashMultiset.create();
    bigrams.add(new AbstractMap.SimpleEntry<>("hello", "kevin"));
    bigrams.add(new AbstractMap.SimpleEntry<>("hello", "joe"));
    bigrams.add(new AbstractMap.SimpleEntry<>("goodbye", "bill"));
    bigrams.add(new AbstractMap.SimpleEntry<>("goodbye", "joe"));
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
