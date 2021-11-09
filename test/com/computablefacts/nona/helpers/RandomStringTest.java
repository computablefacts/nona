package com.computablefacts.nona.helpers;

import java.security.SecureRandom;

import org.junit.Assert;
import org.junit.Test;

@Deprecated
public class RandomStringTest {

  @Test
  public void testRandomStringOfDefaultLength() {
    RandomString rnd = new RandomString();
    String str = rnd.nextString();
    Assert.assertEquals(21, str.length());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRandomStringOfLength0() {
    RandomString rnd = new RandomString(0);
  }

  @Test
  public void testRandomStringWithDigitsOnly() {
    RandomString rnd = new RandomString(21, new SecureRandom(), RandomString.digits);
    String str = rnd.nextString();
    Assert.assertEquals(21, str.length());
    Assert.assertEquals(str.toUpperCase(), str);
    Assert.assertEquals(str.toLowerCase(), str);

  }

  @Test
  public void testRandomStringWithUpperCaseCharactersOnly() {
    RandomString rnd = new RandomString(21, new SecureRandom(), RandomString.upper);
    String str = rnd.nextString();
    Assert.assertEquals(21, str.length());
    Assert.assertEquals(str.toUpperCase(), str);
  }
}
