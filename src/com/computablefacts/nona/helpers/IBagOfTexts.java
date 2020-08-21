package com.computablefacts.nona.helpers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multiset;
import com.google.errorprone.annotations.Var;

public interface IBagOfTexts extends IBagOfWords, IBagOfBigrams {

  double K1 = 1.2; // in [1.2, 2.0]
  double B = 0.75;

  static IBagOfTexts wrap(Multiset<Text> bagOfTexts, Multiset<String> bagOfWords,
      Multiset<Map.Entry<String, String>> bagOfBigrams) {
    return new SimpleBagOfTexts(bagOfTexts, bagOfWords, bagOfBigrams);
  }

  @Deprecated
  static String encode(String text) {
    return com.computablefacts.nona.Function.encode(text).replace("\n", "\\u000d").replace("\r",
        "\\u000a");
  }

  @Deprecated
  static String decode(String text) {
    return com.computablefacts.nona.Function
        .decode(text.replace("\\u000d", "\n").replace("\\u000a", "\r"));
  }

  /**
   * Anagram hashing function.
   *
   * See http://www.lrec-conf.org/proceedings/lrec2006/pdf/229_pdf.pdf for details.
   *
   * @param word word.
   * @return hash.
   */
  static String anagramHash(String word) {

    Preconditions.checkNotNull(word, "word should not be null");

    @Var
    long hash = 0;

    for (int i = 0; i < word.length(); i++) {
      hash += Math.pow(word.codePointAt(i), 5);
    }
    return Long.toString(hash, 10);
  }

  /**
   * Normalized edit distance.
   *
   * @param word1 word.
   * @param word2 word.
   * @return normalized distance. This value is in [0, 1].
   */
  static double normalizedLevenshteinDistance(CharSequence word1, CharSequence word2) {

    Preconditions.checkNotNull(word1, "word1 should not be null");
    Preconditions.checkNotNull(word2, "word2 should not be null");

    if (word1.length() == 0 && word2.length() == 0) {
      return 0.0;
    }
    if (word1.equals(word2)) {
      return 0.0;
    }
    return (double) levenshteinDistance(word1, word2)
        / (double) Math.max(word1.length(), word2.length());
  }

  /**
   * Implementation of the Levenshtein algorithm to compute the edit distance between two words.
   *
   * @param word1 word.
   * @param word2 word.
   * @return distance. This value is an integer in [0, max(word1.length, word2.length)]. 0 means
   *         both words are equals.
   */
  static int levenshteinDistance(CharSequence word1, CharSequence word2) {

    Preconditions.checkNotNull(word1, "word1 should not be null");
    Preconditions.checkNotNull(word2, "word2 should not be null");

    if (word1.length() == 0 && word2.length() == 0) {
      return 0;
    }
    if (word1.equals(word2)) {
      return 0;
    }

    int len0 = word1.length() + 1;
    int len1 = word2.length() + 1;

    // The array of distances
    @Var
    int[] cost = new int[len0];
    @Var
    int[] newcost = new int[len0];

    // Initial cost of skipping prefix in String s0
    for (int i = 0; i < len0; i++) {
      cost[i] = i;
    }

    // Dynamically computing the array of distances

    // Transformation cost for each letter in s1
    for (int j = 1; j < len1; j++) {

      // Initial cost of skipping prefix in String s1
      newcost[0] = j;

      // Transformation cost for each letter in s0
      for (int i = 1; i < len0; i++) {

        // Matching current letters in both strings
        int match = (word1.charAt(i - 1) == word2.charAt(j - 1)) ? 0 : 1;

        // Computing cost for each transformation
        int cost_replace = cost[i - 1] + match;
        int cost_insert = cost[i] + 1;
        int cost_delete = newcost[i - 1] + 1;

        // Keep minimum cost
        newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
      }

      // Swap cost/newcost arrays
      int[] swap = cost;
      cost = newcost;
      newcost = swap;
    }

    // The distance is the cost for transforming all letters in both strings
    return cost[len0 - 1];
  }

  /**
   * Compute likelihood ratio.
   *
   * See https://nlp.stanford.edu/fsnlp/promo/colloc.pdf section 5.3.4 for details.
   *
   * @param bagOfWords bag of words.
   * @param bagOfBigrams bag of bigrams.
   * @param word1 first word of the bigram.
   * @param word2 second word of the bigram.
   * @return likelihood ratio.
   */
  static double likelihoodRatio(IBagOfWords bagOfWords, IBagOfBigrams bagOfBigrams, String word1,
      String word2) {

    Preconditions.checkNotNull(bagOfWords, "bagOfWords should not be null");
    Preconditions.checkNotNull(bagOfBigrams, "bagOfBigrams should not be null");
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
    int c12 = bagOfBigrams.frequency(word1, word2);
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
  default Multiset<Map.Entry<String, String>> bagOfBigrams() {
    Multiset<Map.Entry<String, String>> bag = HashMultiset.create();
    bagOfTexts().elementSet().stream().map(Text::bagOfBigrams).forEach(bag::addAll);
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
    Multiset<Map.Entry<String, String>> bagOfBigrams = HashMultiset.create();

    bagOfTexts().elementSet().forEach(bag -> {
      bagOfWords.addAll(bag.bagOfWords());
      bagOfBigrams.addAll(bag.bagOfBigrams());
    });
    return wrap(bagOfTexts(), bagOfWords, bagOfBigrams);
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

    return (double) text.frequency(word1, word2) / (double) text.numberOfBigrams();
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
        IBagOfBigrams.wrap(bagOfBigrams()), word1, word2);
  }

  final class SimpleBagOfTexts implements IBagOfTexts {

    private final Multiset<Text> bagOfTexts_;
    private final Multiset<String> bagOfWords_;
    private final Multiset<Map.Entry<String, String>> bagOfBigrams_;

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
      if (!(obj instanceof SimpleBagOfTexts)) {
        return false;
      }
      SimpleBagOfTexts other = (SimpleBagOfTexts) obj;
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
  }
}
