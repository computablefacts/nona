package com.computablefacts.nona.helpers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.errorprone.annotations.Var;

public class IBagOfNGramsTest {

  @Test
  public void testSimpleBagOfNGramsEquals() {

    IBagOfNGrams bag1 = bagOfNGrams();
    IBagOfNGrams bag2 = bagOfNGrams();

    Assert.assertFalse(bag1.equals(null));
    Assert.assertFalse(bag1.equals("string"));

    Assert.assertTrue(bag1.equals(bag2));
    Assert.assertTrue(bag2.equals(bag1));
  }

  @Test
  public void testSimpleBagOfNGramsHashcode() {

    IBagOfNGrams bag1 = bagOfNGrams();
    IBagOfNGrams bag2 = bagOfNGrams();

    Assert.assertEquals(bag1.hashCode(), bag2.hashCode());
  }

  @Test
  public void testBagOfNGrams() {

    IBagOfNGrams bag = bagOfNGrams();

    Assert.assertEquals(ngrams(), bag.bagOfNGrams());
  }

  @Test
  public void testFreezeBagOfNGrams() {

    IBagOfNGrams bag = bagOfNGrams();
    IBagOfNGrams copy = IBagOfNGrams.wrap(ngrams());
    IBagOfNGrams frozen = bag.freezeBagOfNGrams();

    Assert.assertEquals(copy, frozen);
  }

  @Test
  public void testUniqueNGrams() {

    IBagOfNGrams bag = bagOfNGrams();

    Set<List<String>> ngrams = new HashSet<>();

    ngrams.add(Lists.newArrayList("a", "b"));
    ngrams.add(Lists.newArrayList("a", "d"));
    ngrams.add(Lists.newArrayList("c", "d"));
    ngrams.add(Lists.newArrayList("c", "e"));
    ngrams.add(Lists.newArrayList("f", "g"));

    ngrams.add(Lists.newArrayList("a", "b", "c"));
    ngrams.add(Lists.newArrayList("a", "b", "d"));
    ngrams.add(Lists.newArrayList("c", "d", "e"));
    ngrams.add(Lists.newArrayList("c", "d", "d"));

    Assert.assertEquals(ngrams, bag.uniqueNGrams());
  }

  @Test
  public void testNumberOfNGrams() {

    IBagOfNGrams bag = bagOfNGrams();

    Assert.assertEquals(13, bag.numberOfNGrams());
  }

  @Test
  public void testNumberOfDistinctNGrams() {

    IBagOfNGrams bag = bagOfNGrams();

    Assert.assertEquals(9, bag.numberOfDistinctNGrams());
  }

  @Test
  public void testFrequency() {

    IBagOfNGrams bag = bagOfNGrams();

    Assert.assertEquals(1, bag.frequency("a", "b"));
    Assert.assertEquals(1, bag.frequency("a", "d"));
    Assert.assertEquals(1, bag.frequency("c", "d"));
    Assert.assertEquals(3, bag.frequency("c", "e"));
    Assert.assertEquals(1, bag.frequency("f", "g"));

    Assert.assertEquals(1, bag.frequency("a", "b", "c"));
    Assert.assertEquals(1, bag.frequency("a", "b", "d"));
    Assert.assertEquals(3, bag.frequency("c", "d", "e"));
    Assert.assertEquals(1, bag.frequency("c", "d", "d"));
  }

  @Test
  public void testNormalizedFrequency() {

    IBagOfNGrams bag = bagOfNGrams();

    Assert.assertEquals(1.0 / 7.0, bag.normalizedFrequency("a", "b"), 0.00000001);
    Assert.assertEquals(1.0 / 7.0, bag.normalizedFrequency("a", "d"), 0.00000001);
    Assert.assertEquals(1.0 / 7.0, bag.normalizedFrequency("c", "d"), 0.00000001);
    Assert.assertEquals(3.0 / 7.0, bag.normalizedFrequency("c", "e"), 0.00000001);
    Assert.assertEquals(1.0 / 7.0, bag.normalizedFrequency("f", "g"), 0.00000001);

    Assert.assertEquals(1.0 / 6.0, bag.normalizedFrequency("a", "b", "c"), 0.00000001);
    Assert.assertEquals(1.0 / 6.0, bag.normalizedFrequency("a", "b", "d"), 0.00000001);
    Assert.assertEquals(3.0 / 6.0, bag.normalizedFrequency("c", "d", "e"), 0.00000001);
    Assert.assertEquals(1.0 / 6.0, bag.normalizedFrequency("c", "d", "d"), 0.00000001);
  }

  @Test
  public void testMergeWith() {

    IBagOfNGrams bag1 = bagOfNGrams();
    IBagOfNGrams bag2 = bagOfNGrams();

    Multiset<List<String>> ngrams = bag1.mergeWith(bag2);

    // Ensure bag1 has not been modified
    Assert.assertEquals(13, bag1.numberOfNGrams());

    Assert.assertEquals(1, bag1.frequency("a", "b"));
    Assert.assertEquals(1, bag1.frequency("a", "d"));
    Assert.assertEquals(1, bag1.frequency("c", "d"));
    Assert.assertEquals(3, bag1.frequency("c", "e"));
    Assert.assertEquals(1, bag1.frequency("f", "g"));

    Assert.assertEquals(1, bag1.frequency("a", "b", "c"));
    Assert.assertEquals(1, bag1.frequency("a", "b", "d"));
    Assert.assertEquals(3, bag1.frequency("c", "d", "e"));
    Assert.assertEquals(1, bag1.frequency("c", "d", "d"));

    // Ensure bag2 has not been modified
    Assert.assertEquals(13, bag2.numberOfNGrams());

    Assert.assertEquals(1, bag2.frequency("a", "b"));
    Assert.assertEquals(1, bag2.frequency("a", "d"));
    Assert.assertEquals(1, bag2.frequency("c", "d"));
    Assert.assertEquals(3, bag2.frequency("c", "e"));
    Assert.assertEquals(1, bag2.frequency("f", "g"));

    Assert.assertEquals(1, bag2.frequency("a", "b", "c"));
    Assert.assertEquals(1, bag2.frequency("a", "b", "d"));
    Assert.assertEquals(3, bag2.frequency("c", "d", "e"));
    Assert.assertEquals(1, bag2.frequency("c", "d", "d"));

    // Ensure merge is ok
    Assert.assertEquals(26, ngrams.size());

    Assert.assertEquals(2, ngrams.count(Lists.newArrayList("a", "b")));
    Assert.assertEquals(2, ngrams.count(Lists.newArrayList("a", "d")));
    Assert.assertEquals(2, ngrams.count(Lists.newArrayList("c", "d")));
    Assert.assertEquals(6, ngrams.count(Lists.newArrayList("c", "e")));
    Assert.assertEquals(2, ngrams.count(Lists.newArrayList("f", "g")));

    Assert.assertEquals(2, ngrams.count(Lists.newArrayList("a", "b", "c")));
    Assert.assertEquals(2, ngrams.count(Lists.newArrayList("a", "b", "d")));
    Assert.assertEquals(6, ngrams.count(Lists.newArrayList("c", "d", "e")));
    Assert.assertEquals(2, ngrams.count(Lists.newArrayList("c", "d", "d")));
  }

  @Test
  public void testMostProbableNextBigramWord() {

    IBagOfNGrams bag = bagOfNGrams();

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

  @Test
  public void testMostProbableNextTrigramWord() {

    IBagOfNGrams bag = bagOfNGrams();

    @Var
    int b = 0;
    @Var
    int d = 0;

    for (int i = 0; i < 100000; i++) {

      String word = bag.mostProbableNextWord("a", "b");

      if (word.equals("c")) {
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

      String word = bag.mostProbableNextWord("c", "d");

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

  private IBagOfNGrams bagOfNGrams() {
    return IBagOfNGrams.wrap(ngrams());
  }

  private Multiset<List<String>> ngrams() {

    Multiset<List<String>> bag = HashMultiset.create();

    bag.add(Lists.newArrayList("a", "b"));
    bag.add(Lists.newArrayList("a", "d"));
    bag.add(Lists.newArrayList("c", "d"));
    bag.add(Lists.newArrayList("c", "e"), 3);
    bag.add(Lists.newArrayList("f", "g"));

    bag.add(Lists.newArrayList("a", "b", "c"));
    bag.add(Lists.newArrayList("a", "b", "d"));
    bag.add(Lists.newArrayList("c", "d", "e"), 3);
    bag.add(Lists.newArrayList("c", "d", "d"));

    return bag;
  }
}
