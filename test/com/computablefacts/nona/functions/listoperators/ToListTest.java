package com.computablefacts.nona.functions.listoperators;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.collect.Lists;

public class ToListTest {

  @Test
  public void testToListOnEmptyList() {
    Function fn = new Function("TO_LIST([])");
    Assert.assertEquals(BoxedType.create(new ArrayList<>()), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testArrayToList() {
    Function fn = new Function("TO_LIST(_([1, 2, 3]))");
    Assert.assertEquals(BoxedType.create(Lists.newArrayList("1", "2", "3")),
        fn.evaluate(Function.definitions()));
  }
}
