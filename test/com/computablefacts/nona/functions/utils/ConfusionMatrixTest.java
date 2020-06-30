/*
 * Copyright (c) 2011-2020 MNCC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @author http://www.mncc.fr
 */
package com.computablefacts.nona.functions.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Values verified using https://www.mdapp.co/confusion-matrix-calculator-406/
 */
public class ConfusionMatrixTest {

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
