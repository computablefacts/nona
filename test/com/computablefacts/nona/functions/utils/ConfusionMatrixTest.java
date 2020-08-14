package com.computablefacts.nona.functions.utils;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * Values verified using https://www.mdapp.co/confusion-matrix-calculator-406/
 */
public class ConfusionMatrixTest {

  @Test
  public void testSimpleMicroAverage() {

    ConfusionMatrix matrixA = new ConfusionMatrix("A");
    matrixA.addTruePositives(1);
    matrixA.addFalsePositives(1);

    ConfusionMatrix matrixB = new ConfusionMatrix("B");
    matrixB.addTruePositives(10);
    matrixB.addFalsePositives(90);

    ConfusionMatrix matrixC = new ConfusionMatrix("C");
    matrixC.addTruePositives(1);
    matrixC.addFalsePositives(1);

    ConfusionMatrix matrixD = new ConfusionMatrix("D");
    matrixD.addTruePositives(1);
    matrixD.addFalsePositives(1);

    String microAverage =
        ConfusionMatrix.microAverage(Lists.newArrayList(matrixA, matrixB, matrixC, matrixD));

    Assert.assertTrue(microAverage.contains("\nMCC : NaN"));
    Assert.assertTrue(microAverage.contains("\nF1 : 0.21848739495798317"));
    Assert.assertTrue(microAverage.contains("\nPrecision : 0.12264150943396226"));
    Assert.assertTrue(microAverage.contains("\nRecall : 1.0"));
    Assert.assertTrue(microAverage.contains("\nAccuracy : 0.12264150943396226"));
  }

  @Test
  public void testSimpleMacroAverage() {

    ConfusionMatrix matrixA = new ConfusionMatrix("A");
    matrixA.addTruePositives(1);
    matrixA.addFalsePositives(1);

    ConfusionMatrix matrixB = new ConfusionMatrix("B");
    matrixB.addTruePositives(10);
    matrixB.addFalsePositives(90);

    ConfusionMatrix matrixC = new ConfusionMatrix("C");
    matrixC.addTruePositives(1);
    matrixC.addFalsePositives(1);

    ConfusionMatrix matrixD = new ConfusionMatrix("D");
    matrixD.addTruePositives(1);
    matrixD.addFalsePositives(1);

    String macroAverage =
        ConfusionMatrix.macroAverage(Lists.newArrayList(matrixA, matrixB, matrixC, matrixD));

    Assert.assertTrue(macroAverage.contains("\nMCC : NaN"));
    Assert.assertTrue(macroAverage.contains("\nF1 : 0.5454545454545454"));
    Assert.assertTrue(macroAverage.contains("\nPrecision : 0.4"));
    Assert.assertTrue(macroAverage.contains("\nRecall : 1"));
    Assert.assertTrue(macroAverage.contains("\nAccuracy : 0.4"));
  }

  @Test
  public void testComplexMicroAverage() {

    ConfusionMatrix matrixA = new ConfusionMatrix("A");
    matrixA.addTruePositives(12);
    matrixA.addFalsePositives(9);
    matrixA.addFalseNegatives(3);

    ConfusionMatrix matrixB = new ConfusionMatrix("B");
    matrixB.addTruePositives(50);
    matrixB.addFalsePositives(23);
    matrixB.addFalseNegatives(9);

    String microAverage = ConfusionMatrix.microAverage(Lists.newArrayList(matrixA, matrixB));

    Assert.assertTrue(microAverage.contains("\nMCC : -0.23495561349012983"));
    Assert.assertTrue(microAverage.contains("\nF1 : 0.7380952380952381"));
    Assert.assertTrue(microAverage.contains("\nPrecision : 0.6595744680851063"));
    Assert.assertTrue(microAverage.contains("\nRecall : 0.8378378378378378"));
    Assert.assertTrue(microAverage.contains("\nAccuracy : 0.5849056603773585"));
  }

  @Test
  public void testComplexMacroAverage() {

    ConfusionMatrix matrixA = new ConfusionMatrix("A");
    matrixA.addTruePositives(12);
    matrixA.addFalsePositives(9);
    matrixA.addFalseNegatives(3);

    ConfusionMatrix matrixB = new ConfusionMatrix("B");
    matrixB.addTruePositives(50);
    matrixB.addFalsePositives(23);
    matrixB.addFalseNegatives(9);

    String macroAverage = ConfusionMatrix.macroAverage(Lists.newArrayList(matrixA, matrixB));

    Assert.assertTrue(macroAverage.contains("\nMCC : -0.2559994438028284"));
    Assert.assertTrue(macroAverage.contains("\nF1 : 0.7121212121212122"));
    Assert.assertTrue(macroAverage.contains("\nPrecision : 0.6281800391389432"));
    Assert.assertTrue(macroAverage.contains("\nRecall : 0.823728813559322"));
    Assert.assertTrue(macroAverage.contains("\nAccuracy : 0.5548780487804879"));
  }

  @Test
  public void testAdd() {

    ConfusionMatrix matrix0 = confusionMatrix1();
    ConfusionMatrix matrix1 = confusionMatrix2();
    ConfusionMatrix matrix2 = confusionMatrix();

    Assert.assertEquals(matrix0.toString(), matrix2.toString());
    Assert.assertEquals(matrix1.toString(), matrix2.toString());
  }

  @Test
  public void testMcc() {

    ConfusionMatrix matrix = confusionMatrix();

    Assert.assertEquals(0.6635, matrix.matthewsCorrelationCoefficient(), 0.0001);
  }

  @Test
  public void testAccuracy() {

    ConfusionMatrix matrix = confusionMatrix();

    Assert.assertEquals(0.944, matrix.accuracy(), 0.000001);
  }

  @Test
  public void testSensitivity() {

    ConfusionMatrix matrix = confusionMatrix();

    Assert.assertEquals(0.62, matrix.sensitivity(), 0.000001);
  }

  @Test
  public void testRecall() {

    ConfusionMatrix matrix = confusionMatrix();

    Assert.assertEquals(0.62, matrix.recall(), 0.000001);
  }

  @Test
  public void testPrecision() {

    ConfusionMatrix matrix = confusionMatrix();

    Assert.assertEquals(0.775, matrix.precision(), 0.000001);
  }

  @Test
  public void testPositivePredictionValue() {

    ConfusionMatrix matrix = confusionMatrix();

    Assert.assertEquals(0.775, matrix.positivePredictionValue(), 0.000001);
  }

  @Test
  public void testNegativePredictionValue() {

    ConfusionMatrix matrix = confusionMatrix();

    Assert.assertEquals(0.958, matrix.negativePredictionValue(), 0.001);
  }

  @Test
  public void testSpecificity() {

    ConfusionMatrix matrix = confusionMatrix();

    Assert.assertEquals(0.98, matrix.specificity(), 0.000001);
  }

  @Test
  public void testF1() {

    ConfusionMatrix matrix = confusionMatrix();

    Assert.assertEquals(0.6889, matrix.f1Score(), 0.0001);
  }

  @Test
  public void falsePositiveRate() {

    ConfusionMatrix matrix = confusionMatrix();

    Assert.assertEquals(0.02, matrix.falsePositiveRate(), 0.000001);
  }

  @Test
  public void falseDiscoveryRate() {

    ConfusionMatrix matrix = confusionMatrix();

    Assert.assertEquals(0.225, matrix.falseDiscoveryRate(), 0.000001);
  }

  @Test
  public void falseNegativeRate() {

    ConfusionMatrix matrix = confusionMatrix();

    Assert.assertEquals(0.38, matrix.falseNegativeRate(), 0.000001);
  }

  private ConfusionMatrix confusionMatrix() {

    ConfusionMatrix matrix = new ConfusionMatrix("");

    matrix.addTruePositives(620);
    matrix.addTrueNegatives(8820);
    matrix.addFalsePositives(180);
    matrix.addFalseNegatives(380);

    return matrix;
  }

  private ConfusionMatrix confusionMatrix1() {

    ConfusionMatrix matrix = new ConfusionMatrix("");

    for (int i = 0; i < 620; i++) {
      matrix.incrementTruePositives();
    }
    for (int i = 0; i < 8820; i++) {
      matrix.incrementTrueNegatives();
    }
    for (int i = 0; i < 180; i++) {
      matrix.incrementFalsePositives();
    }
    for (int i = 0; i < 380; i++) {
      matrix.incrementFalseNegatives();
    }
    return matrix;
  }

  private ConfusionMatrix confusionMatrix2() {

    ConfusionMatrix matrix = new ConfusionMatrix("");

    for (int i = 0; i < 620; i++) {
      matrix.add("", "");
    }
    for (int i = 0; i < 8820; i++) {
      matrix.add("<UNK>", "<UNK>");
    }
    for (int i = 0; i < 180; i++) {
      matrix.add("<UNK>", "");
    }
    for (int i = 0; i < 380; i++) {
      matrix.add("", "<UNK>");
    }
    return matrix;
  }
}
