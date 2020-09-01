package com.computablefacts.nona.dictionaries;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class IbanTest {

  @Test
  public void testEqualsAndHashcode() {
    EqualsVerifier.forClass(Iban.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }
}
