package com.computablefacts.nona.helpers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
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
        && com.google.common.base.Objects.equal(bagOfNGrams(), other.bagOfNGrams());
  }

  @Override
  public int hashCode() {
    return com.google.common.base.Objects.hashCode(texts_, sentenceSplitter_, wordSplitter_,
        bagOfWords(), bagOfNGrams());
  }

  @Override
  public Multiset<Text> bagOfTexts() {
    return texts_;
  }

  @Override
  public IBagOfTexts freezeBagOfTexts() {

    Multiset<String> bagOfWords = HashMultiset.create();
    Multiset<List<String>> bagOfNGrams = HashMultiset.create();

    bagOfTexts().elementSet().forEach(bag -> {
      bagOfWords.addAll(bag.bagOfWords());
      bagOfNGrams.addAll(bag.bagOfNGrams());
    });
    return IBagOfTexts.wrap(bagOfTexts(), bagOfWords, bagOfNGrams);
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
