package com.computablefacts.nona.functions.listoperators;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.collect.Lists;

public class ConcatListsTest {

  @Test
  public void testConcatEmptyLists() {
    Function fn = new Function("CONCAT_LISTS(TO_LIST([]), TO_LIST([]))");
    Assert.assertEquals(BoxedType.create(new ArrayList<>()), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testConcatFirstIsAnEmptyList() {
    Function fn = new Function("CONCAT_LISTS(TO_LIST([]), TO_LIST(_([1, 2, 3])))");
    Assert.assertEquals(BoxedType.create(Lists.newArrayList("1", "2", "3")),
        fn.evaluate(Function.definitions()));
  }

  @Test
  public void testConcatSecondIsAnEmptyList() {
    Function fn = new Function("CONCAT_LISTS(TO_LIST(_([1, 2, 3])), TO_LIST([]))");
    Assert.assertEquals(BoxedType.create(Lists.newArrayList("1", "2", "3")),
        fn.evaluate(Function.definitions()));
  }

  @Test
  public void testConcatLists() {
    Function fn = new Function("CONCAT_LISTS(TO_LIST(_([1, 2, 3])), TO_LIST(_([a, b, c])))");
    Assert.assertEquals(BoxedType.create(Lists.newArrayList("1", "2", "3", "a", "b", "c")),
        fn.evaluate(Function.definitions()));
  }
}
