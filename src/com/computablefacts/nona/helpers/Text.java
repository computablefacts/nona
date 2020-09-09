package com.computablefacts.nona.helpers;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.graph.MutableNetwork;
import com.google.common.graph.NetworkBuilder;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
final public class Text implements IBagOfWords, IBagOfBigrams {

  private final static String UNKNOWN = "<UNK>";
  private final static HashFunction GOOD_FAST_HASH_128 = Hashing.goodFastHash(128);

  private final String text_;
  private final String hash_;
  private final Function<String, List<String>> sentenceSplitter_;
  private final Function<String, List<String>> wordSplitter_;
  private final MutableNetwork<String, String> graph_ =
      NetworkBuilder.directed().allowsParallelEdges(true).allowsSelfLoops(true)
          .expectedNodeCount(1000).expectedEdgeCount(1000).build();

  private Multiset<String> unigrams_ = null;
  private Multiset<Map.Entry<String, String>> bigrams_ = null;

  public Text(String text, Function<String, List<String>> sentenceSplitter,
      Function<String, List<String>> wordSplitter) {
    this(text, sentenceSplitter, wordSplitter, null);
  }

  public Text(String text, Function<String, List<String>> sentenceSplitter,
      Function<String, List<String>> wordSplitter, Set<String> wordsSeen) {

    Preconditions.checkNotNull(text, "text should not be null");
    Preconditions.checkNotNull(sentenceSplitter, "sentenceSplitter should not be null");
    Preconditions.checkNotNull(wordSplitter, "wordSplitter should not be null");

    text_ = text;
    sentenceSplitter_ = sentenceSplitter;
    wordSplitter_ = wordSplitter;
    hash_ = GOOD_FAST_HASH_128.newHasher().putUnencodedChars(text).hash().toString();

    List<String> sentences = sentenceSplitter.apply(text_);

    for (int i = 0; i < sentences.size(); i++) {

      String sentence = sentences.get(i);
      List<String> words = wordSplitter.apply(sentence);
      List<String> wordsCopy;

      if (wordsSeen == null) {
        wordsCopy = words;
      } else { // An attempt to deal with the unknown words problem

        wordsCopy = new ArrayList<>(words.size());

        for (int j = 0; j < words.size(); j++) {

          String word = words.get(j);

          if (wordsSeen.contains(word)) {
            wordsCopy.add(word);
          } else {
            wordsCopy.add(UNKNOWN);
            wordsSeen.add(word);
          }
        }
      }

      if (!wordsCopy.isEmpty()) {

        for (int j = 0; j < wordsCopy.size() - 1; j++) {

          String word1 = wordsCopy.get(j);
          String word2 = wordsCopy.get(j + 1);

          graph_.addEdge(word1, word2, i + "-" + j);
        }

        String word = wordsCopy.get(wordsCopy.size() - 1);

        graph_.addNode(word);
      }
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Text other = (Text) obj;
    return Objects.equal(text_, other.text_) && Objects.equal(hash_, other.hash_)
        && Objects.equal(sentenceSplitter_, other.sentenceSplitter_)
        && Objects.equal(wordSplitter_, other.wordSplitter_);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(text_, hash_, sentenceSplitter_, wordSplitter_);
  }

  @Override
  public Multiset<String> bagOfWords() {
    if (unigrams_ == null) {
      unigrams_ = HashMultiset.create();
      graph_.nodes().forEach(word -> unigrams_.add(word,
          Math.max(1, Math.max(graph_.inDegree(word), graph_.outDegree(word)))));
    }
    return HashMultiset.create(unigrams_);
  }

  @Override
  public Multiset<Map.Entry<String, String>> bagOfBigrams() {
    if (bigrams_ == null) {
      bigrams_ = HashMultiset.create();
      graph_.nodes().stream()
          .flatMap(word -> graph_.successors(word).stream()
              .map(nextWord -> new AbstractMap.SimpleEntry<>(word, nextWord)))
          .forEach(bigram -> bigrams_.add(bigram,
              graph_.edgesConnecting(bigram.getKey(), bigram.getValue()).size()));
    }
    return HashMultiset.create(bigrams_);
  }

  /**
   * Returns the raw text.
   *
   * @return text.
   */
  public String text() {
    return text_;
  }

  /**
   * Returns the text hash.
   *
   * @return hash.
   */
  public String hash() {
    return hash_;
  }

  /**
   * Returns the text to sentences tokenizer.
   *
   * @return tokenizer.
   */
  public Function<String, List<String>> sentenceSplitter() {
    return sentenceSplitter_;
  }

  /**
   * Returns the sentence to words tokenizer.
   *
   * @return tokenizer.
   */
  public Function<String, List<String>> wordSplitter() {
    return wordSplitter_;
  }
}
