package com.computablefacts.nona.functions.listoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class IsEmptyListTest {

  @Test
  public void testIsEmptyOnEmptyList() {
    Function fn = new Function("IS_EMPTY_LIST(TO_LIST([]))");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsEmpty() {

    String array = Function.wrap("[1, 2, 3]");
    Function fn = new Function(String.format("IS_EMPTY_LIST(TO_LIST(%s))", array));

    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));
  }
}
