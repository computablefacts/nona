package com.computablefacts.nona.types;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class BoxedTypeTest {

  @Test
  public void testEqualsWithNull() {

    BoxedType<?> bt = BoxedType.create(1);

    Assert.assertFalse(bt.equals(null));
  }

  @Test
  public void testEqualsWithWrongObjectType() {

    BoxedType<?> bt = BoxedType.create(1);

    Assert.assertFalse(bt.equals("string"));
  }

  @Test
  public void testEqualsWithQuotedString() {

    BoxedType<?> bt1 = BoxedType.create("\"string\"");
    BoxedType<?> bt2 = BoxedType.create("string");

    Assert.assertTrue(bt1.equals(bt2));
    Assert.assertTrue(bt2.equals(bt1));
  }

  @Test
  public void testBooleanEquals() {

    BoxedType<?> btTrue = BoxedType.create(true);

    Assert.assertEquals(BoxedType.create("true"), btTrue);
    Assert.assertEquals(BoxedType.create("TRUE"), btTrue);
    Assert.assertEquals(BoxedType.create(true), btTrue);

    BoxedType<?> btFalse = BoxedType.create(false);

    Assert.assertEquals(BoxedType.create("false"), btFalse);
    Assert.assertEquals(BoxedType.create("FALSE"), btFalse);
    Assert.assertEquals(BoxedType.create(false), btFalse);
  }

  @Test
  public void testIntegerEquals() {

    BoxedType<?> bt1 = BoxedType.create(1);

    Assert.assertEquals(BoxedType.create("1"), bt1);
    Assert.assertEquals(BoxedType.create(1.0), bt1);
    Assert.assertEquals(BoxedType.create(BigDecimal.valueOf(1.0)), bt1);
    Assert.assertEquals(BoxedType.create(BigInteger.valueOf(1)), bt1);
  }

  @Test
  public void testLongEquals() {

    BoxedType<?> bt1 = BoxedType.create(Long.MAX_VALUE);

    Assert.assertEquals(BoxedType.create("9223372036854775807"), bt1);
    Assert.assertEquals(BoxedType.create(Long.MAX_VALUE), bt1);
    Assert.assertEquals(BoxedType.create(BigDecimal.valueOf(Long.MAX_VALUE)), bt1);
    Assert.assertEquals(BoxedType.create(BigInteger.valueOf(Long.MAX_VALUE)), bt1);
  }

  @Test
  public void testDoubleEquals() {

    BoxedType<?> bt11 = BoxedType.create(Double.MAX_VALUE);

    Assert.assertEquals(BoxedType.create("1.7976931348623157e+308"), bt11);
    Assert.assertEquals(BoxedType.create(Double.MAX_VALUE), bt11);
    Assert.assertEquals(BoxedType.create(BigDecimal.valueOf(Double.MAX_VALUE)), bt11);
  }

  @Test
  public void testFloatEquals() {

    BoxedType<?> bt11 = BoxedType.create(Float.MAX_VALUE);

    // Should be 3.4028235e+38 but is 3.4028234663852886E+38 due to rounding shenanigans
    Assert.assertEquals(BoxedType.create("3.4028234663852886E+38"), bt11);
    Assert.assertEquals(BoxedType.create(Float.MAX_VALUE), bt11);
    Assert.assertEquals(BoxedType.create(BigDecimal.valueOf(Float.MAX_VALUE)), bt11);
  }

  @Test
  public void testNumberStringEndingWithDotEquals() {

    BoxedType<?> bt1 = BoxedType.create("1.");

    Assert.assertEquals(BoxedType.create("1."), bt1);
    Assert.assertNotEquals(BoxedType.create(1), bt1);
  }

  @Test
  public void testNumberStringStartingWithDotEquals() {

    BoxedType<?> bt1 = BoxedType.create(".1");

    Assert.assertEquals(BoxedType.create(".1"), bt1);
    Assert.assertNotEquals(BoxedType.create(0.1), bt1);
  }

  @Test
  public void testBooleanHashcode() {

    BoxedType<?> btTrue = BoxedType.create(true);

    Assert.assertEquals(BoxedType.create("true").hashCode(), btTrue.hashCode());
    Assert.assertEquals(BoxedType.create("TRUE").hashCode(), btTrue.hashCode());
    Assert.assertEquals(BoxedType.create(true).hashCode(), btTrue.hashCode());

    BoxedType<?> btFalse = BoxedType.create(false);

    Assert.assertEquals(BoxedType.create("false").hashCode(), btFalse.hashCode());
    Assert.assertEquals(BoxedType.create("FALSE").hashCode(), btFalse.hashCode());
    Assert.assertEquals(BoxedType.create(false).hashCode(), btFalse.hashCode());
  }

  @Test
  public void testIntegerHashcode() {

    BoxedType<?> bt1 = BoxedType.create(1);

    Assert.assertEquals(BoxedType.create("1").hashCode(), bt1.hashCode());
    Assert.assertEquals(BoxedType.create(1.0).hashCode(), bt1.hashCode());
    Assert.assertEquals(BoxedType.create(BigDecimal.valueOf(1.0)).hashCode(), bt1.hashCode());
    Assert.assertEquals(BoxedType.create(BigInteger.valueOf(1)).hashCode(), bt1.hashCode());
  }

  @Test
  public void testLongHashcode() {

    BoxedType<?> bt1 = BoxedType.create(Long.MAX_VALUE);

    Assert.assertEquals(BoxedType.create("9223372036854775807").hashCode(), bt1.hashCode());
    Assert.assertEquals(BoxedType.create(Long.MAX_VALUE).hashCode(), bt1.hashCode());
    Assert.assertEquals(BoxedType.create(BigDecimal.valueOf(Long.MAX_VALUE)).hashCode(),
        bt1.hashCode());
    Assert.assertEquals(BoxedType.create(BigInteger.valueOf(Long.MAX_VALUE)).hashCode(),
        bt1.hashCode());
  }

  @Test
  public void testDoubleHashcode() {

    BoxedType<?> bt11 = BoxedType.create(Double.MAX_VALUE);

    Assert.assertEquals(BoxedType.create("1.7976931348623157e+308").hashCode(), bt11.hashCode());
    Assert.assertEquals(BoxedType.create(Double.MAX_VALUE).hashCode(), bt11.hashCode());
    Assert.assertEquals(BoxedType.create(BigDecimal.valueOf(Double.MAX_VALUE)).hashCode(),
        bt11.hashCode());
  }

  @Test
  public void testFloatHashcode() {

    BoxedType<?> bt11 = BoxedType.create(Float.MAX_VALUE);

    // Should be 3.4028235e+38 but is 3.4028234663852886E+38 due to rounding shenanigans
    Assert.assertEquals(BoxedType.create("3.4028234663852886E+38").hashCode(), bt11.hashCode());
    Assert.assertEquals(BoxedType.create(Float.MAX_VALUE).hashCode(), bt11.hashCode());
    Assert.assertEquals(BoxedType.create(BigDecimal.valueOf(Float.MAX_VALUE)).hashCode(),
        bt11.hashCode());
  }

  @Test
  public void testNumberStringEndingWithDotHashcode() {

    BoxedType<?> bt1 = BoxedType.create("1.");

    Assert.assertEquals(BoxedType.create("1.").hashCode(), bt1.hashCode());
    Assert.assertNotEquals(BoxedType.create(1).hashCode(), bt1.hashCode());
  }

  @Test
  public void testNumberStringStartingWithDotHashcode() {

    BoxedType<?> bt1 = BoxedType.create(".1");

    Assert.assertEquals(BoxedType.create(".1").hashCode(), bt1.hashCode());
    Assert.assertNotEquals(BoxedType.create(0.1).hashCode(), bt1.hashCode());
  }

  @Test
  public void testNullCompareTo() {

    BoxedType<?> bt1 = BoxedType.empty();
    BoxedType<?> bt2 = BoxedType.empty();

    Assert.assertTrue(bt1.compareTo(bt2).get() == 0);
  }

  @Test
  public void testBooleanCompareTo() {

    BoxedType<?> btTrue = BoxedType.create(true);

    Assert.assertTrue(BoxedType.create("true").compareTo(btTrue).get() == 0);
    Assert.assertTrue(BoxedType.create("TRUE").compareTo(btTrue).get() == 0);
    Assert.assertTrue(BoxedType.create(true).compareTo(btTrue).get() == 0);

    BoxedType<?> btFalse = BoxedType.create(false);

    Assert.assertTrue(BoxedType.create("false").compareTo(btFalse).get() == 0);
    Assert.assertTrue(BoxedType.create("FALSE").compareTo(btFalse).get() == 0);
    Assert.assertTrue(BoxedType.create(false).compareTo(btFalse).get() == 0);
  }

  @Test
  public void testIntegerCompareTo() {

    BoxedType<?> bt1 = BoxedType.create(1);

    Assert.assertTrue(BoxedType.create("1").compareTo(bt1).get() == 0);
    Assert.assertTrue(BoxedType.create(1.0).compareTo(bt1).get() == 0);
    Assert.assertTrue(BoxedType.create(BigDecimal.valueOf(1.0)).compareTo(bt1).get() == 0);
    Assert.assertTrue(BoxedType.create(BigInteger.valueOf(1)).compareTo(bt1).get() == 0);
  }

  @Test
  public void testDecimalCompareTo() {

    BoxedType<?> bt11 = BoxedType.create(1.1);

    Assert.assertTrue(BoxedType.create("1.1").compareTo(bt11).get() == 0);
    Assert.assertTrue(BoxedType.create(1.1).compareTo(bt11).get() == 0);
    Assert.assertTrue(BoxedType.create(BigDecimal.valueOf(1.1)).compareTo(bt11).get() == 0);
  }

  @Test
  public void testNumberStringEndingWithDotCompareTo() {

    BoxedType<?> bt1 = BoxedType.create("1.");

    Assert.assertTrue(BoxedType.create("1.").compareTo(bt1).get() == 0);
  }

  @Test
  public void testNumberStringStartingWithDotCompareTo() {

    BoxedType<?> bt1 = BoxedType.create(".1");

    Assert.assertTrue(BoxedType.create(".1").compareTo(bt1).get() == 0);
  }

  @Test
  public void testBoxList() {

    BoxedType<?> bt1 = BoxedType.create(Lists.newArrayList(1, 2, 3, 4, 5, 6));
    BoxedType<?> bt2 = BoxedType.create(Lists.newArrayList(1, 2, 3, 4, 5, 6));
    BoxedType<?> bt3 = BoxedType.create(Lists.newArrayList(1, 2, 3));

    Assert.assertTrue(bt1.isCollection());

    Assert.assertEquals(bt1.hashCode(), bt2.hashCode());
    Assert.assertNotEquals(bt1.hashCode(), bt3.hashCode());

    Assert.assertTrue(bt1.equals(bt2));
    Assert.assertFalse(bt1.equals(bt3));
  }

  @Test
  public void testBoxSet() {

    BoxedType<?> bt1 = BoxedType.create(Sets.newHashSet(1, 2, 3, 4, 5, 6));
    BoxedType<?> bt2 = BoxedType.create(Sets.newHashSet(1, 2, 3, 4, 5, 6));
    BoxedType<?> bt3 = BoxedType.create(Sets.newHashSet(1, 2, 3));

    Assert.assertTrue(bt1.isCollection());

    Assert.assertEquals(bt1.hashCode(), bt2.hashCode());
    Assert.assertNotEquals(bt1.hashCode(), bt3.hashCode());

    Assert.assertTrue(bt1.equals(bt2));
    Assert.assertFalse(bt1.equals(bt3));
  }

  @Test
  public void testBoxStringArray() {

    BoxedType<?> bt1 = BoxedType.create(new String[] {"1", "2", "3", "4", "5", "6"});
    BoxedType<?> bt2 = BoxedType.create(new String[] {"1", "2", "3", "4", "5", "6"});
    BoxedType<?> bt3 = BoxedType.create(new String[] {"1", "2", "3"});

    Assert.assertTrue(bt1.isCollection());

    Assert.assertEquals(bt1.hashCode(), bt2.hashCode());
    Assert.assertNotEquals(bt1.hashCode(), bt3.hashCode());

    Assert.assertTrue(bt1.equals(bt2));
    Assert.assertFalse(bt1.equals(bt3));
  }

  @Test
  public void testBoxIntArray() {

    BoxedType<?> bt1 = BoxedType.create(new int[] {1, 2, 3, 4, 5, 6});
    BoxedType<?> bt2 = BoxedType.create(new int[] {1, 2, 3, 4, 5, 6});
    BoxedType<?> bt3 = BoxedType.create(new int[] {1, 2, 3});

    Assert.assertTrue(bt1.isCollection());

    Assert.assertEquals(bt1.hashCode(), bt2.hashCode());
    Assert.assertNotEquals(bt1.hashCode(), bt3.hashCode());

    Assert.assertTrue(bt1.equals(bt2));
    Assert.assertFalse(bt1.equals(bt3));
  }

  @Test
  public void testBoxLongArray() {

    BoxedType<?> bt1 = BoxedType.create(new long[] {1, 2, 3, 4, 5, 6});
    BoxedType<?> bt2 = BoxedType.create(new long[] {1, 2, 3, 4, 5, 6});
    BoxedType<?> bt3 = BoxedType.create(new long[] {1, 2, 3});

    Assert.assertTrue(bt1.isCollection());

    Assert.assertEquals(bt1.hashCode(), bt2.hashCode());
    Assert.assertNotEquals(bt1.hashCode(), bt3.hashCode());

    Assert.assertTrue(bt1.equals(bt2));
    Assert.assertFalse(bt1.equals(bt3));
  }

  @Test
  public void testBoxFloatArray() {

    BoxedType<?> bt1 = BoxedType.create(new float[] {1, 2, 3, 4, 5, 6});
    BoxedType<?> bt2 = BoxedType.create(new float[] {1, 2, 3, 4, 5, 6});
    BoxedType<?> bt3 = BoxedType.create(new float[] {1, 2, 3});

    Assert.assertTrue(bt1.isCollection());

    Assert.assertEquals(bt1.hashCode(), bt2.hashCode());
    Assert.assertNotEquals(bt1.hashCode(), bt3.hashCode());

    Assert.assertTrue(bt1.equals(bt2));
    Assert.assertFalse(bt1.equals(bt3));
  }

  @Test
  public void testBoxDoubleArray() {

    BoxedType<?> bt1 = BoxedType.create(new double[] {1, 2, 3, 4, 5, 6});
    BoxedType<?> bt2 = BoxedType.create(new double[] {1, 2, 3, 4, 5, 6});
    BoxedType<?> bt3 = BoxedType.create(new double[] {1, 2, 3});

    Assert.assertTrue(bt1.isCollection());

    Assert.assertEquals(bt1.hashCode(), bt2.hashCode());
    Assert.assertNotEquals(bt1.hashCode(), bt3.hashCode());

    Assert.assertTrue(bt1.equals(bt2));
    Assert.assertFalse(bt1.equals(bt3));
  }

  @Test
  public void testBoxBooleanArray() {

    BoxedType<?> bt1 = BoxedType.create(new boolean[] {true, false, true, false, true, false});
    BoxedType<?> bt2 = BoxedType.create(new boolean[] {true, false, true, false, true, false});
    BoxedType<?> bt3 = BoxedType.create(new boolean[] {true, false, true});

    Assert.assertTrue(bt1.isCollection());

    Assert.assertEquals(bt1.hashCode(), bt2.hashCode());
    Assert.assertNotEquals(bt1.hashCode(), bt3.hashCode());

    Assert.assertTrue(bt1.equals(bt2));
    Assert.assertFalse(bt1.equals(bt3));
  }

  @Test
  public void testAsCollection() {
    Assert.assertEquals(Lists.newArrayList("string"), BoxedType.create("string").asCollection());
    Assert.assertEquals(Lists.newArrayList(BigInteger.valueOf(1)),
        BoxedType.create(1).asCollection());
    Assert.assertEquals(Lists.newArrayList(BigInteger.valueOf(1L)),
        BoxedType.create(1L).asCollection());
    Assert.assertEquals(Lists.newArrayList(BigDecimal.valueOf(1.0f)),
        BoxedType.create(1.0f).asCollection());
    Assert.assertEquals(Lists.newArrayList(BigDecimal.valueOf(1.0d)),
        BoxedType.create(1.0d).asCollection());
  }
}
