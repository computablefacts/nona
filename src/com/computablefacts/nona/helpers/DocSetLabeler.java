package com.computablefacts.nona.helpers;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Var;

/**
 * Implements a modified version of the DocSetLabeler algorithm from "Topic Similarity Networks:
 * Visual Analytics for Large Document Sets" by Arun S. Maiya and Robert M. Rolfe.
 */
@CheckReturnValue
public abstract class DocSetLabeler {

  protected DocSetLabeler() {}

  /**
   * See https://en.wikipedia.org/wiki/Entropy_(information_theory)#Definition for details.
   *
   * @param nbDocs total number of documents.
   * @param nbPosDocs number of documents (from the subset) that contain this label.
   * @param nbNegDocs number of documents (not from the subset) that contain this label.
   * @return entropy.
   */
  static double entropy(double nbDocs, double nbPosDocs, double nbNegDocs) {

    Preconditions.checkArgument(nbDocs >= 0.0, "nbDocs must be >= 0");
    Preconditions.checkArgument(nbPosDocs >= 0.0, "nbPosDocs must be >= 0");
    Preconditions.checkArgument(nbNegDocs >= 0.0, "nbNegDocs must be >= 0");
    Preconditions.checkArgument(nbDocs == nbPosDocs + nbNegDocs,
        "nbDocs must be = nbPosDocs + nbNegDocs");

    if (nbDocs == 0.0) {
      return 0.0;
    }

    double positive = nbPosDocs / nbDocs;
    double negative = nbNegDocs / nbDocs;

    return (positive == 0.0 ? 0.0 : -positive * log2(positive))
        + (negative == 0.0 ? 0.0 : -negative * log2(negative));
  }

  static double log2(double a) {
    return Math.log(a) / Math.log(2);
  }

  /**
   * Extract labels from texts.
   *
   * @param corpus a corpus of texts.
   * @param subsetOk a subset of the corpus having caller-defined characteristics that should be
   *        matched.
   * @param subsetKo a subset of the corpus having caller-defined characteristics that should not be
   *        matched.
   * @param nbCandidatesToConsider the number of candidate terms to consider in each document.
   * @param nbLabelsToReturn the number of labels to return.
   * @return labels and scores.
   */
  public List<Map.Entry<String, Double>> label(List<String> corpus, List<String> subsetOk,
      List<String> subsetKo, int nbCandidatesToConsider, int nbLabelsToReturn) {
    return label(corpus, subsetOk, subsetKo, nbCandidatesToConsider, nbLabelsToReturn, false);
  }

  /**
   * Extract labels from texts.
   *
   * @param corpus a corpus of texts.
   * @param subsetOk a subset of the corpus having caller-defined characteristics that should be
   *        matched.
   * @param subsetKo a subset of the corpus having caller-defined characteristics that should not be
   *        matched.
   * @param nbCandidatesToConsider the number of candidate terms to consider in each document.
   * @param nbLabelsToReturn the number of labels to return.
   * @param hasProgressBar true iif a progress bar must be displayed, false otherwise.
   * @return labels and scores.
   */
  public List<Map.Entry<String, Double>> label(List<String> corpus, List<String> subsetOk,
      List<String> subsetKo, int nbCandidatesToConsider, int nbLabelsToReturn,
      boolean hasProgressBar) {

    Preconditions.checkNotNull(corpus, "corpus should not be null");
    Preconditions.checkNotNull(subsetOk, "subsetOk should not be null");
    Preconditions.checkNotNull(subsetKo, "subsetKo should not be null");
    Preconditions.checkArgument(nbCandidatesToConsider > 0, "nbCandidatesToConsider should be > 0");
    Preconditions.checkArgument(nbLabelsToReturn > 0, "nbLabelsToReturn should be > 0");
    Preconditions.checkArgument(nbLabelsToReturn <= nbCandidatesToConsider,
        "nbLabelsToReturn should be <= nbCandidatesToConsider");

    if (corpus.isEmpty() || subsetOk.isEmpty()) {
      return new ArrayList<>();
    }

    init(corpus, subsetOk, subsetKo);

    Comparator<Map.Entry<String, Double>> byScoreDesc =
        Comparator.comparingDouble((Map.Entry<String, Double> pair) -> pair.getValue())
            .thenComparingInt(pair -> pair.getKey().length()).thenComparing(pair -> pair.getKey())
            .reversed();

    Map<String, Set<String>> pos = new HashMap<>();
    Map<String, Set<String>> neg = new HashMap<>();

    @Var
    int nbTextsProcessed = 0;
    int nbTexts = corpus.size();
    AsciiProgressBar.ProgressBar bar = hasProgressBar ? AsciiProgressBar.create() : null;

    for (String text : corpus) {

      nbTextsProcessed++;

      if (bar != null) {
        bar.update(nbTextsProcessed, nbTexts);
      }

      List<Map.Entry<String, Double>> weights = new ArrayList<>();
      Set<String> candidates = candidates(text);

      for (String candidate : candidates) {

        double x = computeX(text, candidate);
        double y = computeY(text, candidate);
        double weight = (2.0 * x * y) / (x + y);

        Preconditions.checkState(0.0 <= x && x <= 1.0, "x should be such as 0.0 <= x <= 1.0 : %s",
            x);
        Preconditions.checkState(0.0 <= y && y <= 1.0, "y should be such as 0.0 <= y <= 1.0 : %s",
            y);

        weights.add(new AbstractMap.SimpleEntry<>(candidate, weight));
      }

      Set<String> selection = weights.isEmpty() ? Sets.newHashSet()
          : weights.stream().sorted(byScoreDesc).limit(nbCandidatesToConsider)
              .map(Map.Entry::getKey).collect(Collectors.toSet());

      if (subsetOk.contains(text)) {
        pos.put(text, selection);
      } else {
        neg.put(text, selection);
      }
    }

    if (bar != null) {
      bar.update(nbTextsProcessed, nbTexts);
    }

    uinit();

    // For each candidate keyword compute the information gain
    List<Map.Entry<String, Double>> candidates = pos.values().stream().flatMap(Collection::stream)
        .map(candidate -> new AbstractMap.SimpleEntry<>(candidate, calcScore(candidate, pos, neg)))
        .sorted(byScoreDesc).distinct().collect(Collectors.toList());

    // Keep the keywords with the highest information gain
    return filter(candidates).stream().limit(nbLabelsToReturn).collect(Collectors.toList());
  }

  protected void init(@NotNull List<String> corpus, @NotNull List<String> subsetOk,
      @NotNull List<String> subsetKo) {}

  protected void uinit() {}

  protected abstract Set<String> candidates(String text);

  protected abstract double computeX(String text, String candidate);

  protected abstract double computeY(String text, String candidate);

  protected List<Map.Entry<String, Double>> filter(
      @NotNull List<Map.Entry<String, Double>> candidates) {
    return candidates;
  }

  private double calcScore(String candidate, Map<String, Set<String>> pos,
      Map<String, Set<String>> neg) {
    return informationGainRatio(candidate, pos, neg);
  }

  /**
   * See https://en.wikipedia.org/wiki/Information_gain_ratio#Information_gain_ratio_calculation for
   * details.
   *
   * @param candidate candidate label.
   * @param pos documents (from the subset) that contain this label.
   * @param neg documents (from the corpus) that contain this label.
   * @return score.
   */
  private double informationGainRatio(String candidate, Map<String, Set<String>> pos,
      Map<String, Set<String>> neg) {
    return informationGain(candidate, pos, neg) / intrinsicValue(candidate, pos, neg);
  }

  /**
   * See https://en.wikipedia.org/wiki/Information_gain_ratio#Intrinsic_value_calculation for
   * details.
   *
   * @param candidate candidate label.
   * @param pos documents (from the subset) that contain this label.
   * @param neg documents (from the corpus) that contain this label.
   * @return intrinsic value.
   */
  private double intrinsicValue(String candidate, Map<String, Set<String>> pos,
      Map<String, Set<String>> neg) {

    Preconditions.checkNotNull(candidate, "candidate should not be null");
    Preconditions.checkNotNull(pos, "pos should not be null");
    Preconditions.checkNotNull(neg, "neg should not be null");

    double nbPosDocs = pos.size();
    double nbNegDocs = neg.size();
    double nbDocs = pos.size() + neg.size();

    return -((nbPosDocs / nbDocs) * log2(nbPosDocs / nbDocs)
        + (nbNegDocs / nbDocs) * log2(nbNegDocs / nbDocs));
  }

  /**
   * See https://en.wikipedia.org/wiki/Information_gain_ratio#Information_gain_calculation for
   * details.
   *
   * A good high-level overview can be found here
   * https://towardsdatascience.com/entropy-how-decision-trees-make-decisions-2946b9c18c8
   *
   * @param candidate candidate label.
   * @param pos documents (from the subset) that contain this label.
   * @param neg documents (from the corpus) that contain this label.
   * @return information gain.
   */
  private double informationGain(String candidate, Map<String, Set<String>> pos,
      Map<String, Set<String>> neg) {

    Preconditions.checkNotNull(candidate, "candidate should not be null");
    Preconditions.checkNotNull(pos, "pos should not be null");
    Preconditions.checkNotNull(neg, "neg should not be null");

    double nbPosDocs = pos.size();
    double nbNegDocs = neg.size();
    double nbDocs = pos.size() + neg.size();

    double nbCandidateMatchInPosDocs = pos.values().stream()
        .mapToDouble(list -> list.stream().anyMatch(term -> term.equals(candidate)) ? 1.0 : 0.0)
        .sum();
    double nbCandidateMatchInNegDocs = neg.values().stream()
        .mapToDouble(list -> list.stream().anyMatch(term -> term.equals(candidate)) ? 1.0 : 0.0)
        .sum();
    double nbCandidateMatchInDocs = nbCandidateMatchInPosDocs + nbCandidateMatchInNegDocs;

    double h = entropy(nbDocs, nbCandidateMatchInDocs, nbDocs - nbCandidateMatchInDocs);
    double hPos =
        entropy(nbPosDocs, nbCandidateMatchInPosDocs, nbPosDocs - nbCandidateMatchInPosDocs);
    double hNeg =
        entropy(nbNegDocs, nbCandidateMatchInNegDocs, nbNegDocs - nbCandidateMatchInNegDocs);

    return h - ((nbPosDocs / nbDocs) * hPos + (nbNegDocs / nbDocs) * hNeg);
  }
}
