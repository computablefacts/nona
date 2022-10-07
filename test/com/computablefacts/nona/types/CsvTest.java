package com.computablefacts.nona.types;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Assert;
import org.junit.Test;

public class CsvTest {

  @Test
  public void testHashcodeAndEquals() {
    EqualsVerifier.forClass(Csv.class).verify();
  }

  @Test
  public void testEqualsWithNull() {

    Csv csv = Csv.create("col_1,col_2,col_3\n11,12,13\n21,22,23");

    Assert.assertFalse(csv.equals(null));
  }

  @Test
  public void testEqualsWithWrongObjectType() {

    Csv csv = Csv.create("col_1,col_2,col_3\n11,12,13\n21,22,23");

    Assert.assertFalse(csv.equals("string"));
  }

  @Test
  public void testEquals() {

    Csv csv1 = Csv.create("col_1,col_2,col_3\n11,12,13\n21,22,23");
    Csv csv2 = Csv.create("col_1,col_2,col_3\n11,12,13\n21,22,23");

    Assert.assertEquals(csv1, csv2);
  }

  @Test
  public void testNotEquals() {

    Csv csv1 = Csv.create("col_1,col_2,col_3\n11,12,13\n21,22,23");
    Csv csv2 = Csv.create("col_1,col_2,col_3\n21,22,23\n11,12,13");

    Assert.assertNotEquals(csv1, csv2);
  }

  @Test
  public void testSameHashcode() {

    Csv csv1 = Csv.create("col_1,col_2,col_3\n11,12,13\n21,22,23");
    Csv csv2 = Csv.create("col_1,col_2,col_3\n11,12,13\n21,22,23");

    Assert.assertEquals(csv1.hashCode(), csv2.hashCode());
  }

  @Test
  public void testNotSameHashcode() {

    Csv csv1 = Csv.create("col_1,col_2,col_3\n11,12,13\n21,22,23");
    Csv csv2 = Csv.create("col_1,col_2,col_3\n21,22,23\n11,12,13");

    Assert.assertNotEquals(csv1.hashCode(), csv2.hashCode());
  }

  @Test
  public void testCompareSorted() {

    Csv csv1 = Csv.create("col_1,col_2,col_3\n11,12,13\n21,22,23");
    Csv csv2 = Csv.create("col_1,col_2,col_3\n11,12,13\n21,22,23");

    Assert.assertEquals(0, csv1.compareTo(csv2));
    Assert.assertEquals(0, csv2.compareTo(csv1));
  }

  @Test
  public void testCompareScrambled() {

    Csv csv1 = Csv.create("col_1,col_2,col_3\n11,12,13\n21,22,23");
    Csv csv2 = Csv.create("col_3,col_2,col_1\n13,12,11\n23,22,21");

    Assert.assertEquals(0, csv1.compareTo(csv2));
    Assert.assertEquals(0, csv2.compareTo(csv1));
  }

  @Test
  public void testCompareLessRows() {

    Csv csv1 = Csv.create("col_1,col_2,col_3\n11,12,13\n21,22,23");
    Csv csv2 = Csv.create("col_1,col_2,col_3\n11,12,13");

    Assert.assertEquals(0, csv1.compareTo(csv2));
    Assert.assertEquals(0, csv2.compareTo(csv1));
  }

  @Test
  public void testCompareLessColumns() {

    Csv csv1 = Csv.create("col_1,col_2,col_3\n11,12,13\n21,22,23");
    Csv csv2 = Csv.create("col_1,col_2\n11,12\n21,22");

    Assert.assertEquals(0, csv1.compareTo(csv2));
    Assert.assertEquals(0, csv2.compareTo(csv1));
  }

  @Test
  public void testCompareDifferentColumns() {

    Csv csv1 = Csv.create("col_1,col_2,col_3\n11,12,13\n21,22,23");
    Csv csv2 = Csv.create("col_1,col_2,col_n\n11,12,1n\n21,22,2n");

    Assert.assertEquals(0, csv1.compareTo(csv2));
    Assert.assertEquals(0, csv2.compareTo(csv1));
  }

  @Test
  public void testParseCsv() {

    Csv csv = Csv.create("col_1,col_2,col_3\n11,12,13\n21,22,23");

    Assert.assertEquals(2, csv.nbRows());
    Assert.assertEquals("11", csv.value(0, "col_1"));
    Assert.assertEquals("22", csv.value(1, "col_2"));
  }

  @Test
  public void testParseEmptyCsv() {

    Csv csv = Csv.create("col_1,col_2,col_3\n");

    Assert.assertEquals(0, csv.nbRows());
  }

  @Test
  public void testParseMalformedCsv() {

    Csv csv = Csv.create("col_1,col_2,col_3\n11,12,13\n21,22");

    Assert.assertEquals(2, csv.nbRows());
    Assert.assertEquals("21", csv.value(1, "col_1"));
    Assert.assertEquals("22", csv.value(1, "col_2"));
    Assert.assertNull(csv.value(1, "col_3"));
  }

  @Test
  public void testEmptyCsvToText() {

    Csv csv = Csv.create("col_1,col_2,col_3\n");

    Assert.assertEquals("", csv.asString());
  }

  @Test
  public void testCsvToText() {

    Csv csv = Csv.create("col_1,col_2,col_3\n11,12,13\n21,22,23");

    Assert.assertEquals("col_1,col_2,col_3\n11,12,13\n21,22,23", csv.asString());
  }
}
