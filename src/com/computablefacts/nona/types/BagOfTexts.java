package com.computablefacts.nona.types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.computablefacts.nona.functions.utils.IBagOfTexts;
import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;
import com.google.errorprone.annotations.Var;

final public class BagOfTexts implements IBagOfTexts {

  private final Multiset<Text> texts_ = HashMultiset.create();
  private final Set<String> wordsSeen_;

  private final Function<String, List<String>> sentenceSplitter_;
  private final Function<String, List<String>> wordSplitter_;

  public BagOfTexts(Function<String, List<String>> sentenceSplitter,
      Function<String, List<String>> wordSplitter) {
    this(sentenceSplitter, wordSplitter, false);
  }

  public BagOfTexts(Function<String, List<String>> sentenceSplitter,
      Function<String, List<String>> wordSplitter, boolean takeUnknownWordsIntoAccount) {

    Preconditions.checkNotNull(sentenceSplitter, "sentenceSplitter should not be null");
    Preconditions.checkNotNull(wordSplitter, "wordSplitter should not be null");

    sentenceSplitter_ = sentenceSplitter;
    wordSplitter_ = wordSplitter;
    wordsSeen_ = takeUnknownWordsIntoAccount ? new HashSet<>() : null;
  }

  public static IBagOfTexts wrap(Multiset<Text> bagOfTexts, Multiset<String> bagOfWords,
      Multiset<Map.Entry<String, String>> bagOfBigrams) {
    return new BagOfTexts.SimpleBagOfTexts(bagOfTexts, bagOfWords, bagOfBigrams);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof BagOfTexts)) {
      return false;
    }
    BagOfTexts other = (BagOfTexts) obj;
    return com.google.common.base.Objects.equal(texts_, other.texts_)
        && com.google.common.base.Objects.equal(sentenceSplitter_, other.sentenceSplitter_)
        && com.google.common.base.Objects.equal(wordSplitter_, other.wordSplitter_)
        && com.google.common.base.Objects.equal(bagOfWords(), other.bagOfWords())
        && com.google.common.base.Objects.equal(bagOfBigrams(), other.bagOfBigrams());
  }

  @Override
  public int hashCode() {
    return com.google.common.base.Objects.hashCode(texts_, sentenceSplitter_, wordSplitter_,
        bagOfWords(), bagOfBigrams());
  }

  @Override
  public Multiset<Text> bagOfTexts() {
    return texts_;
  }

  @Override
  public IBagOfTexts freezeBagOfTexts() {

    Multiset<String> bagOfWords = HashMultiset.create();
    Multiset<Map.Entry<String, String>> bagOfBigrams = HashMultiset.create();

    bagOfTexts().elementSet().forEach(bag -> {
      bagOfWords.addAll(bag.bagOfWords());
      bagOfBigrams.addAll(bag.bagOfBigrams());
    });
    return wrap(bagOfTexts(), bagOfWords, bagOfBigrams);
  }

  /**
   * Add a new document to the bag.
   *
   * @param text document.
   */
  public void add(String text) {

    Preconditions.checkNotNull(text, "text should not be null");

    texts_.add(new Text(text, sentenceSplitter_, wordSplitter_, wordsSeen_));
  }

  final static class SimpleBagOfTexts implements IBagOfTexts {

    private final Multiset<Text> bagOfTexts_;
    private final Multiset<String> bagOfWords_;
    private final Multiset<Map.Entry<String, String>> bagOfBigrams_;

    private Map<String, Set<Text>> index_;

    public SimpleBagOfTexts(Multiset<Text> bagOfTexts, Multiset<String> bagOfWords,
        Multiset<Map.Entry<String, String>> bagOfBigrams) {
      bagOfTexts_ = Preconditions.checkNotNull(bagOfTexts, "bagOfTexts should not be null");
      bagOfWords_ = Preconditions.checkNotNull(bagOfWords, "bagOfWords should not be null");
      bagOfBigrams_ = Preconditions.checkNotNull(bagOfBigrams, "bagOfBigrams should not be null");
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == null) {
        return false;
      }
      if (!(obj instanceof com.computablefacts.nona.types.BagOfTexts.SimpleBagOfTexts)) {
        return false;
      }
      com.computablefacts.nona.types.BagOfTexts.SimpleBagOfTexts other =
          (com.computablefacts.nona.types.BagOfTexts.SimpleBagOfTexts) obj;
      return com.google.common.base.Objects.equal(bagOfTexts_, other.bagOfTexts_)
          && com.google.common.base.Objects.equal(bagOfWords_, other.bagOfWords_)
          && com.google.common.base.Objects.equal(bagOfBigrams_, other.bagOfBigrams_);
    }

    @Override
    public int hashCode() {
      return com.google.common.base.Objects.hashCode(bagOfTexts_, bagOfWords_, bagOfBigrams_);
    }

    @Override
    public Multiset<Text> bagOfTexts() {
      return bagOfTexts_;
    }

    @Override
    public Multiset<String> bagOfWords() {
      return bagOfWords_;
    }

    @Override
    public Multiset<Map.Entry<String, String>> bagOfBigrams() {
      return bagOfBigrams_;
    }

    @Override
    public int numberOfDistinctTextsOccurrences(String word) {

      Preconditions.checkNotNull(word, "word should not be null");

      if (index().containsKey(word)) {
        return index().get(word).size();
      }
      return 0;
    }

    @Override
    public int numberOfDistinctTextsOccurrences(String word1, String word2) {

      Preconditions.checkNotNull(word1, "word1 should not be null");
      Preconditions.checkNotNull(word2, "word2 should not be null");

      Set<Text> set1 = index().getOrDefault(word1, new HashSet<>());
      Set<Text> set2 = index().getOrDefault(word2, new HashSet<>());

      int size1 = set1.size();
      int size2 = set2.size();

      if (size1 < size2) {
        return set1.stream().mapToInt(text -> text.frequency(word1, word2) > 0 ? 1 : 0).sum();
      }
      return set2.stream().mapToInt(text -> text.frequency(word1, word2) > 0 ? 1 : 0).sum();
    }

    @Override
    public List<Text> find(Set<String> words, int limit,
        BiFunction<Text, Set<String>, Double> rank) {

      Preconditions.checkNotNull(words, "words should not be null");
      Preconditions.checkArgument(limit > 0, "limit must be > 0");
      Preconditions.checkNotNull(rank, "rank should not be null");

      @Var
      Set<Text> set = null;

      for (String word : words) {
        if (set == null) {
          set = index().getOrDefault(word, new HashSet<>());
        } else {
          set = Sets.union(set, index().getOrDefault(word, new HashSet<>()));
        }
      }

      if (set == null) {
        return new ArrayList<>();
      }
      return set.stream()
          .sorted(
              Collections.reverseOrder(Comparator.comparingDouble(text -> rank.apply(text, words))))
          .limit(limit).collect(Collectors.toList());
    }

    /**
     * Build and cache an inverted index.
     *
     * @return index.
     */
    private Map<String, Set<Text>> index() {
      if (index_ == null) {
        index_ = new HashMap<>();
        bagOfTexts_.elementSet().forEach(text -> {
          for (String word : text.bagOfWords()) {
            if (!index_.containsKey(word)) {
              index_.put(word, new HashSet<>());
            }
            index_.get(word).add(text);
          }
        });
      }
      return index_;
    }
  }
}
