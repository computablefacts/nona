package com.computablefacts.nona.dictionaries;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class TldTest {

  @Test
  public void testEqualsAndHashcode() {
    EqualsVerifier.forClass(Tld.class).suppress(Warning.INHERITED_DIRECTLY_FROM_OBJECT).verify();
  }
}
