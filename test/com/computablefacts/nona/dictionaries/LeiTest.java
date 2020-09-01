package com.computablefacts.nona.dictionaries;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class LeiTest {

  @Test
  public void testEqualsNull() {
    Lei.load().forEach(lei -> Assert.assertFalse(lei.equals(null)));
  }

  @Test
  public void testEqualsItself() {
    Lei.load().forEach(lei -> Assert.assertTrue(lei.equals(lei)));
  }

  @Test
  public void testEqualsAndHashcode() {

    List<Lei> leis1 = Lei.load();
    List<Lei> leis2 = Lei.load();

    for (int i = 0; i < leis1.size(); i++) {

      Lei lei1 = leis1.get(i);
      Lei lei2 = leis2.get(i);

      Assert.assertEquals(lei1.hashCode(), lei2.hashCode());
      Assert.assertTrue(lei1.equals(lei2));
      Assert.assertEquals(lei1.lei(), lei2.lei());
      Assert.assertEquals(lei1.legalName(), lei2.legalName());
      Assert.assertEquals(lei1.countryCode(), lei2.countryCode());
      Assert.assertEquals(lei1.bic(), lei2.bic());
      Assert.assertEquals(lei1.bic8(), lei2.bic8());
    }
  }
}
