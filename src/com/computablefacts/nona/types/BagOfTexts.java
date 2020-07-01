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

  private Map<String, Set<Text>> index_;

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
  public int numberOfDistinctTextsOccurrences(String word) {

    Preconditions.checkNotNull(word, "word should not be null");

    return index(false).get(word).size();
  }

  @Override
  public int numberOfDistinctTextsOccurrences(String word1, String word2) {

    Preconditions.checkNotNull(word1, "word1 should not be null");
    Preconditions.checkNotNull(word2, "word2 should not be null");

    Set<Text> set1 = index(false).getOrDefault(word1, new HashSet<>());
    Set<Text> set2 = index(false).getOrDefault(word2, new HashSet<>());

    int size1 = set1.size();
    int size2 = set2.size();

    if (size1 < size2) {
      return set1.stream().mapToInt(text -> text.frequency(word1, word2) > 0 ? 1 : 0).sum();
    }
    return set2.stream().mapToInt(text -> text.frequency(word1, word2) > 0 ? 1 : 0).sum();
  }

  @Override
  public List<Text> find(Set<String> words, int limit, BiFunction<Text, Set<String>, Double> rank) {

    Preconditions.checkNotNull(words, "words should not be null");
    Preconditions.checkArgument(limit > 0, "limit must be > 0");
    Preconditions.checkNotNull(rank, "rank should not be null");

    @Var
    Set<Text> set = null;

    for (String word : words) {
      if (set == null) {
        set = index(false).getOrDefault(word, new HashSet<>());
      } else {
        set = Sets.union(set, index(false).getOrDefault(word, new HashSet<>()));
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
   * @param forceRebuild if an index has already been built and cached, force a rebuild.
   * @return index.
   */
  public Map<String, Set<Text>> index(boolean forceRebuild) {
    if (index_ == null || forceRebuild) {
      index_ = new HashMap<>();
      texts_.elementSet().forEach(text -> {
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

  /**
   * Add a new document to the bag.
   *
   * @param text document.
   */
  public void add(String text) {

    Preconditions.checkNotNull(text, "text should not be null");

    texts_.add(new Text(text, sentenceSplitter_, wordSplitter_, wordsSeen_));
  }
}
