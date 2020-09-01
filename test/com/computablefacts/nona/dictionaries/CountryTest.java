package com.computablefacts.nona.dictionaries;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class CountryTest {

  @Test
  public void testEqualsAndHashcode() {
    EqualsVerifier.forClass(Country.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }
}
