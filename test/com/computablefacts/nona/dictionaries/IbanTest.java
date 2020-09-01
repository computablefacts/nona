package com.computablefacts.nona.dictionaries;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class IbanTest {

  @Test
  public void testEqualsNull() {
    Iban.load().forEach((countryCode, iban) -> Assert.assertFalse(iban.equals(null)));
  }

  @Test
  public void testEqualsItself() {
    Iban.load().forEach((countryCode, iban) -> Assert.assertTrue(iban.equals(iban)));
  }

  @Test
  public void testEqualsAndHashcode() {

    Map<String, Iban> ibans1 = Iban.load();
    Map<String, Iban> ibans2 = Iban.load();

    ibans1.forEach((countryCode1, iban1) -> {

      Iban iban2 = ibans2.getOrDefault(countryCode1, null);

      Assert.assertEquals(iban2.hashCode(), iban1.hashCode());
      Assert.assertTrue(iban2.equals(iban1));
      Assert.assertEquals(iban2.countryName(), iban1.countryName());
      Assert.assertEquals(iban2.countryCode(), iban1.countryCode());
      Assert.assertEquals(iban2.isSepaMember(), iban1.isSepaMember());
      Assert.assertEquals(iban2.ibanExample(), iban1.ibanExample());
      Assert.assertEquals(iban2.ibanLength(), iban1.ibanLength());
    });
  }
}
