package com.computablefacts.nona.helpers;

import java.util.Arrays;
import java.util.Set;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multiset;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
public interface IBagOfWords {

  static IBagOfWords wrap(Multiset<String> bag) {
    return new SimpleBagOfWords(bag);
  }

  /**
   * Returns a bag of words.
   *
   * @return a bag of words.
   */
  Multiset<String> bagOfWords();

  /**
   * Freeze the bag. From now on, it won't be possible to add new words to it. Useful when
   * {@link #bagOfWords()} requires heavy computations.
   *
   * @return frozen bag.
   */
  default IBagOfWords freezeBagOfWords() {
    return wrap(bagOfWords());
  }

  /**
   * Returns unique words.
   *
   * @return words.
   */
  default Set<String> uniqueWords() {
    return ImmutableSet.copyOf(bagOfWords().elementSet());
  }

  /**
   * Returns the total number of words, including duplicates.
   *
   * @return total number of words.
   */
  default int numberOfWords() {
    return bagOfWords().size();
  }

  /**
   * Returns the total number of distinct words, excluding duplicates.
   *
   * @return total number of distinct words. This value is in [0, #words].
   */
  default int numberOfDistinctWords() {
    return bagOfWords().elementSet().size();
  }

  /**
   * Returns the number of occurrences of a word.
   *
   * @param word word.
   * @return number of occurrences. This value is in [0, #words].
   */
  default int frequency(String word) {

    Preconditions.checkNotNull(word, "word should not be null");

    return bagOfWords().count(word);
  }

  /**
   * Returns the normalized frequency of a word i.e the frequency of a word divided by the total
   * number of words.
   *
   * @param word word.
   * @return normalized frequency. This value is in [0, 1].
   */
  default double normalizedFrequency(String word) {
    return (double) frequency(word) / (double) numberOfWords();
  }

  /**
   * Merge multiple bags of words into a single one.
   *
   * @param bags bags of words.
   * @return bag of words.
   */
  default Multiset<String> mergeWith(IBagOfWords... bags) {

    Multiset<String> bag = HashMultiset.create(bagOfWords());
    Arrays.asList(bags).forEach(b -> bag.addAll(b.bagOfWords()));
    return bag;
  }

  final class SimpleBagOfWords implements IBagOfWords {

    private final Multiset<String> bag_;

    public SimpleBagOfWords(Multiset<String> bag) {
      bag_ = Preconditions.checkNotNull(bag, "bag should not be null");
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == null) {
        return false;
      }
      if (!(obj instanceof SimpleBagOfWords)) {
        return false;
      }
      SimpleBagOfWords other = (SimpleBagOfWords) obj;
      return Objects.equal(bag_, other.bag_);
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(bag_);
    }

    @Override
    public Multiset<String> bagOfWords() {
      return bag_;
    }
  }
}
