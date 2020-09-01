package com.computablefacts.nona.dictionaries;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class BicFrTest {

  @Test
  public void testEqualsNull() {
    BicFr.load().forEach((swift, bic) -> Assert.assertFalse(bic.equals(null)));
  }

  @Test
  public void testEqualsItself() {
    BicFr.load().forEach((swift, bic) -> Assert.assertTrue(bic.equals(bic)));
  }

  @Test
  public void testEqualsAndHashcode() {

    Map<String, BicFr> bics1 = BicFr.load();
    Map<String, BicFr> bics2 = BicFr.load();

    bics1.forEach((swift1, bic1) -> {

      BicFr bic2 = bics2.getOrDefault(swift1, null);

      Assert.assertEquals(bic2.hashCode(), bic1.hashCode());
      Assert.assertTrue(bic2.equals(bic1));
      Assert.assertEquals(bic2.name(), bic1.name());
      Assert.assertEquals(bic2.city(), bic1.city());
      Assert.assertEquals(bic2.swiftCode(), bic1.swiftCode());
    });
  }
}
