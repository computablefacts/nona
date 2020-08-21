package com.computablefacts.nona.helpers;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multiset;
import com.google.errorprone.annotations.Var;

public interface IBagOfBigrams {

  Random RNG = new Random();

  static IBagOfBigrams wrap(Multiset<Map.Entry<String, String>> bag) {
    return new SimpleBagOfBigrams(bag);
  }

  /**
   * Returns a bag of bigrams.
   *
   * @return a bag of bigrams.
   */
  Multiset<Map.Entry<String, String>> bagOfBigrams();

  /**
   * Freeze the bag. From now on, it won't be possible to add new bigrams to it. Useful when
   * {@link #bagOfBigrams()} requires heavy computations.
   *
   * @return frozen bag.
   */
  default IBagOfBigrams freezeBagOfBigrams() {
    return wrap(bagOfBigrams());
  }

  /**
   * Returns unique bigrams.
   *
   * @return bigrams.
   */
  default Set<Map.Entry<String, String>> uniqueBigrams() {
    return ImmutableSet.copyOf(bagOfBigrams().elementSet());
  }

  /**
   * Returns the total number of bigrams, including duplicates.
   *
   * @return total number of bigrams.
   */
  default int numberOfBigrams() {
    return bagOfBigrams().size();
  }

  /**
   * Returns the total number of distinct bigrams, excluding duplicates.
   *
   * @return total number of distinct bigrams. This value is in [0, #bigrams].
   */
  default int numberOfDistinctBigrams() {
    return bagOfBigrams().elementSet().size();
  }

  /**
   * Returns the number of occurrences of a bigram.
   *
   * @param word1 first word of the bigram.
   * @param word2 second word of the bigram.
   * @return number of occurrences. This value is in [0, #bigrams].
   */
  default int frequency(String word1, String word2) {

    Preconditions.checkNotNull(word1, "word1 should not be null");
    Preconditions.checkNotNull(word2, "word2 should not be null");

    return bagOfBigrams().count(new AbstractMap.SimpleEntry<>(word1, word2));
  }

  /**
   * Returns the normalized frequency of a bigram i.e the frequency of a bigram divided by the total
   * number of bigrams.
   *
   * @param word1 first word of the bigram.
   * @param word2 second word of the bigram.
   * @return normalized frequency. This value is in [0, 1].
   */
  default double normalizedFrequency(String word1, String word2) {
    return (double) frequency(word1, word2) / (double) numberOfBigrams();
  }

  /**
   * Merge multiple bags of bigrams into a single one.
   *
   * @param bags bags of bigrams.
   * @return bag of bigrams.
   */
  default Multiset<Map.Entry<String, String>> mergeWith(IBagOfBigrams... bags) {

    Multiset<Map.Entry<String, String>> bag = HashMultiset.create(bagOfBigrams());
    Arrays.asList(bags).forEach(b -> bag.addAll(b.bagOfBigrams()));
    return bag;
  }

  /**
   * Find the most probable word following a given word.
   *
   * @param word word.
   * @return most probable next word.
   */
  default String mostProbableNextWord(String word) {

    Preconditions.checkNotNull(word, "word should not be null");

    Multiset<Map.Entry<String, String>> bag = bagOfBigrams();
    Set<Map.Entry<String, String>> bigrams = bag.elementSet().stream()
        .filter(bigram -> word.equals(bigram.getKey())).collect(Collectors.toSet());

    if (bigrams.isEmpty()) {
      return "";
    }
    if (bigrams.size() == 1) {
      return Strings.nullToEmpty(Iterables.get(bigrams, 0).getValue());
    }

    @Var
    int sum = 0;
    int rnd = RNG.nextInt(bigrams.stream().mapToInt(bigram -> bag.count(bigram)).sum());

    for (Map.Entry<String, String> bigram : bigrams) {

      sum += bag.count(bigram);

      if (rnd < sum) {
        return bigram.getValue();
      }
    }
    return "";
  }

  final class SimpleBagOfBigrams implements IBagOfBigrams {

    private final Multiset<Map.Entry<String, String>> bag_;

    public SimpleBagOfBigrams(Multiset<Map.Entry<String, String>> bag) {
      bag_ = Preconditions.checkNotNull(bag, "bag should not be null");
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == null) {
        return false;
      }
      if (!(obj instanceof SimpleBagOfBigrams)) {
        return false;
      }
      SimpleBagOfBigrams other = (SimpleBagOfBigrams) obj;
      return Objects.equal(bag_, other.bag_);
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(bag_);
    }

    @Override
    public Multiset<Map.Entry<String, String>> bagOfBigrams() {
      return bag_;
    }
  }
}
