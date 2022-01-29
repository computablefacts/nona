package com.computablefacts.nona.functions.listoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.errorprone.annotations.Var;

public class IsEmptyListTest {

  @Test
  public void testIsEmptyOnEmptyList() {
    Function fn = new Function("IS_EMPTY_LIST(TO_LIST([]))");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsEmpty() {
    @Var
    Function fn = new Function("IS_EMPTY_LIST(TO_LIST(_([1, 2, 3])))");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));
  }
}
