package com.computablefacts.nona.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Var;

@CheckReturnValue
public interface IBagOfTexts extends IBagOfWords, IBagOfNGrams {

  double K1 = 1.2; // in [1.2, 2.0]
  double B = 0.75;

  static IBagOfTexts wrap(Multiset<Text> bagOfTexts, Multiset<String> bagOfWords,
      Multiset<List<String>> bagOfNGrams) {
    return new SimpleBagOfTexts(bagOfTexts, bagOfWords, bagOfNGrams);
  }

  /**
   * Compute likelihood ratio.
   *
   * See https://nlp.stanford.edu/fsnlp/promo/colloc.pdf section 5.3.4 for details.
   *
   * @param bagOfWords bag of words.
   * @param bagOfNGrams bag of ngrams.
   * @param word1 first word of the bigram.
   * @param word2 second word of the bigram.
   * @return likelihood ratio.
   */
  static double likelihoodRatio(IBagOfWords bagOfWords, IBagOfNGrams bagOfNGrams, String word1,
      String word2) {

    Preconditions.checkNotNull(bagOfWords, "bagOfWords should not be null");
    Preconditions.checkNotNull(bagOfNGrams, "bagOfNGrams should not be null");
    Preconditions.checkNotNull(word1, "word1 should not be null");
    Preconditions.checkNotNull(word2, "word2 should not be null");

    if (bagOfWords.frequency(word1) == 0) {
      return Double.NaN;
    }
    if (bagOfWords.frequency(word2) == 0) {
      return Double.NaN;
    }

    int c1 = bagOfWords.frequency(word1);
    int c2 = bagOfWords.frequency(word2);
    int c12 = bagOfNGrams.frequency(word1, word2);
    long n = bagOfWords.numberOfWords();

    return likelihoodRatio(c1, c2, c12, n);
  }

  /**
   * Returns the likelihood ratio test statistic -2 log &lambda;
   *
   * @param c1 the number of occurrences of w1.
   * @param c2 the number of occurrences of w2.
   * @param c12 the number of occurrences of w1 w2.
   * @param N the number of tokens in the corpus.
   */
  static double likelihoodRatio(int c1, int c2, int c12, long N) {

    double p = (double) c2 / N;
    double p1 = (double) c12 / c1;
    double p2 = (double) (c2 - c12) / (N - c1);

    double logLambda = logL(c12, c1, p) + logL(c2 - c12, N - c1, p) - logL(c12, c1, p1)
        - logL(c2 - c12, N - c1, p2);
    return -2 * logLambda;
  }

  /**
   * Help function for calculating likelihood ratio statistic.
   */
  static double logL(int k, long n, @Var double x) {
    if (x == 0.0) {
      x = 0.01;
    }
    if (x == 1.0) {
      x = 0.99;
    }
    return k * Math.log(x) + (n - k) * Math.log(1 - x);
  }

  @Override
  default Multiset<String> bagOfWords() {
    Multiset<String> bag = HashMultiset.create();
    bagOfTexts().elementSet().stream().map(Text::bagOfWords).forEach(bag::addAll);
    return bag;
  }

  @Override
  default Multiset<List<String>> bagOfNGrams() {
    Multiset<List<String>> bag = HashMultiset.create();
    bagOfTexts().elementSet().stream().map(Text::bagOfNGrams).forEach(bag::addAll);
    return bag;
  }

  /**
   * Returns a bag of texts.
   *
   * @return a bag of texts.
   */
  Multiset<Text> bagOfTexts();

  /**
   * Freeze the bag. From now on, it won't be possible to add new texts to it. Useful when
   * {@link #bagOfTexts()} requires heavy computations.
   *
   * @return frozen bag.
   */
  default IBagOfTexts freezeBagOfTexts() {

    Multiset<String> bagOfWords = HashMultiset.create();
    Multiset<List<String>> bagOfNGrams = HashMultiset.create();

    bagOfTexts().elementSet().forEach(bag -> {
      bagOfWords.addAll(bag.bagOfWords());
      bagOfNGrams.addAll(bag.bagOfNGrams());
    });
    return wrap(bagOfTexts(), bagOfWords, bagOfNGrams);
  }

  /**
   * Returns the document associated to a given text.
   *
   * @return document or null.
   */
  default Text text(String text) {

    Preconditions.checkNotNull(text, "text should not be null");

    return bagOfTexts().elementSet().stream().filter(entry -> text.equals(entry.text())).findFirst()
        .orElse(null);
  }

  /**
   * Returns unique texts.
   *
   * @return texts.
   */
  default Set<Text> uniqueTexts() {
    return ImmutableSet.copyOf(bagOfTexts().elementSet());
  }

  /**
   * Returns the number of documents, including duplicates.
   *
   * @return the number of documents.
   */
  default int numberOfTexts() {
    return bagOfTexts().size();
  }

  /**
   * Returns the number of distinct documents, excluding duplicates.
   *
   * @return the number of distinct documents. This value is in [0, #documents].
   */
  default int numberOfDistinctTexts() {
    return bagOfTexts().elementSet().size();
  }

  /**
   * Returns the average document length of the collection.
   *
   * @return average document length i.e. number of words
   */
  default double averageTextLength() {
    return bagOfTexts().entrySet().stream()
        .mapToDouble(text -> (double) text.getElement().numberOfWords()).average().orElse(0.0);
  }

  /**
   * Returns the number of distinct documents containing a given word.
   *
   * @param word word.
   * @return the number of documents.
   */
  default int numberOfDistinctTextsOccurrences(String word) {

    Preconditions.checkNotNull(word, "word should not be null");

    return bagOfTexts().entrySet().stream()
        .mapToInt(text -> text.getElement().frequency(word) > 0 ? 1 : 0).sum();
  }

  /**
   * Returns the number of distinct documents containing a given bigram.
   *
   * @param word1 first word of the bigram.
   * @param word2 second word of the bigram.
   * @return the number of documents.
   */
  default int numberOfDistinctTextsOccurrences(String word1, String word2) {

    Preconditions.checkNotNull(word1, "word1 should not be null");
    Preconditions.checkNotNull(word2, "word2 should not be null");

    return bagOfTexts().entrySet().stream()
        .mapToInt(text -> text.getElement().frequency(word1, word2) > 0 ? 1 : 0).sum();
  }

  /**
   * Returns the number of distinct documents containing a given trigram.
   *
   * @param word1 first word of the trigram.
   * @param word2 second word of the trigram.
   * @param word3 second word of the trigram.
   * @return the number of documents.
   */
  default int numberOfDistinctTextsOccurrences(String word1, String word2, String word3) {

    Preconditions.checkNotNull(word1, "word1 should not be null");
    Preconditions.checkNotNull(word2, "word2 should not be null");
    Preconditions.checkNotNull(word3, "word3 should not be null");

    return bagOfTexts().entrySet().stream()
        .mapToInt(text -> text.getElement().frequency(word1, word2, word3) > 0 ? 1 : 0).sum();
  }

  /**
   * Computes "document frequency" which measures how common a word is among all documents.
   *
   * @param word word.
   * @return document frequency.
   */
  default double documentFrequency(String word) {
    return (double) numberOfDistinctTextsOccurrences(word) / (double) numberOfDistinctTexts();
  }

  /**
   * Computes "document frequency" which measures how common a bigram is among all documents.
   *
   * @param word1 first word of the bigram.
   * @param word2 second word of the bigram.
   * @return document frequency.
   */
  default double documentFrequency(String word1, String word2) {
    return (double) numberOfDistinctTextsOccurrences(word1, word2)
        / (double) numberOfDistinctTexts();
  }

  /**
   * Computes "document frequency" which measures how common a trigram is among all documents.
   *
   * @param word1 first word of the trigram.
   * @param word2 second word of the trigram.
   * @param word3 third word of the trigram.
   * @return document frequency.
   */
  default double documentFrequency(String word1, String word2, String word3) {
    return (double) numberOfDistinctTextsOccurrences(word1, word2, word3)
        / (double) numberOfDistinctTexts();
  }

  /**
   * Computes "term frequency" which is the number of times a word appears in a given document.
   *
   * @param text document.
   * @param word word.
   * @return term frequency.
   */
  default double termFrequency(Text text, String word) {

    Preconditions.checkNotNull(text, "text should not be null");
    Preconditions.checkNotNull(word, "word should not be null");

    return (double) text.frequency(word) / (double) text.numberOfWords();
  }

  /**
   * Computes "term frequency" which is the number of times a bigram appears in a given document.
   *
   * @param text document.
   * @param word1 first word of the bigram.
   * @param word2 second word of the bigram.
   * @return term frequency.
   */
  default double termFrequency(Text text, String word1, String word2) {

    Preconditions.checkNotNull(text, "text should not be null");
    Preconditions.checkNotNull(word1, "word1 should not be null");
    Preconditions.checkNotNull(word2, "word2 should not be null");

    return (double) text.frequency(word1, word2) / (double) text.numberOfNGrams(2);
  }

  /**
   * Computes "term frequency" which is the number of times a trigram appears in a given document.
   *
   * @param text document.
   * @param word1 first word of the trigram.
   * @param word2 second word of the trigram.
   * @param word3 third word of the trigram.
   * @return term frequency.
   */
  default double termFrequency(Text text, String word1, String word2, String word3) {

    Preconditions.checkNotNull(text, "text should not be null");
    Preconditions.checkNotNull(word1, "word1 should not be null");
    Preconditions.checkNotNull(word2, "word2 should not be null");

    return (double) text.frequency(word1, word2, word3) / (double) text.numberOfNGrams(3);
  }

  /**
   * Computes "inverse document frequency" which measures how common a word is among all documents.
   *
   * @param word word.
   * @return inverse document frequency.
   */
  default double inverseDocumentFrequency(String word) {
    return 1 + Math.log(
        (double) numberOfDistinctTexts() / (double) (1 + numberOfDistinctTextsOccurrences(word)));
  }

  /**
   * Computes "inverse document frequency" which measures how common a bigram is among all
   * documents.
   *
   * @param word1 first word of the bigram.
   * @param word2 second word of the bigram.
   * @return inverse document frequency.
   */
  default double inverseDocumentFrequency(String word1, String word2) {
    return 1 + Math.log((double) numberOfDistinctTexts()
        / (double) (1 + numberOfDistinctTextsOccurrences(word1, word2)));
  }

  /**
   * Computes "inverse document frequency" which measures how common a trigram is among all
   * documents.
   *
   * @param word1 first word of the trigram.
   * @param word2 second word of the trigram.
   * @param word3 third word of the trigram.
   * @return inverse document frequency.
   */
  default double inverseDocumentFrequency(String word1, String word2, String word3) {
    return 1 + Math.log((double) numberOfDistinctTexts()
        / (double) (1 + numberOfDistinctTextsOccurrences(word1, word2, word3)));
  }

  /**
   * Computes the TF-IDF score of a document. It's the product of tf and idf.
   *
   * @param text text.
   * @param word word.
   * @return TF-IDF.
   */
  default double tfIdf(Text text, String word) {

    Preconditions.checkNotNull(text, "text should not be null");
    Preconditions.checkNotNull(word, "word should not be null");

    double tf = termFrequency(text, word);
    double idf = inverseDocumentFrequency(word);

    return tf * idf;
  }

  /**
   * Computes the TF-IDF score of a document. It's the product of tf and idf.
   *
   * @param text text.
   * @param word1 first word of the bigram.
   * @param word2 second word of the bigram.
   * @return TF-IDF.
   */
  default double tfIdf(Text text, String word1, String word2) {

    Preconditions.checkNotNull(text, "text should not be null");
    Preconditions.checkNotNull(word1, "word1 should not be null");
    Preconditions.checkNotNull(word2, "word2 should not be null");

    double tf = termFrequency(text, word1, word2);
    double idf = inverseDocumentFrequency(word1, word2);

    return tf * idf;
  }

  /**
   * Computes the TF-IDF score of a document. It's the product of tf and idf.
   *
   * @param text text.
   * @param word1 first word of the trigram.
   * @param word2 second word of the trigram.
   * @param word3 thirs word of the trigram.
   * @return TF-IDF.
   */
  default double tfIdf(Text text, String word1, String word2, String word3) {

    Preconditions.checkNotNull(text, "text should not be null");
    Preconditions.checkNotNull(word1, "word1 should not be null");
    Preconditions.checkNotNull(word2, "word2 should not be null");
    Preconditions.checkNotNull(word3, "word3 should not be null");

    double tf = termFrequency(text, word1, word2, word3);
    double idf = inverseDocumentFrequency(word1, word2, word3);

    return tf * idf;
  }

  /**
   * Find texts matching a given list of words. To qualify, a text must contain at least 2/3 of the
   * given words. The default ranking function is BM25.
   *
   * @param words words to match.
   * @param limit max number of texts to return.
   * @return matched texts.
   */
  default List<Text> find(Set<String> words, int limit) {

    Preconditions.checkNotNull(words, "words should not be null");
    Preconditions.checkArgument(limit > 0, "limit must be > 0");

    return find(words, limit, this::bm25);
  }

  /**
   * Find texts matching a given list of words.
   *
   * @param words words to match.
   * @param limit max number of texts to return.
   * @param rank ranking function. The function takes as first argument a text and as second
   *        argument the list of words to match. The higher the score the more relevant.
   * @return matched texts.
   */
  default List<Text> find(Set<String> words, int limit,
      BiFunction<Text, Set<String>, Double> rank) {

    Preconditions.checkNotNull(words, "words should not be null");
    Preconditions.checkArgument(limit > 0, "limit must be > 0");
    Preconditions.checkNotNull(rank, "rank should not be null");

    return bagOfTexts().entrySet().stream()
        .filter(entry -> words.stream().anyMatch(word -> entry.getElement().frequency(word) > 0))
        .sorted(Collections.reverseOrder(
            Comparator.comparingDouble(entry -> rank.apply(entry.getElement(), words))))
        .limit(limit).map(entry -> entry.getElement()).collect(Collectors.toList());
  }

  /**
   * BM25 (BM is an abbreviation of best matching) is a ranking function used by search engines to
   * estimate the relevance of documents to a given search query.
   *
   * @param text text.
   * @param words multi-words query.
   * @return score. The higher the more relevant.
   */
  default double bm25(Text text, Set<String> words) {

    Preconditions.checkNotNull(text, "text should not be null");
    Preconditions.checkNotNull(words, "words should not be null");

    return words.stream().mapToDouble(
        word -> bm25InverseDocumentFrequency(text, word) * bm25TermFrequency(text, word)).sum();
  }

  default double bm25TermFrequency(Text text, String word) {

    Preconditions.checkNotNull(text, "text should not be null");
    Preconditions.checkNotNull(word, "word should not be null");

    double tf = termFrequency(text, word);

    return (tf * (K1 + 1.0))
        / (tf + K1 * (1.0 - B + B * (text.numberOfWords() / averageTextLength())));
  }

  default double bm25InverseDocumentFrequency(Text text, String word) {

    Preconditions.checkNotNull(text, "text should not be null");
    Preconditions.checkNotNull(word, "word should not be null");

    double df = documentFrequency(word);

    return Math.log(numberOfDistinctTexts() - df + 0.5) / (df + 0.5);
  }

  /**
   * Compute likelihood ratio.
   *
   * See https://nlp.stanford.edu/fsnlp/promo/colloc.pdf section 5.3.4 for details.
   *
   * @param word1 first word of the bigram.
   * @param word2 second word of the bigram.
   * @return likelihood ratio.
   */
  default double likelihoodRatio(String word1, String word2) {
    return IBagOfTexts.likelihoodRatio(IBagOfWords.wrap(bagOfWords()),
        IBagOfNGrams.wrap(bagOfNGrams()), word1, word2);
  }

  final class SimpleBagOfTexts implements IBagOfTexts {

    private final Multiset<Text> bagOfTexts_;
    private final Multiset<String> bagOfWords_;
    private final Multiset<List<String>> bagOfNGrams_;

    private Map<String, Set<Text>> index_;
    private Multiset<List<String>> bagOfBigrams_;
    private Multiset<List<String>> bagOfTrigrams_;

    public SimpleBagOfTexts(Multiset<Text> bagOfTexts, Multiset<String> bagOfWords,
        Multiset<List<String>> bagOfNGrams) {
      bagOfTexts_ = Preconditions.checkNotNull(bagOfTexts, "bagOfTexts should not be null");
      bagOfWords_ = Preconditions.checkNotNull(bagOfWords, "bagOfWords should not be null");
      bagOfNGrams_ = Preconditions.checkNotNull(bagOfNGrams, "bagOfNGrams should not be null");
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == this) {
        return true;
      }
      if (!(obj instanceof IBagOfTexts.SimpleBagOfTexts)) {
        return false;
      }
      IBagOfTexts.SimpleBagOfTexts other = (IBagOfTexts.SimpleBagOfTexts) obj;
      return com.google.common.base.Objects.equal(bagOfTexts_, other.bagOfTexts_)
          && com.google.common.base.Objects.equal(bagOfWords_, other.bagOfWords_)
          && com.google.common.base.Objects.equal(bagOfNGrams_, other.bagOfNGrams_);
    }

    @Override
    public int hashCode() {
      return com.google.common.base.Objects.hashCode(bagOfTexts_, bagOfWords_, bagOfNGrams_);
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
    public Multiset<List<String>> bagOfBigrams() {
      if (bagOfBigrams_ == null) {
        bagOfBigrams_ = bagOfNGrams(2);
      }
      return bagOfBigrams_;
    }

    @Override
    public Multiset<List<String>> bagOfTrigrams() {
      if (bagOfTrigrams_ == null) {
        bagOfTrigrams_ = bagOfNGrams(3);
      }
      return bagOfTrigrams_;
    }

    @Override
    public Multiset<List<String>> bagOfNGrams() {
      return bagOfNGrams_;
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
    public int numberOfDistinctTextsOccurrences(String word1, String word2, String word3) {

      Preconditions.checkNotNull(word1, "word1 should not be null");
      Preconditions.checkNotNull(word2, "word2 should not be null");
      Preconditions.checkNotNull(word3, "word3 should not be null");

      Set<Text> set1 = index().getOrDefault(word1, new HashSet<>());
      Set<Text> set2 = index().getOrDefault(word2, new HashSet<>());
      Set<Text> set3 = index().getOrDefault(word3, new HashSet<>());

      int size1 = set1.size();
      int size2 = set2.size();
      int size3 = set3.size();

      if (size1 < size2) {
        if (size1 < size3) {
          return set1.stream().mapToInt(text -> text.frequency(word1, word2) > 0 ? 1 : 0).sum();
        }
        return set3.stream().mapToInt(text -> text.frequency(word1, word2) > 0 ? 1 : 0).sum();
      }
      if (size2 < size3) {
        return set2.stream().mapToInt(text -> text.frequency(word1, word2) > 0 ? 1 : 0).sum();
      }
      return set3.stream().mapToInt(text -> text.frequency(word1, word2) > 0 ? 1 : 0).sum();
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
