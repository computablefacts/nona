package com.computablefacts.nona.helpers;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Var;

@CheckReturnValue
public interface IBagOfNGrams {

  Random RNG = new Random();

  static IBagOfNGrams wrap(Multiset<List<String>> bag) {
    return new SimpleBagOfNGrams(bag);
  }

  /**
   * Returns a bag of ngrams.
   *
   * @return a bag of ngrams.
   */
  Multiset<List<String>> bagOfNGrams();

  /**
   * Returns a bag of bigrams.
   *
   * @return a bag of bigrams.
   */
  default Multiset<List<String>> bagOfBigrams() {
    return bagOfNGrams(2);
  }

  /**
   * Returns a bag of trigrams.
   *
   * @return a bag of trigrams.
   */
  default Multiset<List<String>> bagOfTrigrams() {
    return bagOfNGrams(3);
  }

  /**
   * Returns a bag of ngrams of length n.
   *
   * @param n ngram length.
   * @return a bag of ngrams of length n.
   */
  default Multiset<List<String>> bagOfNGrams(int n) {

    Preconditions.checkArgument(n > 1, "n must be > 1");

    Multiset<List<String>> bag = HashMultiset.create();

    bagOfNGrams().entrySet().forEach(e -> {
      if (e.getElement().size() == n) {
        bag.add(e.getElement(), e.getCount());
      }
    });
    return bag;
  }

  /**
   * Freeze the bag. From now on, it won't be possible to add new ngrams to it. Useful when
   * {@link #bagOfNGrams()} requires heavy computations.
   *
   * @return a frozen bag of ngrams.
   */
  default IBagOfNGrams freezeBagOfNGrams() {
    return wrap(bagOfNGrams());
  }

  /**
   * Freeze the bag. From now on, it won't be possible to add new ngrams to it. Useful when
   * {@link #bagOfNGrams(int)} requires heavy computations.
   *
   * @param n ngram length.
   * @return a frozen bag of ngrams of length n.
   */
  default IBagOfNGrams freezeBagOfNGrams(int n) {
    return wrap(bagOfNGrams(n));
  }

  /**
   * Returns unique ngrams.
   *
   * @return ngrams.
   */
  default Set<List<String>> uniqueNGrams() {
    return ImmutableSet.copyOf(bagOfNGrams().elementSet());
  }

  /**
   * Returns unique ngrams of length n.
   *
   * @param n ngram length.
   * @return ngrams of length n.
   */
  default Set<List<String>> uniqueNGrams(int n) {
    return ImmutableSet.copyOf(bagOfNGrams(n).elementSet());
  }

  /**
   * Returns the total number of ngrams, including duplicates.
   *
   * @return total number of ngrams.
   */
  default int numberOfNGrams() {
    return bagOfNGrams().size();
  }

  /**
   * Returns the total number of ngrams of length n, including duplicates.
   *
   * @param n ngram length.
   * @return total number of ngrams of length n.
   */
  default int numberOfNGrams(int n) {
    return bagOfNGrams(n).size();
  }

  /**
   * Returns the total number of distinct ngrams, excluding duplicates.
   *
   * @return total number of distinct ngrams. This value is in [0, #ngrams].
   */
  default int numberOfDistinctNGrams() {
    return bagOfNGrams().elementSet().size();
  }

  /**
   * Returns the total number of distinct ngrams of length n, excluding duplicates.
   *
   * @param n ngram length.
   * @return total number of distinct ngrams of length n. This value is in [0, #ngrams].
   */
  default int numberOfDistinctNGrams(int n) {
    return bagOfNGrams(n).elementSet().size();
  }

  /**
   * Returns the number of occurrences of a ngram.
   *
   * @param words sequence of words.
   * @return number of occurrences. This value is in [0, #ngrams].
   */
  default int frequency(String... words) {

    Preconditions.checkNotNull(words, "words should not be null");

    return bagOfNGrams().count(Lists.newArrayList(words));
  }

  /**
   * Returns the normalized frequency of a ngram i.e the frequency of a ngram divided by the total
   * number of ngrams of the same length.
   *
   * @param words sequence of words.
   * @return normalized frequency. This value is in [0, 1].
   */
  default double normalizedFrequency(String... words) {

    Preconditions.checkNotNull(words, "words should not be null");

    return (double) frequency(words) / (double) numberOfNGrams(words.length);
  }

  /**
   * Merge multiple bags of ngrams into a single one.
   *
   * @param bags bags of ngrams.
   * @return bag of ngrams.
   */
  default Multiset<List<String>> mergeWith(IBagOfNGrams... bags) {

    Preconditions.checkNotNull(bags, "bags should not be null");

    Multiset<List<String>> bag = HashMultiset.create(bagOfNGrams());
    Arrays.asList(bags).forEach(b -> bag.addAll(b.bagOfNGrams()));
    return bag;
  }

  /**
   * Find the most probable word following a given sequence of words.
   *
   * @param words sequence of words.
   * @return most probable next word.
   */
  default String mostProbableNextWord(String... words) {

    Preconditions.checkNotNull(words, "words should not be null");

    Multiset<List<String>> bag = bagOfNGrams(words.length + 1);
    Set<List<String>> ngrams = bag.elementSet().stream().filter(ngram -> {
      for (int i = 0; i < words.length; i++) {
        if (!words[i].equals(ngram.get(i))) {
          return false;
        }
      }
      return true;
    }).collect(Collectors.toSet());

    if (ngrams.isEmpty()) {
      return "";
    }
    if (ngrams.size() == 1) {
      return Strings.nullToEmpty(Iterables.get(ngrams, 0).get(words.length));
    }

    @Var
    int sum = 0;
    int rnd = RNG.nextInt(ngrams.stream().mapToInt(ngram -> bag.count(ngram)).sum());

    for (List<String> ngram : ngrams) {

      sum += bag.count(ngram);

      if (rnd < sum) {
        return ngram.get(words.length);
      }
    }
    return "";
  }

  final class SimpleBagOfNGrams implements IBagOfNGrams {

    private final Multiset<List<String>> bag_;

    public SimpleBagOfNGrams(Multiset<List<String>> bag) {
      bag_ = Preconditions.checkNotNull(bag, "bag should not be null");
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == null) {
        return false;
      }
      if (!(obj instanceof SimpleBagOfNGrams)) {
        return false;
      }
      SimpleBagOfNGrams other = (SimpleBagOfNGrams) obj;
      return Objects.equal(bag_, other.bag_);
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(bag_);
    }

    @Override
    public Multiset<List<String>> bagOfNGrams() {
      return bag_;
    }
  }
}
