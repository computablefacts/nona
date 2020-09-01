package com.computablefacts.nona.dictionaries;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class CountryTest {

  @Test
  public void testEqualsNull() {
    Country.load().forEach((alpha2, country) -> Assert.assertFalse(country.equals(null)));
  }

  @Test
  public void testEqualsItself() {
    Country.load().forEach((alpha2, country) -> Assert.assertTrue(country.equals(country)));
  }

  @Test
  public void testEqualsAndHashcode() {

    Map<String, Country> countries1 = Country.load();
    Map<String, Country> countries2 = Country.load();

    countries1.forEach((alpha21, country1) -> {

      Country country2 = countries2.getOrDefault(alpha21, null);

      Assert.assertEquals(country2.hashCode(), country1.hashCode());
      Assert.assertTrue(country2.equals(country1));
      Assert.assertEquals(country2.name(), country1.name());
      Assert.assertEquals(country2.alpha2(), country1.alpha2());
      Assert.assertEquals(country2.alpha3(), country1.alpha3());
      Assert.assertEquals(country2.numeric(), country1.numeric());
    });
  }
}
