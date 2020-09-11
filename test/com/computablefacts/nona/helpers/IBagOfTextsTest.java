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

public class IBagOfTextsTest {

  @Test
  public void testLikelihoodRatio() {

    IBagOfTexts bagOfTexts = bagOfTexts();

    double ratio1 = bagOfTexts.likelihoodRatio("hello", "joe");
    double ratio2 = bagOfTexts.likelihoodRatio("goodbye", "kevin");

    Assert.assertEquals(0.81803, ratio1, 0.00001);
    Assert.assertEquals(0.58138, ratio2, 0.00001);

    double ratio3 = bagOfTexts.likelihoodRatio("goodbye", "");
    double ratio4 = bagOfTexts.likelihoodRatio("", "kevin");

    Assert.assertTrue(Double.isNaN(ratio3));
    Assert.assertTrue(Double.isNaN(ratio4));
  }

  /**
   * See https://nlp.stanford.edu/fsnlp/promo/colloc.pdf page 163 for details.
   */
  @Test
  public void testLikelihoodRatioFormulaC1AboveC2() {

    int c1 = 12593;
    int c2 = 932;
    int c12 = 150;
    long N = 14307668; // See https://nlp.stanford.edu/fsnlp/promo/colloc.pdf page 158

    double ratio = IBagOfTexts.likelihoodRatio(c1, c2, c12, N);

    Assert.assertEquals(1291.32, ratio, 0.001);
  }

  /**
   * See https://nlp.stanford.edu/fsnlp/promo/colloc.pdf page 163 for details.
   */
  @Test
  public void testLikelihoodRatioFormulaC1UnderC2() {

    int c1 = 932;
    int c2 = 2064;
    int c12 = 8;
    long N = 14307668; // See https://nlp.stanford.edu/fsnlp/promo/colloc.pdf page 158

    double ratio = IBagOfTexts.likelihoodRatio(c1, c2, c12, N);

    Assert.assertEquals(49.741, ratio, 0.001);
  }

  @Test
  public void testSimpleBagOfTextsEquals() {

    IBagOfTexts bag1 = bagOfTexts();
    IBagOfTexts bag2 = bagOfTexts();

    Assert.assertFalse(bag1.equals(null));
    Assert.assertFalse(bag1.equals("string"));

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

  @Test
  public void testFindNoWord() {

    IBagOfTexts bag = bagOfTexts();
    List<Text> set = bag.find(Sets.newHashSet(), 3);

    Assert.assertEquals(0, set.size());
  }

  @Test
  public void testFindOneWord() {

    IBagOfTexts bag = bagOfTexts();
    List<Text> set = bag.find(Sets.newHashSet("joe"), 3);

    Assert.assertEquals(2, set.size());
    Assert.assertTrue(set.contains(textHello()));
    Assert.assertTrue(set.contains(textGoodbye()));
  }

  @Test
  public void testFindTwoWords() {

    IBagOfTexts bag = bagOfTexts();
    List<Text> set = bag.find(Sets.newHashSet("john", "hello"), 3);

    Assert.assertEquals(1, set.size());
    Assert.assertTrue(set.contains(textHello()));
    Assert.assertFalse(set.contains(textGoodbye()));
  }

  private IBagOfTexts bagOfTexts() {
    return IBagOfTexts.wrap(texts(), words(), bigrams());
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
