package com.computablefacts.nona.functions.utils;

import org.junit.Assert;
import org.junit.Test;

public class DocSetLabelerTest {

  @Test
  public void testLog2() {
    Assert.assertEquals(0, (int) DocSetLabeler.log2(1));
    Assert.assertEquals(1, (int) DocSetLabeler.log2(2));
    Assert.assertEquals(3, (int) DocSetLabeler.log2(8));
    Assert.assertEquals(4, (int) DocSetLabeler.log2(16));
    Assert.assertEquals(5, (int) DocSetLabeler.log2(32));
    Assert.assertEquals(6, (int) DocSetLabeler.log2(64));
    Assert.assertEquals(7, (int) DocSetLabeler.log2(128));
    Assert.assertEquals(8, (int) DocSetLabeler.log2(256));
    Assert.assertEquals(9, (int) DocSetLabeler.log2(512));
    Assert.assertEquals(10, (int) DocSetLabeler.log2(1024));
  }

  @Test
  public void testEntropy() {
    Assert.assertEquals(0, DocSetLabeler.entropy(0, 0, 0), 0.00001);
    Assert.assertEquals(0, DocSetLabeler.entropy(1, 1, 0), 0.00001);
    Assert.assertEquals(0, DocSetLabeler.entropy(1, 0, 1), 0.00001);
    Assert.assertEquals(0.97095, DocSetLabeler.entropy(5, 2, 3), 0.00001);
  }

  @Test
  public void testInformationGain() {
    // TODO
  }

  @Test
  public void testIntrinsicValue() {
    // TODO
  }

  @Test
  public void testInformationGainRatio() {
    // TODO
  }
}
