package com.computablefacts.nona.functions.stringoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class HashTest {

  @Test
  public void testHashMd5() {

    Function fn = new Function("HASH(md5, arg1, arg2, arg3)");
    Assert.assertEquals(BoxedType.create("188bcec347a885d9ad1ad6ca98916a3a"), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testHashSha256() {

    Function fn = new Function("HASH(sha256, arg1, arg2, arg3)");
    Assert.assertEquals(BoxedType.create("0c1d243327ac99eeb40ff8084704f4df14bbb5d8ba8bdc3115cf4530c36bc125"), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testHashSha512() {

    Function fn = new Function("HASH(sha512, arg1, arg2, arg3)");
    Assert.assertEquals(BoxedType.create("c613a7fad985cd4348fbcb4fbe22f706e127dd51b2c0eaa31103955e29b3cb2c6c64c69c069d914c45850c2d96aabfcdb5b6e9538963ee66b6eec088605c3d7c"), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testHashCrc32() {

    Function fn = new Function("HASH(crc32, arg1, arg2, arg3)");
    Assert.assertEquals(BoxedType.create("afc3388c"), fn.evaluate(Function.definitions()));
  }
}
