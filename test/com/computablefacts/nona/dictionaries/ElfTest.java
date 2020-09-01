package com.computablefacts.nona.dictionaries;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class ElfTest {

  @Test
  public void testEqualsAndHashcode() {
    EqualsVerifier.forClass(Elf.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }
}
