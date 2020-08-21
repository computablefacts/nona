package com.computablefacts.nona.helpers;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.errorprone.annotations.Var;

public class BagOfBigramsTest {

  @Test
  public void testSimpleBagOfBigramsEquals() {

    IBagOfBigrams bag1 = bagOfBigrams();
    IBagOfBigrams bag2 = bagOfBigrams();

    Assert.assertFalse(bag1.equals(null));
    Assert.assertFalse(bag1.equals("string"));

    Assert.assertTrue(bag1.equals(bag2));
    Assert.assertTrue(bag2.equals(bag1));
  }

  @Test
  public void testSimpleBagOfBigramsHashcode() {

    IBagOfBigrams bag1 = bagOfBigrams();
    IBagOfBigrams bag2 = bagOfBigrams();

    Assert.assertEquals(bag1.hashCode(), bag2.hashCode());
  }

  @Test
  public void testBagOfBigrams() {

    IBagOfBigrams bag = bagOfBigrams();

    Assert.assertEquals(bigrams(), bag.bagOfBigrams());
  }

  @Test
  public void testFreezeBagOfBigrams() {

    IBagOfBigrams bag = bagOfBigrams();
    IBagOfBigrams copy = IBagOfBigrams.wrap(bigrams());
    IBagOfBigrams frozen = bag.freezeBagOfBigrams();

    Assert.assertEquals(copy, frozen);
  }

  @Test
  public void testUniqueBigrams() {

    IBagOfBigrams bag = bagOfBigrams();

    Set<Map.Entry<String, String>> bigrams = new HashSet<>();
    bigrams.add(new AbstractMap.SimpleEntry<>("a", "b"));
    bigrams.add(new AbstractMap.SimpleEntry<>("a", "d"));
    bigrams.add(new AbstractMap.SimpleEntry<>("c", "d"));
    bigrams.add(new AbstractMap.SimpleEntry<>("c", "e"));
    bigrams.add(new AbstractMap.SimpleEntry<>("f", "g"));

    Assert.assertEquals(bigrams, bag.uniqueBigrams());
  }

  @Test
  public void testNumberOfBigrams() {

    IBagOfBigrams bag = bagOfBigrams();

    Assert.assertEquals(7, bag.numberOfBigrams());
  }

  @Test
  public void testNumberOfDistinctBigrams() {

    IBagOfBigrams bag = bagOfBigrams();

    Assert.assertEquals(5, bag.numberOfDistinctBigrams());
  }

  @Test
  public void testFrequency() {

    IBagOfBigrams bag = bagOfBigrams();

    Assert.assertEquals(1, bag.frequency("a", "b"));
    Assert.assertEquals(1, bag.frequency("a", "d"));
    Assert.assertEquals(1, bag.frequency("c", "d"));
    Assert.assertEquals(3, bag.frequency("c", "e"));
    Assert.assertEquals(1, bag.frequency("f", "g"));
  }

  @Test
  public void testNormalizedFrequency() {

    IBagOfBigrams bag = bagOfBigrams();

    Assert.assertEquals(1.0 / 7.0, bag.normalizedFrequency("a", "b"), 0.00000001);
    Assert.assertEquals(1.0 / 7.0, bag.normalizedFrequency("a", "d"), 0.00000001);
    Assert.assertEquals(1.0 / 7.0, bag.normalizedFrequency("c", "d"), 0.00000001);
    Assert.assertEquals(3.0 / 7.0, bag.normalizedFrequency("c", "e"), 0.00000001);
    Assert.assertEquals(1.0 / 7.0, bag.normalizedFrequency("f", "g"), 0.00000001);
  }

  @Test
  public void testMergeWith() {

    IBagOfBigrams bag1 = bagOfBigrams();
    IBagOfBigrams bag2 = bagOfBigrams();

    Multiset<Map.Entry<String, String>> bigrams = bag1.mergeWith(bag2);

    // Ensure bag1 has not been modified
    Assert.assertEquals(7, bag1.numberOfBigrams());
    Assert.assertEquals(1, bag1.frequency("a", "b"));
    Assert.assertEquals(1, bag1.frequency("a", "d"));
    Assert.assertEquals(1, bag1.frequency("c", "d"));
    Assert.assertEquals(3, bag1.frequency("c", "e"));
    Assert.assertEquals(1, bag1.frequency("f", "g"));

    // Ensure bag2 has not been modified
    Assert.assertEquals(7, bag2.numberOfBigrams());
    Assert.assertEquals(1, bag2.frequency("a", "b"));
    Assert.assertEquals(1, bag2.frequency("a", "d"));
    Assert.assertEquals(1, bag2.frequency("c", "d"));
    Assert.assertEquals(3, bag2.frequency("c", "e"));
    Assert.assertEquals(1, bag2.frequency("f", "g"));

    // Ensure merge is ok
    Assert.assertEquals(14, bigrams.size());
    Assert.assertEquals(2, bigrams.count(new AbstractMap.SimpleEntry<>("a", "b")));
    Assert.assertEquals(2, bigrams.count(new AbstractMap.SimpleEntry<>("a", "d")));
    Assert.assertEquals(2, bigrams.count(new AbstractMap.SimpleEntry<>("c", "d")));
    Assert.assertEquals(6, bigrams.count(new AbstractMap.SimpleEntry<>("c", "e")));
    Assert.assertEquals(2, bigrams.count(new AbstractMap.SimpleEntry<>("f", "g")));
  }

  @Test
  public void testMostProbableNextWord2() {

    IBagOfBigrams bag = bagOfBigrams();

    @Var
    int b = 0;
    @Var
    int d = 0;

    for (int i = 0; i < 100000; i++) {

      String word = bag.mostProbableNextWord("a");

      if (word.equals("b")) {
        b++;
      } else if (word.equals("d")) {
        d++;
      } else {
        Preconditions.checkState(false, "Invalid next word : %s", word);
      }
    }

    Assert.assertTrue(49000 <= b && b <= 51000);
    Assert.assertTrue(49000 <= d && d <= 51000);

    @Var
    int e = 0;
    d = 0;

    for (int i = 0; i < 100000; i++) {

      String word = bag.mostProbableNextWord("c");

      if (word.equals("d")) {
        d++;
      } else if (word.equals("e")) {
        e++;
      } else {
        Preconditions.checkState(false, "Invalid next word : %s", word);
      }
    }

    Assert.assertTrue(24000 <= d && d <= 26000);
    Assert.assertTrue(74000 <= e && e <= 76000);
  }

  private IBagOfBigrams bagOfBigrams() {
    return IBagOfBigrams.wrap(bigrams());
  }

  private Multiset<Map.Entry<String, String>> bigrams() {

    Multiset<Map.Entry<String, String>> bag = HashMultiset.create();

    bag.add(new AbstractMap.SimpleEntry<>("a", "b"));
    bag.add(new AbstractMap.SimpleEntry<>("a", "d"));
    bag.add(new AbstractMap.SimpleEntry<>("c", "d"));
    bag.add(new AbstractMap.SimpleEntry<>("c", "e"), 3);
    bag.add(new AbstractMap.SimpleEntry<>("f", "g"));

    return bag;
  }
}
