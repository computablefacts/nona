package com.computablefacts.nona.functions.utils;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.types.BagOfTexts;
import com.computablefacts.nona.types.Text;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

public class BagOfTextsTest {

  @Test(expected = NullPointerException.class)
  public void testAnagramHashWithNullWord() {
    IBagOfTexts.anagramHash(null);
  }

  @Test
  public void testAnagramHash() {
    Assert.assertEquals("39100657332", IBagOfTexts.anagramHash("act"));
    Assert.assertEquals("39100657332", IBagOfTexts.anagramHash("atc"));
    Assert.assertEquals("39100657332", IBagOfTexts.anagramHash("cat"));
    Assert.assertEquals("39100657332", IBagOfTexts.anagramHash("cta"));
    Assert.assertEquals("39100657332", IBagOfTexts.anagramHash("tac"));
    Assert.assertEquals("39100657332", IBagOfTexts.anagramHash("tca"));
  }

  @Test(expected = NullPointerException.class)
  public void testLevenshteinDistanceWithNullWord1() {
    IBagOfTexts.levenshteinDistance(null, "word2");
  }

  @Test(expected = NullPointerException.class)
  public void testLevenshteinDistanceWithNullWord2() {
    IBagOfTexts.levenshteinDistance("word1", null);
  }

  @Test
  public void testLevenshteinDistance() {
    Assert.assertEquals(0, IBagOfTexts.levenshteinDistance("", ""));
    Assert.assertEquals(0, IBagOfTexts.levenshteinDistance("ABCDE", "ABCDE"));
    Assert.assertEquals(1, IBagOfTexts.levenshteinDistance("ABCDE", "ABCD"));
    Assert.assertEquals(3, IBagOfTexts.levenshteinDistance("kitten", "sitting"));
    Assert.assertEquals(5, IBagOfTexts.levenshteinDistance("matrix", "xxxmatr"));
    Assert.assertEquals(8, IBagOfTexts.levenshteinDistance("rosettacode", "raisethysword"));
  }

  @Test(expected = NullPointerException.class)
  public void testNormalizedLevenshteinDistanceWithNullWord1() {
    IBagOfTexts.levenshteinDistance(null, "word2");
  }

  @Test(expected = NullPointerException.class)
  public void testNormalizedLevenshteinDistanceWithNullWord2() {
    IBagOfTexts.levenshteinDistance("word1", null);
  }

  @Test
  public void testNormalizedLevenshteinDistance() {
    Assert.assertEquals(0.0, IBagOfTexts.normalizedLevenshteinDistance("", ""), 0.00000001);
    Assert.assertEquals(0.0, IBagOfTexts.normalizedLevenshteinDistance("ABCDE", "ABCDE"),
        0.00000001);
    Assert.assertEquals(1.0 / 5.0, IBagOfTexts.normalizedLevenshteinDistance("ABCDE", "ABCD"),
        0.00000001);
    Assert.assertEquals(3.0 / 7.0, IBagOfTexts.normalizedLevenshteinDistance("kitten", "sitting"),
        0.00000001);
    Assert.assertEquals(5.0 / 7.0, IBagOfTexts.normalizedLevenshteinDistance("matrix", "xxxmatr"),
        0.00000001);
    Assert.assertEquals(8.0 / 13.0,
        IBagOfTexts.normalizedLevenshteinDistance("rosettacode", "raisethysword"), 0.00000001);
  }

  @Test
  public void testSimpleBagOfTextsEquals() {

    IBagOfTexts bag1 = bagOfTexts();
    IBagOfTexts bag2 = bagOfTexts();

    Assert.assertTrue(bag1.equals(bag2));
    Assert.assertTrue(bag2.equals(bag1));
  }

  @Test
  public void testSimpleBagOfTextsHashcode() {

    IBagOfTexts bag1 = bagOfTexts();
    IBagOfTexts bag2 = bagOfTexts();

    Assert.assertEquals(bag1.hashCode(), bag2.hashCode());
  }

  @Test
  public void testBagOfTexts() {

    IBagOfTexts bag = bagOfTexts();

    Assert.assertEquals(texts(), bag.bagOfTexts());
  }

  @Test
  public void testBagOfWords() {

    IBagOfTexts bag = bagOfTexts();

    Assert.assertEquals(words(), bag.bagOfWords());
  }

  @Test
  public void testBagOfBigrams() {

    IBagOfTexts bag = bagOfTexts();

    Assert.assertEquals(bigrams(), bag.bagOfBigrams());
  }

  @Test
  public void testFreezeBagOfTexts() {

    IBagOfTexts bag = bagOfTexts();
    IBagOfTexts copy = IBagOfTexts.wrap(texts(), words(), bigrams());
    IBagOfTexts frozen = bag.freezeBagOfTexts();

    Assert.assertEquals(copy, frozen);
  }

  @Test
  public void testText() {

    IBagOfTexts bag = bagOfTexts();

    Assert.assertEquals(textHello(), bag.text("Hello Kevin!\nHello Joe!"));
    Assert.assertEquals(textGoodbye(), bag.text("Goodbye Bill.\nGoodbye Joe."));
  }

  @Test
  public void testUniqueText() {

    IBagOfTexts bag = bagOfTexts();
    Set<Text> set = texts().elementSet();

    Assert.assertEquals(2, bag.uniqueTexts().size());
    Assert.assertTrue(set.contains(textHello()));
    Assert.assertTrue(set.contains(textGoodbye()));
  }

  @Test
  public void testNumberOfTexts() {

    IBagOfTexts bag = bagOfTexts();

    Assert.assertEquals(3, bag.numberOfTexts());
  }

  @Test
  public void testNumberOfDistinctTexts() {

    IBagOfTexts bag = bagOfTexts();

    Assert.assertEquals(2, bag.numberOfDistinctTexts());
  }

  @Test
  public void testAverageTextLength() {

    IBagOfTexts bag = bagOfTexts();

    Assert.assertEquals(4.0, bag.averageTextLength(), 0.000001);
  }

  @Test
  public void testNumberOfDistinctTextsOccurrences1() {

    IBagOfTexts bag = bagOfTexts();

    Assert.assertEquals(1, bag.numberOfDistinctTextsOccurrences("hello"));
    Assert.assertEquals(1, bag.numberOfDistinctTextsOccurrences("kevin"));
    Assert.assertEquals(2, bag.numberOfDistinctTextsOccurrences("joe"));
    Assert.assertEquals(1, bag.numberOfDistinctTextsOccurrences("bill"));
    Assert.assertEquals(1, bag.numberOfDistinctTextsOccurrences("goodbye"));
  }

  @Test
  public void testNumberOfDistinctTextsOccurrences2() {

    IBagOfTexts bag = bagOfTexts();

    Assert.assertEquals(1, bag.numberOfDistinctTextsOccurrences("hello", "kevin"));
    Assert.assertEquals(1, bag.numberOfDistinctTextsOccurrences("hello", "joe"));
    Assert.assertEquals(1, bag.numberOfDistinctTextsOccurrences("goodbye", "bill"));
    Assert.assertEquals(1, bag.numberOfDistinctTextsOccurrences("goodbye", "joe"));
  }

  @Test
  public void testDocumentFrequency1() {

    IBagOfTexts bag = bagOfTexts();

    Assert.assertEquals(0.5, bag.documentFrequency("hello"), 0.000001);
    Assert.assertEquals(1.0, bag.documentFrequency("joe"), 0.000001);
  }

  @Test
  public void testDocumentFrequency2() {

    IBagOfTexts bag = bagOfTexts();

    Assert.assertEquals(0.5, bag.documentFrequency("hello", "joe"), 0.000001);
    Assert.assertEquals(0.5, bag.documentFrequency("goodbye", "joe"), 0.000001);
  }

  @Test
  public void testTermFrequency1() {

    IBagOfTexts bag = bagOfTexts();

    Assert.assertEquals(0.5, bag.termFrequency(textHello(), "hello"), 0.000001);
    Assert.assertEquals(0.25, bag.termFrequency(textHello(), "joe"), 0.000001);
    Assert.assertEquals(0.0, bag.termFrequency(textGoodbye(), "hello"), 0.000001);
    Assert.assertEquals(0.25, bag.termFrequency(textGoodbye(), "joe"), 0.000001);
  }

  @Test
  public void testTermFrequency2() {

    IBagOfTexts bag = bagOfTexts();

    Assert.assertEquals(0.5, bag.termFrequency(textHello(), "hello", "joe"), 0.000001);
    Assert.assertEquals(0.0, bag.termFrequency(textHello(), "goodbye", "joe"), 0.000001);
    Assert.assertEquals(0.0, bag.termFrequency(textGoodbye(), "hello", "joe"), 0.000001);
    Assert.assertEquals(0.5, bag.termFrequency(textGoodbye(), "goodbye", "joe"), 0.000001);
  }

  @Test
  public void testInverseDocumentFrequency1() {

    IBagOfTexts bag = bagOfTexts();

    Assert.assertEquals(1.0, bag.inverseDocumentFrequency("hello"), 0.000001);
    Assert.assertEquals(0.5945348918918356, bag.inverseDocumentFrequency("joe"), 0.000001);
  }

  @Test
  public void testInverseDocumentFrequency2() {

    IBagOfTexts bag = bagOfTexts();

    Assert.assertEquals(1.0, bag.inverseDocumentFrequency("hello", "joe"), 0.000001);
    Assert.assertEquals(1.0, bag.inverseDocumentFrequency("goodbye", "joe"), 0.000001);
  }

  @Test
  public void testTfIdf1() {

    IBagOfTexts bag = bagOfTexts();

    Assert.assertEquals(0.5, bag.tfIdf(textHello(), "hello"), 0.000001);
    Assert.assertEquals(0.1486337229729589, bag.tfIdf(textHello(), "joe"), 0.000001);
    Assert.assertEquals(0.0, bag.tfIdf(textGoodbye(), "hello"), 0.000001);
    Assert.assertEquals(0.1486337229729589, bag.tfIdf(textGoodbye(), "joe"), 0.000001);
  }

  @Test
  public void testTfIdf2() {

    IBagOfTexts bag = bagOfTexts();

    Assert.assertEquals(0.5, bag.tfIdf(textHello(), "hello", "joe"), 0.000001);
    Assert.assertEquals(0.5, bag.tfIdf(textHello(), "hello", "joe"), 0.000001);
    Assert.assertEquals(0.5, bag.tfIdf(textGoodbye(), "goodbye", "joe"), 0.000001);
    Assert.assertEquals(0.5, bag.tfIdf(textGoodbye(), "goodbye", "joe"), 0.000001);
  }

  private BagOfTexts bagOfTexts() {

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
