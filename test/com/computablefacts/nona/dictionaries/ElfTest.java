package com.computablefacts.nona.dictionaries;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ElfTest {

  @Test
  public void testEqualsNull() {
    Elf.load().forEach(elf -> Assert.assertFalse(elf.equals(null)));
  }

  @Test
  public void testEqualsItself() {
    Elf.load().forEach(elf -> Assert.assertTrue(elf.equals(elf)));
  }

  @Test
  public void testEqualsAndHashcode() {

    List<Elf> elfs1 = Elf.load();
    List<Elf> elfs2 = Elf.load();

    for (int i = 0; i < elfs1.size(); i++) {

      Elf elf1 = elfs1.get(i);
      Elf elf2 = elfs2.get(i);

      Assert.assertEquals(elf1.hashCode(), elf2.hashCode());
      Assert.assertTrue(elf1.equals(elf2));
      Assert.assertEquals(elf1.elfCode(), elf2.elfCode());
      Assert.assertEquals(elf1.countryName(), elf2.countryName());
      Assert.assertEquals(elf1.countryCode(), elf2.countryCode());
      Assert.assertEquals(elf1.jurisdictionOfFormation(), elf2.jurisdictionOfFormation());
      Assert.assertEquals(elf1.countrySubdivisionCode(), elf2.countrySubdivisionCode());
      Assert.assertEquals(elf1.legalFormNameLocal(), elf2.legalFormNameLocal());
      Assert.assertEquals(elf1.language(), elf2.language());
      Assert.assertEquals(elf1.languageCode(), elf2.languageCode());
      Assert.assertEquals(elf1.legalFormNameTransliterated(), elf2.legalFormNameTransliterated());
      Assert.assertEquals(elf1.abbreviationLocal(), elf2.abbreviationLocal());
      Assert.assertEquals(elf1.abbreviationTransliterated(), elf2.abbreviationTransliterated());
      Assert.assertEquals(elf1.dateCreated(), elf2.dateCreated());
      Assert.assertEquals(elf1.elfStatus(), elf2.elfStatus());
      Assert.assertEquals(elf1.modification(), elf2.modification());
      Assert.assertEquals(elf1.modificationDate(), elf2.modificationDate());
      Assert.assertEquals(elf1.reason(), elf2.reason());
    }
  }
}
