package com.computablefacts.nona.types;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;

public class BoxedTypeTest {

  @Test
  public void testBooleanEquals() {

    BoxedType btTrue = BoxedType.create(true);

    Assert.assertEquals(BoxedType.create("true"), btTrue);
    Assert.assertEquals(BoxedType.create("TRUE"), btTrue);
    Assert.assertEquals(BoxedType.create(true), btTrue);

    BoxedType btFalse = BoxedType.create(false);

    Assert.assertEquals(BoxedType.create("false"), btFalse);
    Assert.assertEquals(BoxedType.create("FALSE"), btFalse);
    Assert.assertEquals(BoxedType.create(false), btFalse);
  }

  @Test
  public void testIntegerEquals() {

    BoxedType bt1 = BoxedType.create(1);

    Assert.assertEquals(BoxedType.create("1"), bt1);
    Assert.assertEquals(BoxedType.create(1.0), bt1);
    Assert.assertEquals(BoxedType.create(BigDecimal.valueOf(1.0)), bt1);
    Assert.assertEquals(BoxedType.create(BigInteger.valueOf(1)), bt1);
  }

  @Test
  public void testDecimalEquals() {

    BoxedType bt11 = BoxedType.create(1.1);

    Assert.assertEquals(BoxedType.create("1.1"), bt11);
    Assert.assertEquals(BoxedType.create(1.1), bt11);
    Assert.assertEquals(BoxedType.create(BigDecimal.valueOf(1.1)), bt11);
  }

  @Test
  public void testNumberStringEndingWithDotEquals() {

    BoxedType bt1 = BoxedType.create("1.");

    Assert.assertEquals(BoxedType.create("1."), bt1);
    Assert.assertNotEquals(BoxedType.create(1), bt1);
  }

  @Test
  public void testNumberStringStartingWithDotEquals() {

    BoxedType bt1 = BoxedType.create(".1");

    Assert.assertEquals(BoxedType.create(".1"), bt1);
    Assert.assertNotEquals(BoxedType.create(0.1), bt1);
  }

  @Test
  public void testBooleanHashcode() {

    BoxedType btTrue = BoxedType.create(true);

    Assert.assertEquals(BoxedType.create("true").hashCode(), btTrue.hashCode());
    Assert.assertEquals(BoxedType.create("TRUE").hashCode(), btTrue.hashCode());
    Assert.assertEquals(BoxedType.create(true).hashCode(), btTrue.hashCode());

    BoxedType btFalse = BoxedType.create(false);

    Assert.assertEquals(BoxedType.create("false").hashCode(), btFalse.hashCode());
    Assert.assertEquals(BoxedType.create("FALSE").hashCode(), btFalse.hashCode());
    Assert.assertEquals(BoxedType.create(false).hashCode(), btFalse.hashCode());
  }

  @Test
  public void testIntegerHashcode() {

    BoxedType bt1 = BoxedType.create(1);

    Assert.assertEquals(BoxedType.create("1").hashCode(), bt1.hashCode());
    Assert.assertEquals(BoxedType.create(1.0).hashCode(), bt1.hashCode());
    Assert.assertEquals(BoxedType.create(BigDecimal.valueOf(1.0)).hashCode(), bt1.hashCode());
    Assert.assertEquals(BoxedType.create(BigInteger.valueOf(1)).hashCode(), bt1.hashCode());
  }

  @Test
  public void testDecimalHashcode() {

    BoxedType bt11 = BoxedType.create(1.1);

    Assert.assertEquals(BoxedType.create("1.1").hashCode(), bt11.hashCode());
    Assert.assertEquals(BoxedType.create(1.1).hashCode(), bt11.hashCode());
    Assert.assertEquals(BoxedType.create(BigDecimal.valueOf(1.1)).hashCode(), bt11.hashCode());
  }

  @Test
  public void testNumberStringEndingWithDotHashcode() {

    BoxedType bt1 = BoxedType.create("1.");

    Assert.assertEquals(BoxedType.create("1.").hashCode(), bt1.hashCode());
    Assert.assertNotEquals(BoxedType.create(1).hashCode(), bt1.hashCode());
  }

  @Test
  public void testBooleanCompareTo() {

    BoxedType btTrue = BoxedType.create(true);

    Assert.assertTrue(BoxedType.create("true").compareTo(btTrue) == 0);
    Assert.assertTrue(BoxedType.create("TRUE").compareTo(btTrue) == 0);
    Assert.assertTrue(BoxedType.create(true).compareTo(btTrue) == 0);

    BoxedType btFalse = BoxedType.create(false);

    Assert.assertTrue(BoxedType.create("false").compareTo(btFalse) == 0);
    Assert.assertTrue(BoxedType.create("FALSE").compareTo(btFalse) == 0);
    Assert.assertTrue(BoxedType.create(false).compareTo(btFalse) == 0);
  }

  @Test
  public void testIntegerCompareTo() {

    BoxedType bt1 = BoxedType.create(1);

    Assert.assertTrue(BoxedType.create("1").compareTo(bt1) == 0);
    Assert.assertTrue(BoxedType.create(1.0).compareTo(bt1) == 0);
    Assert.assertTrue(BoxedType.create(BigDecimal.valueOf(1.0)).compareTo(bt1) == 0);
    Assert.assertTrue(BoxedType.create(BigInteger.valueOf(1)).compareTo(bt1) == 0);
  }

  @Test
  public void testDecimalCompareTo() {

    BoxedType bt11 = BoxedType.create(1.1);

    Assert.assertTrue(BoxedType.create("1.1").compareTo(bt11) == 0);
    Assert.assertTrue(BoxedType.create(1.1).compareTo(bt11) == 0);
    Assert.assertTrue(BoxedType.create(BigDecimal.valueOf(1.1)).compareTo(bt11) == 0);
  }

  @Test
  public void testNumberStringEndingWithDotCompareTo() {

    BoxedType bt1 = BoxedType.create("1.");

    Assert.assertTrue(BoxedType.create("1.").compareTo(bt1) == 0);
  }
}
