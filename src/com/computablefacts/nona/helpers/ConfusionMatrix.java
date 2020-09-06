package com.computablefacts.nona.helpers;

import java.util.Collection;

import com.computablefacts.nona.Generated;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Var;

/**
 * Implementation of confusion matrix for evaluating learning algorithms.
 *
 * See https://en.wikipedia.org/wiki/Evaluation_of_binary_classifiers for details.
 *
 * See https://mkhalusova.github.io/blog/2019/04/11/ml-model-evaluation-metrics-p1 for an overview
 * of MCC.
 */
@CheckReturnValue
final public class ConfusionMatrix {

  private final String clazz_;

  private double tp_ = 0;
  private double tn_ = 0;
  private double fp_ = 0;
  private double fn_ = 0;

  public ConfusionMatrix(String clazz) {

    Preconditions.checkNotNull(clazz, "clazz should not be null");

    clazz_ = clazz;
  }

  /**
   * In a multi-class classification setup, micro-average is preferable if you suspect there might
   * be class imbalance (i.e you may have many more examples of one class than of other classes).
   * 
   * @param matrices confusion matrices.
   * @return micro-averages.
   */
  public static String microAverage(Collection<ConfusionMatrix> matrices) {

    Preconditions.checkNotNull(matrices, "matrices should not be null");

    ConfusionMatrix matrix = new ConfusionMatrix("MICRO_AVG_OF_" + matrices.size() + "_MATRICES");

    for (ConfusionMatrix m : matrices) {
      matrix.tp_ += m.tp_;
      matrix.tn_ += m.tn_;
      matrix.fp_ += m.fp_;
      matrix.fn_ += m.fn_;
    }

    StringBuilder builder = new StringBuilder();
    builder.append(
        "\n================================================================================");
    builder.append("\nClass : " + matrix.clazz_);
    builder.append("\nMCC : " + matrix.matthewsCorrelationCoefficient());
    builder.append("\nF1 : " + matrix.f1Score());
    builder.append("\nPrecision : " + matrix.precision());
    builder.append("\nRecall : " + matrix.recall());
    builder.append("\nAccuracy : " + matrix.accuracy());

    return builder.toString();
  }

  /**
   * A macro-average will compute the metric independently for each class and then take the average
   * (hence treating all classes equally). The macro-average is used when you want to know how the
   * system performs overall across a given dataset. You should not come up with any specific
   * decision with this average.
   * 
   * @param matrices confusion matrices.
   * @return macro-averages.
   */
  public static String macroAverage(Collection<ConfusionMatrix> matrices) {

    Preconditions.checkNotNull(matrices, "matrices should not be null");

    @Var
    double mcc = 0;
    @Var
    double f1 = 0;
    @Var
    double precision = 0;
    @Var
    double recall = 0;
    @Var
    double accuracy = 0;

    for (ConfusionMatrix m : matrices) {
      mcc +=
          Double.isFinite(m.matthewsCorrelationCoefficient()) ? m.matthewsCorrelationCoefficient()
              : 0;
      f1 += Double.isFinite(m.f1Score()) ? m.f1Score() : 0;
      precision += Double.isFinite(m.precision()) ? m.precision() : 0;
      recall += Double.isFinite(m.recall()) ? m.recall() : 0;
      accuracy += Double.isFinite(m.accuracy()) ? m.accuracy() : 0;
    }

    StringBuilder builder = new StringBuilder();
    builder.append(
        "\n================================================================================");
    builder.append("\nClass : MACRO_AVG_OF_" + matrices.size() + "_MATRICES");
    builder.append("\nMCC : " + mcc / matrices.size());
    builder.append("\nF1 : " + f1 / matrices.size());
    builder.append("\nPrecision : " + precision / matrices.size());
    builder.append("\nRecall : " + recall / matrices.size());
    builder.append("\nAccuracy : " + accuracy / matrices.size());

    return builder.toString();
  }

  @Generated
  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append(
        "\n================================================================================");
    builder.append("\nClass : " + clazz_);
    builder.append("\nMCC : " + matthewsCorrelationCoefficient());
    builder.append("\nF1 : " + f1Score());
    builder.append("\nPrecision : " + precision());
    builder.append("\nRecall : " + recall());
    builder.append("\nAccuracy : " + accuracy());
    builder.append("\nTP : " + tp_);
    builder.append("\nTN : " + tn_);
    builder.append("\nFP : " + fp_);
    builder.append("\nFN : " + fn_);

    return builder.toString();
  }

  public void add(String actual, String predicted) {

    Preconditions.checkNotNull(actual, "actual should not be null");
    Preconditions.checkNotNull(predicted, "predicted should not be null");

    if (actual.equals(clazz_)) {
      if (predicted.equals(clazz_)) {
        tp_ += 1.0;
      } else {
        fn_ += 1.0;
      }
    } else {
      if (predicted.equals(clazz_)) {
        fp_ += 1.0;
      } else {
        tn_ += 1.0;
      }
    }
  }

  public int nbTruePositives() {
    return (int) tp_;
  }

  public int nbTrueNegatives() {
    return (int) tn_;
  }

  public int nbFalsePositives() {
    return (int) fp_;
  }

  public int nbFalseNegatives() {
    return (int) fn_;
  }

  public void addTruePositives(int count) {
    tp_ += count;
  }

  public void addTrueNegatives(int count) {
    tn_ += count;
  }

  public void addFalsePositives(int count) {
    fp_ += count;
  }

  public void addFalseNegatives(int count) {
    fn_ += count;
  }

  public void incrementTruePositives() {
    tp_ += 1.0;
  }

  public void incrementTrueNegatives() {
    tn_ += 1.0;
  }

  public void incrementFalsePositives() {
    fp_ += 1.0;
  }

  public void incrementFalseNegatives() {
    fn_ += 1.0;
  }

  /**
   * The Matthews correlation coefficient (aka. MCC) takes into account true and false positives and
   * negatives and is generally regarded as a balanced measure which can be used even if the classes
   * are of very different sizes. The MCC is in essence a correlation coefficient between the
   * observed and predicted binary classifications.
   * 
   * @return returns a value between −1 and +1. A coefficient of +1 represents a perfect prediction,
   *         0 no better than random prediction and −1 indicates total disagreement between
   *         prediction and observation.
   */
  public double matthewsCorrelationCoefficient() {
    return ((tp_ * tn_) - (fp_ * fn_))
        / Math.sqrt((tp_ + fp_) * (tp_ + fn_) * (tn_ + fp_) * (tn_ + fn_));
  }

  /**
   * Accuracy (ACC) is a measure of statistical bias.
   *
   * @return accuracy.
   */
  public double accuracy() {
    return (tp_ + tn_) / (tp_ + tn_ + fp_ + fn_);
  }

  /**
   * The Positive Predictive Value (PPV), also known as Precision is the proportion of positive
   * results that are true positive.
   *
   * @return precision.
   */
  public double precision() {
    return positivePredictionValue();
  }

  /**
   * The Positive Predictive Value (PPV), also known as Precision is the proportion of positive
   * results that are true positive.
   *
   * @return positive prediction value.
   */
  public double positivePredictionValue() {
    return tp_ / (tp_ + fp_);
  }

  /**
   * The Negative Predictive Value (NPV) is the proportion of negative results that are true
   * negative.
   * 
   * @return negative prediction value.
   */
  public double negativePredictionValue() {
    return tn_ / (tn_ + fn_);
  }

  /**
   * One of the most commonly determined statistical measures is Sensitivity (also known as recall,
   * hit rate or true positive rate TPR). Sensitivity measures the proportion of actual positives
   * that are correctly identified as positives.
   *
   * @return recall.
   */
  public double recall() {
    return sensitivity();
  }

  /**
   * One of the most commonly determined statistical measures is Sensitivity (also known as recall,
   * hit rate or true positive rate TPR). Sensitivity measures the proportion of actual positives
   * that are correctly identified as positives.
   *
   * @return sensitivity.
   */
  public double sensitivity() {
    return tp_ / (tp_ + fn_);
  }

  /**
   * Specificity, also known as selectivity or true negative rate (TNR), measures the proportion of
   * actual negatives that are correctly identified as negatives.
   *
   * @return specificity.
   */
  public double specificity() {
    return tn_ / (tn_ + fp_);
  }

  /**
   * The F1 Score is a measure of a test’s accuracy, defined as the harmonic mean of precision and
   * recall.
   *
   * @return F1 score.
   */
  public double f1Score() {
    double precision = precision();
    double recall = recall();
    return (2.0 * recall * precision) / (recall + precision);
  }

  /**
   * The False Positive Rate (FPR) or fall-out is the ratio between the number of negative events
   * incorrectly categorized as positive (false positives) and the total number of actual negative
   * events (regardless of classification).
   * 
   * @return false positive rate.
   */
  public double falsePositiveRate() {
    return fp_ / (fp_ + tn_);
  }

  /**
   * The False Discovery Rate (FDR) is a statistical approach used in multiple hypothesis testing to
   * correct for multiple comparisons.
   *
   * @return false discovery rate.
   */
  public double falseDiscoveryRate() {
    return fp_ / (fp_ + tp_);
  }

  /**
   * The False Negative Rate (FNR) measures the proportion of the individuals where a condition is
   * present for which the test result is negative.
   *
   * @return false negative rate.
   */
  public double falseNegativeRate() {
    return fn_ / (fn_ + tp_);
  }
}
