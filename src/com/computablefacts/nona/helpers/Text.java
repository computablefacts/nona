package com.computablefacts.nona.helpers;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.MutableNetwork;
import com.google.common.graph.NetworkBuilder;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.errorprone.annotations.CheckReturnValue;

@CheckReturnValue
final public class Text implements IBagOfWords, IBagOfNGrams {

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
  private Multiset<List<String>> bigrams_ = null;
  private Multiset<List<String>> trigrams_ = null;

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
  public Multiset<List<String>> bagOfBigrams() {
    if (bigrams_ == null) {
      bigrams_ = HashMultiset.create();
      graph_.nodes().stream()
          .flatMap(word -> graph_.successors(word).stream()
              .map(nextWord -> new AbstractMap.SimpleEntry<>(word, nextWord)))
          .forEach(bigram -> bigrams_.add(Lists.newArrayList(bigram.getKey(), bigram.getValue()),
              graph_.edgesConnecting(bigram.getKey(), bigram.getValue()).size()));
    }
    return HashMultiset.create(bigrams_);
  }

  @Override
  public Multiset<List<String>> bagOfTrigrams() {
    if (trigrams_ == null) {
      trigrams_ = HashMultiset.create();
      graph_.nodes().stream()
          .flatMap(word -> graph_.successors(word).stream()
              .flatMap(node2 -> graph_.edgesConnecting(word, node2).stream())
              .map(edge12 -> new AbstractMap.SimpleEntry<>(edge12, nextEdge(edge12)))
              .filter(edges -> edges.getValue() != null).map(edges -> {

                EndpointPair<String> nodes12 = graph_.incidentNodes(edges.getKey());
                EndpointPair<String> nodes23 = graph_.incidentNodes(edges.getValue());

                Preconditions.checkState(nodes12.nodeV().equals(nodes23.nodeU()));

                return Lists.newArrayList(nodes12.nodeU(), nodes12.nodeV(), nodes23.nodeV());
              }))
          .forEach(trigram -> trigrams_.add(trigram));
    }
    return HashMultiset.create(trigrams_);
  }

  @Override
  public Multiset<List<String>> bagOfNGrams() {

    HashMultiset<List<String>> bag = HashMultiset.create();
    bag.addAll(bagOfBigrams());
    bag.addAll(bagOfTrigrams());

    return bag;
  }

  @Override
  public Multiset<List<String>> bagOfNGrams(int n) {

    Preconditions.checkArgument(n > 1, "n must be > 1");
    Preconditions.checkArgument(n <= 3, "n must be <= 3");

    if (n == 2) {
      return bagOfBigrams();
    }
    return bagOfTrigrams();
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

  /**
   * Get bigrams starting with word1 and/or ending with word2.
   *
   * @param word1 first word.
   * @param word2 second word.
   * @param separator separator added between words.
   * @return a set of bigrams.
   */
  public Multiset<String> bigrams(String word1, String word2, char separator) {

    Multiset<String> multiset = HashMultiset.create();
    Set<String> nodes = graph_.nodes();

    if (word1 == null && word2 == null) {
      nodes.forEach(node -> multiset.addAll(bigrams(node, null, separator)));
    } else if (word1 != null && word2 != null && nodes.contains(word1) && nodes.contains(word2)) {
      if (graph_.hasEdgeConnecting(word1, word2)) {
        multiset.add(word1 + separator + word2, graph_.edgesConnecting(word1, word2).size());
      }
    } else if (word1 != null && nodes.contains(word1)) {
      graph_.successors(word1).forEach(successor -> multiset.add(word1 + separator + successor,
          graph_.edgesConnecting(word1, successor).size()));
    } else if (word2 != null && nodes.contains(word2)) {
      graph_.predecessors(word2).forEach(predecessor -> multiset
          .add(predecessor + separator + word2, graph_.edgesConnecting(predecessor, word2).size()));
    }
    return multiset;
  }

  /**
   * Get trigrams starting with word1 and ending with word3.
   *
   * @param word1 first word.
   * @param word2 second word.
   * @param word3 third word.
   * @param separator separator added between words.
   * @return a set of trigrams.
   */
  public Multiset<String> trigrams(String word1, String word2, String word3, char separator) {

    Multiset<String> multiset = HashMultiset.create();
    Set<String> nodes = graph_.nodes();

    if (word1 == null && word2 == null && word3 == null) {

      nodes.forEach(node -> multiset.addAll(trigrams(node, null, null, separator)));

    } else if (word1 != null && word2 != null && word3 != null && nodes.contains(word1)
        && nodes.contains(word2) && nodes.contains(word3)) {

      Set<String> edges12 = graph_.edgesConnecting(word1, word2);

      graph_.edgesConnecting(word2, word3).stream().map(edge23 -> {
        String edge12 = previousEdge(edge23);
        return edge12 == null || !edges12.contains(edge12) ? null
            : new AbstractMap.SimpleEntry<>(edge12, edge23);
      }).filter(edges -> edges != null).forEach(edges -> {

        EndpointPair<String> nodes12 = graph_.incidentNodes(edges.getKey());
        EndpointPair<String> nodes23 = graph_.incidentNodes(edges.getValue());

        Preconditions.checkState(nodes12.nodeV().equals(nodes23.nodeU()));

        multiset.add(nodes12.nodeU() + separator + nodes12.nodeV() + separator + nodes23.nodeV());
      });

    } else if (word1 != null && word2 != null && nodes.contains(word1) && nodes.contains(word2)) {

      graph_.edgesConnecting(word1, word2).stream().map(edge12 -> {

        EndpointPair<String> nodes12 = graph_.incidentNodes(edge12);
        EndpointPair<String> nodes23 =
            nextEdge(edge12) == null ? null : graph_.incidentNodes(nextEdge(edge12));

        return nodes23 == null ? null
            : nodes12.nodeU() + separator + nodes12.nodeV() + separator + nodes23.nodeV();
      }).filter(trigram -> trigram != null).forEach(trigram -> multiset.add(trigram));

    } else if (word2 != null && word3 != null && nodes.contains(word2) && nodes.contains(word3)) {

      graph_.edgesConnecting(word2, word3).stream().map(edge23 -> {

        EndpointPair<String> nodes12 =
            previousEdge(edge23) == null ? null : graph_.incidentNodes(previousEdge(edge23));
        EndpointPair<String> nodes23 = graph_.incidentNodes(edge23);

        return nodes12 == null ? null
            : nodes12.nodeU() + separator + nodes23.nodeU() + separator + nodes23.nodeV();
      }).filter(trigram -> trigram != null).forEach(trigram -> multiset.add(trigram));

    } else if (word1 != null && word3 != null && nodes.contains(word1) && nodes.contains(word3)) {

      Set<String> edges12 = graph_.successors(word1).stream()
          .flatMap(successor -> graph_.edgesConnecting(word1, successor).stream())
          .collect(Collectors.toSet());

      graph_.predecessors(word3).stream()
          .flatMap(node2 -> graph_.edgesConnecting(node2, word3).stream()).map(edge23 -> {
            String edge12 = previousEdge(edge23);
            return edge12 == null || !edges12.contains(edge12) ? null
                : new AbstractMap.SimpleEntry<>(edge12, edge23);
          }).filter(edges -> edges != null).forEach(edges -> {

            EndpointPair<String> nodes12 = graph_.incidentNodes(edges.getKey());
            EndpointPair<String> nodes23 = graph_.incidentNodes(edges.getValue());

            Preconditions.checkState(nodes12.nodeV().equals(nodes23.nodeU()));

            multiset
                .add(nodes12.nodeU() + separator + nodes12.nodeV() + separator + nodes23.nodeV());
          });

    } else if (word1 != null && nodes.contains(word1)) {

      graph_.successors(word1).stream()
          .flatMap(node2 -> graph_.edgesConnecting(word1, node2).stream())
          .map(edge12 -> new AbstractMap.SimpleEntry<>(edge12, nextEdge(edge12)))
          .filter(edges -> edges.getValue() != null).forEach(edges -> {

            EndpointPair<String> nodes12 = graph_.incidentNodes(edges.getKey());
            EndpointPair<String> nodes23 = graph_.incidentNodes(edges.getValue());

            Preconditions.checkState(nodes12.nodeV().equals(nodes23.nodeU()));

            multiset
                .add(nodes12.nodeU() + separator + nodes12.nodeV() + separator + nodes23.nodeV());
          });

    } else if (word2 != null && nodes.contains(word2)) {

      graph_.predecessors(word2).stream()
          .flatMap(node1 -> graph_.edgesConnecting(node1, word2).stream())
          .map(edge12 -> new AbstractMap.SimpleEntry<>(edge12, nextEdge(edge12)))
          .filter(edges -> edges.getValue() != null).forEach(edges -> {

            EndpointPair<String> nodes12 = graph_.incidentNodes(edges.getKey());
            EndpointPair<String> nodes23 = graph_.incidentNodes(edges.getValue());

            Preconditions.checkState(nodes12.nodeV().equals(nodes23.nodeU()));

            multiset
                .add(nodes12.nodeU() + separator + nodes12.nodeV() + separator + nodes23.nodeV());
          });

    } else if (word3 != null && nodes.contains(word3)) {

      graph_.predecessors(word3).stream()
          .flatMap(node2 -> graph_.edgesConnecting(node2, word3).stream())
          .map(edge23 -> new AbstractMap.SimpleEntry<>(previousEdge(edge23), edge23))
          .filter(edges -> edges.getKey() != null).forEach(edges -> {

            EndpointPair<String> nodes12 = graph_.incidentNodes(edges.getKey());
            EndpointPair<String> nodes23 = graph_.incidentNodes(edges.getValue());

            Preconditions.checkState(nodes12.nodeV().equals(nodes23.nodeU()));

            multiset
                .add(nodes12.nodeU() + separator + nodes12.nodeV() + separator + nodes23.nodeV());
          });
    }
    return multiset;
  }

  private String previousEdge(String edge) {

    Preconditions.checkNotNull(edge, "edge should not be null");

    int sentenceId = sentenceId(edge);
    int wordId = wordId(edge);

    String prevEdge = sentenceId + "-" + (wordId - 1);

    try {
      EndpointPair<String> ep = graph_.incidentNodes(prevEdge);
      return ep != null ? prevEdge : null;
    } catch (IllegalArgumentException e) {
      // TODO
    }
    return null;
  }

  private String nextEdge(String edge) {

    Preconditions.checkNotNull(edge, "edge should not be null");

    int sentenceId = sentenceId(edge);
    int wordId = wordId(edge);

    String nextEdge = sentenceId + "-" + (wordId + 1);

    try {
      EndpointPair<String> ep = graph_.incidentNodes(nextEdge);
      return ep != null ? nextEdge : null;
    } catch (IllegalArgumentException e) {
      // TODO
    }
    return null;
  }

  private int sentenceId(String edge) {

    Preconditions.checkNotNull(edge, "edge should not be null");

    return Integer.parseInt(edge.substring(0, edge.indexOf('-')), 10);
  }

  private int wordId(String edge) {

    Preconditions.checkNotNull(edge, "edge should not be null");

    return Integer.parseInt(edge.substring(edge.indexOf('-') + 1), 10);
  }
}
