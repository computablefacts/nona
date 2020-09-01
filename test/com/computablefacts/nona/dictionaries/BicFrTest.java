package com.computablefacts.nona.dictionaries;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class BicFrTest {

  @Test
  public void testEqualsAndHashcode() {
    EqualsVerifier.forClass(BicFr.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }
}
